/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.sign.impl;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.tsp.TimeStampToken;

import br.net.woodstock.rockframework.office.pdf.PDFException;
import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;
import br.net.woodstock.rockframework.security.sign.DocumentSigner;
import br.net.woodstock.rockframework.security.sign.PKCS7SignatureRequest;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.store.CertificateEntry;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.impl.BouncyCastleTimeStampHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.CollectionUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.OcspClient;
import com.itextpdf.text.pdf.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.TSAClient;

public class PDFSigner implements DocumentSigner {

	private static final char		PDF_SIGNATURE_VERSION	= '\0';

	private PKCS7SignatureRequest	request;

	public PDFSigner(final PKCS7SignatureRequest request) {
		super();
		Assert.notNull(this.request, "request");
		this.request = request;
	}

	@Override
	public byte[] sign(final byte[] data) {
		Assert.notNull(data, "data");
		try {
			byte[] currentData = data;
			for (Alias alias : this.request.getAliases()) {
				currentData = this.singleSign(data, alias);
			}
			return currentData;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	private byte[] singleSign(final byte[] data, final Alias alias) {
		Assert.notEmpty(data, "data");
		try {
			Store store = this.request.getStore();
			CertificateEntry certificateEntry = (CertificateEntry) store.get(alias, StoreEntryType.CERTIFICATE);
			PrivateKeyEntry privateEntry = (PrivateKeyEntry) store.get(alias, StoreEntryType.PRIVATE_KEY);

			if (certificateEntry == null) {
				throw new SignerException("Certificate '" + alias.getName() + " not found in store");
			}

			if (privateEntry == null) {
				throw new SignerException("Private key '" + alias.getName() + " not found in store");
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			X509Certificate certificate = (X509Certificate) certificateEntry.getValue();
			PrivateKey privateKey = privateEntry.getValue();
			Certificate[] chain = privateEntry.getChain();

			DigestType digestType = this.getDigestTypeFromSignature(certificate.getSigAlgName());
			Calendar calendar = Calendar.getInstance();

			PdfReader reader = new PdfReader(data);
			PdfStamper stamper = PdfStamper.createSignature(reader, outputStream, PDFSigner.PDF_SIGNATURE_VERSION, null, true);

			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			appearance.setCrypto(privateKey, chain, null, PdfSignatureAppearance.SELF_SIGNED);
			appearance.setContact(this.request.getContactInfo());
			appearance.setLocation(this.request.getLocation());
			appearance.setReason(this.request.getReason());
			appearance.setSignDate(calendar);

			PdfSignature signature = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			signature.setReason(appearance.getReason());
			signature.setLocation(appearance.getLocation());
			signature.setContact(appearance.getContact());
			signature.setDate(new PdfDate(appearance.getSignDate()));

			if (ConditionUtils.isNotEmpty(this.request.getName())) {
				signature.setName(this.request.getName());
			} else {
				signature.setName(this.getValue(certificate.getSubjectX500Principal()));
			}

			appearance.setCryptoDictionary(signature);

			int contentSize = 0x2502;
			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(contentSize));
			appearance.preClose(exc);

			Digester digester = new BasicDigester(digestType);
			byte[] rangeStream = IOUtils.toByteArray(appearance.getRangeStream());
			byte[] hash = digester.digest(rangeStream);

			TSAClient tsc = null;
			if (this.request.getTimeStampClient() != null) {
				tsc = new DelegateITextTSAClient(this.request.getTimeStampClient());
			}

			byte[] oscp = null;
			if (ConditionUtils.isNotEmpty(chain)) {
				String oscpUrl = PdfPKCS7.getOCSPURL(certificate);
				X509Certificate parentCertificate = null;

				for (Certificate c : chain) {
					if (!certificate.equals(c)) {
						parentCertificate = (X509Certificate) c;
						break;
					}
				}

				if (parentCertificate != null) {
					if ((oscpUrl != null) && (oscpUrl.trim().length() > 0)) {
						OcspClient ocspClient = new OcspClientBouncyCastle(certificate, parentCertificate, oscpUrl);
						oscp = ocspClient.getEncoded();
					}
				}
			}

			PdfPKCS7 pkcs7 = new PdfPKCS7(privateKey, chain, null, digestType.getAlgorithm(), null, false);
			byte[] authenticatedAttributes = pkcs7.getAuthenticatedAttributeBytes(hash, calendar, oscp);
			pkcs7.update(authenticatedAttributes, 0, authenticatedAttributes.length);
			pkcs7.setLocation(this.request.getLocation());
			pkcs7.setReason(this.request.getReason());

			if (tsc == null) {
				pkcs7.setSignDate(calendar);
			}

			byte[] encodedPkcs7 = pkcs7.getEncodedPKCS7(hash, calendar, tsc, oscp);

			byte[] output = new byte[(contentSize - 2) / 2];

			System.arraycopy(encodedPkcs7, 0, output, 0, encodedPkcs7.length);

			PdfDictionary newDictionary = new PdfDictionary();
			PdfString content = new PdfString(output);
			content.setHexWriting(true);
			newDictionary.put(PdfName.CONTENTS, content);
			appearance.close(newDictionary);

			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		try {
			Signature[] signatures = this.getSignatures(signature);
			if (ConditionUtils.isNotEmpty(signatures)) {
				for (Signature s : signatures) {
					if (!s.getValid().booleanValue()) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	public Signature[] getSignatures(final byte[] data) {
		try {
			PdfReader reader = new PdfReader(data);
			AcroFields fields = reader.getAcroFields();
			Collection<Signature> signatures = new ArrayList<Signature>();
			if (fields != null) {
				List<String> list = fields.getSignatureNames();
				if ((list != null) && (!list.isEmpty())) {
					for (String str : list) {
						PdfPKCS7 pk = fields.verifySignature(str);

						PdfString string = fields.getSignatureDictionary(str).getAsString(PdfName.CONTENTS);
						byte[] content = string.getBytes();

						X509Certificate certificate = pk.getSigningCertificate();
						Certificate[] chain = pk.getSignCertificateChain();

						byte[] encoded = content;
						TimeStamp timeStamp = null;
						String location = pk.getLocation();
						String reason = pk.getReason();
						Date date = pk.getSignDate().getTime();
						Boolean valid = Boolean.TRUE;
						Signatory signatory = this.toSignatory(certificate);

						Store store = new JCAStore(KeyStoreType.JKS);
						store.add(new CertificateEntry(new Alias(certificate.getSerialNumber().toString()), certificate));
						Object[] fails = PdfPKCS7.verifyCertificates(chain, store.toKeyStore(), pk.getCRLs(), pk.getSignDate());
						if (ConditionUtils.isNotEmpty(fails)) {
							valid = Boolean.FALSE;
						}

						TimeStampToken timeStampToken = pk.getTimeStampToken();
						if (timeStampToken != null) {
							timeStamp = BouncyCastleTimeStampHelper.toTimeStamp(timeStampToken);
							timeStampToken.getTimeStampInfo();
							if (valid.booleanValue()) {
								boolean ok = pk.verifyTimestampImprint();
								valid = Boolean.valueOf(ok);
							}
						}

						Signature sig = new Signature();
						sig.setDate(date);
						sig.setEncoded(encoded);
						sig.setLocation(location);
						sig.setReason(reason);
						sig.setSignatories(new ArrayList<Signatory>());
						sig.getSignatories().add(signatory);
						sig.setTimeStamp(timeStamp);
						sig.setValid(valid);
						signatures.add(sig);
					}
				}
			}

			return CollectionUtils.toArray(signatures, Signature.class);
		} catch (Exception e) {
			throw new PDFException(e);
		}
	}

	protected Signatory toSignatory(final X509Certificate certificate) {
		X509Principal x509Subject = (X509Principal) certificate.getSubjectDN();
		X509Principal x509Issuer = (X509Principal) certificate.getIssuerDN();

		String subject = this.getValue(x509Subject);
		String issuer = this.getValue(x509Issuer);

		Signatory signatory = new Signatory();
		signatory.setCertificate(certificate);
		signatory.setIssuer(issuer);
		signatory.setSubject(subject);
		return signatory;
	}

	protected SignatureType getSignatureType(final String signatureAlgorithm) {
		SignatureType type = SignatureType.getSignType(signatureAlgorithm);
		if (type == null) {
			type = SignatureType.SHA1_RSA;
		}
		return type;
	}

	protected DigestType getDigestTypeFromSignature(final String signatureAlgorithm) {
		SignatureType signatureType = this.getSignatureType(signatureAlgorithm);
		DigestType digestType = signatureType.getDigestType();
		return digestType;
	}

	protected String getValue(final X509Principal principal) {
		X500Name x500Name = new X500Name(principal.getName());
		RDN rdn = x500Name.getRDNs(BCStyle.CN)[0];
		return IETFUtils.valueToString(rdn.getFirst().getValue());
	}

	protected String getValue(final X500Principal principal) {
		X500Name x500Name = new X500Name(principal.getName());
		RDN rdn = x500Name.getRDNs(BCStyle.CN)[0];
		return IETFUtils.valueToString(rdn.getFirst().getValue());
	}

	@SuppressWarnings("rawtypes")
	protected String toString(final Vector vector) {
		StringBuilder builder = new StringBuilder();
		if (ConditionUtils.isNotEmpty(vector)) {
			for (Object o : vector) {
				builder.append(o.toString());
			}
		}
		return builder.toString();
	}

}
