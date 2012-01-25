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
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.tsp.TimeStampToken;

import br.net.woodstock.rockframework.office.pdf.PDFException;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;
import br.net.woodstock.rockframework.security.sign.DocumentSigner;
import br.net.woodstock.rockframework.security.sign.SignRequest;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
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

	private static final char	PDF_SIGNATURE_VERSION	= '\0';

	private SignRequest			signRequest;

	public PDFSigner(final SignRequest signRequest) {
		super();
		Assert.notNull(signRequest, "signRequest");
		this.signRequest = signRequest;
	}

	@Override
	public byte[] sign(final byte[] data) {
		Assert.notNull(data, "data");
		try {
			byte[] currentData = data;
			for (CertificateHolder certificateHolder : this.signRequest.getCertificates()) {
				SignRequest sr = new SignRequest();
				sr.setContactInfo(this.signRequest.getContactInfo());
				sr.setLocation(this.signRequest.getLocation());
				sr.setName(this.signRequest.getName());
				sr.setReason(this.signRequest.getReason());
				sr.setTimeStampClient(this.signRequest.getTimeStampClient());

				sr.setCertificates(new CertificateHolder[] { certificateHolder });

				currentData = this.singleSign(data, sr);
			}
			return currentData;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	private byte[] singleSign(final byte[] data, final SignRequest requestData) {
		Assert.notEmpty(data, "data");
		Assert.notNull(requestData, "requestData");
		try {
			CertificateHolder holder = requestData.getCertificates()[0];

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			X509Certificate certificate = (X509Certificate) holder.getCertificate();
			X509Certificate rootCertificate = (X509Certificate) holder.getRootCertificate();
			Certificate[] chain = holder.getChain();
			PrivateKey privateKey = holder.getPrivateKey();
			DigestType digestType = this.getDigestTypeFromSignature(certificate.getSigAlgName());
			Calendar calendar = Calendar.getInstance();

			PdfReader reader = new PdfReader(data);
			PdfStamper stamper = PdfStamper.createSignature(reader, outputStream, PDFSigner.PDF_SIGNATURE_VERSION, null, true);

			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			appearance.setCrypto(privateKey, chain, null, PdfSignatureAppearance.SELF_SIGNED);
			appearance.setContact(requestData.getContactInfo());
			appearance.setLocation(requestData.getLocation());
			appearance.setReason(requestData.getReason());
			appearance.setSignDate(calendar);

			PdfSignature signature = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			signature.setReason(appearance.getReason());
			signature.setLocation(appearance.getLocation());
			signature.setContact(appearance.getContact());
			signature.setDate(new PdfDate(appearance.getSignDate()));

			appearance.setCryptoDictionary(signature);

			int contentSize = 0x2502;
			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(contentSize));
			appearance.preClose(exc);

			Digester digester = new BasicDigester(digestType);
			byte[] rangeStream = IOUtils.toByteArray(appearance.getRangeStream());
			byte[] hash = digester.digest(rangeStream);

			TSAClient tsc = null;
			if (requestData.getTimeStampClient() != null) {
				tsc = new DelegateITextTSAClient(requestData.getTimeStampClient());
			}

			byte[] oscp = null;
			if (rootCertificate != null) {
				String oscpUrl = PdfPKCS7.getOCSPURL(certificate);
				if ((oscpUrl != null) && (oscpUrl.trim().length() > 0)) {
					OcspClient ocspClient = new OcspClientBouncyCastle(certificate, rootCertificate, oscpUrl);
					oscp = ocspClient.getEncoded();
				}
			}

			PdfPKCS7 pkcs7 = new PdfPKCS7(privateKey, chain, null, digestType.getAlgorithm(), null, false);
			byte[] authenticatedAttributes = pkcs7.getAuthenticatedAttributeBytes(hash, calendar, oscp);
			pkcs7.update(authenticatedAttributes, 0, authenticatedAttributes.length);
			pkcs7.setLocation(requestData.getLocation());
			pkcs7.setReason(requestData.getReason());

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

	// protected byte[] getITextPKCS7(final X509Certificate certificate, final PrivateKey privateKey, final
	// Certificate[] chain, final byte[] rangeStream, final DigestType digestType, final Calendar calendar)
	// throws CertificateParsingException, InvalidKeyException, NoSuchProviderException,
	// NoSuchAlgorithmException, SignatureException {
	// Digester digester = new BasicDigester(digestType);
	// byte[] hash = digester.digest(rangeStream);
	//
	// TSAClient tsc = null;
	//
	// if (this.signRequest.getTimeStampClient() != null) {
	// tsc = new DelegateITextTSAClient(this.signRequest.getTimeStampClient());
	// }
	//
	// byte[] oscp = null;
	// if (chain.length > 1) {
	// String oscpUrl = PdfPKCS7.getOCSPURL(certificate);
	// X509Certificate rootCertificate = (X509Certificate) chain[1];
	// if (ConditionUtils.isNotEmpty(oscpUrl)) {
	// OcspClient ocspClient = new OcspClientBouncyCastle(certificate, rootCertificate, oscpUrl);
	// oscp = ocspClient.getEncoded();
	// }
	// }
	//
	// PdfPKCS7 pkcs7 = new PdfPKCS7(privateKey, chain, null, digestType.getAlgorithm(), null, false);
	// byte[] authenticatedAttributes = pkcs7.getAuthenticatedAttributeBytes(hash, calendar, oscp);
	// pkcs7.update(authenticatedAttributes, 0, authenticatedAttributes.length);
	// pkcs7.setLocation(this.signRequest.getLocation());
	// pkcs7.setReason(this.signRequest.getReason());
	//
	// if (tsc == null) {
	// pkcs7.setSignDate(calendar);
	// }
	//
	// byte[] encodedPkcs7 = pkcs7.getEncodedPKCS7(hash, calendar, tsc, oscp);
	// return encodedPkcs7;
	// }
	//
	// protected byte[] getCMSPKCS7(final CertificateHolder certificateHolder, final byte[] rangeStream, final
	// DigestType digestType) {
	// Digester digester = new BasicDigester(digestType);
	//
	// SignRequest sr = new SignRequest();
	//
	// sr.setContactInfo(this.signRequest.getContactInfo());
	// sr.setLocation(this.signRequest.getLocation());
	// sr.setReason(this.signRequest.getReason());
	// sr.setTimeStampClient(this.signRequest.getTimeStampClient());
	// sr.setCertificates(new CertificateHolder[] { certificateHolder });
	//
	// BouncyCastlePKCS7Signer signer = new BouncyCastlePKCS7Signer(sr);
	//
	// byte[] encodedPkcs7 = signer.sign(digester.digest(rangeStream));
	// return encodedPkcs7;
	// }

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

						Certificate[] certificates = pk.getCertificates();

						Store store = new JCAStore(KeyStoreType.JKS);

						List<Signatory> signers = new ArrayList<Signatory>();
						for (Certificate certificate : certificates) {
							X509Certificate x509Certificate = (X509Certificate) certificate;
							X509Principal x509Subject = (X509Principal) x509Certificate.getSubjectDN();
							X509Principal x509Issuer = (X509Principal) x509Certificate.getIssuerDN();

							String subject = this.getValue(x509Subject);
							String issuer = this.getValue(x509Issuer);

							Signatory signatory = new Signatory(subject, issuer);
							signers.add(signatory);

							store.add(new StoreEntry(x509Certificate.getSerialNumber().toString(), null, x509Certificate, StoreEntryType.CERTIFICATE));
						}

						Boolean valid = Boolean.TRUE;

						Object[] fails = PdfPKCS7.verifyCertificates(certificates, store.toKeyStore(), pk.getCRLs(), pk.getSignDate());
						if (ConditionUtils.isNotEmpty(fails)) {
							valid = Boolean.FALSE;
						}

						if (valid.booleanValue()) {
							TimeStampToken timeStampToken = pk.getTimeStampToken();
							if (timeStampToken != null) {
								boolean ok = pk.verifyTimestampImprint();
								valid = Boolean.valueOf(ok);
							}
						}

						Signature sig = new Signature(pk.getLocation(), pk.getReason(), pk.getSignDate().getTime(), valid, signers);
						signatures.add(sig);
					}
				}
			}

			return CollectionUtils.toArray(signatures, Signature.class);
		} catch (Exception e) {
			throw new PDFException(e);
		}
	}

	protected SignType getSignatureType(final String signatureAlgorithm) {
		SignType signType = SignType.getSignType(signatureAlgorithm);
		if (signType == null) {
			signType = SignType.SHA1_RSA;
		}
		return signType;
	}

	protected DigestType getDigestTypeFromSignature(final String signatureAlgorithm) {
		SignType signType = this.getSignatureType(signatureAlgorithm);
		DigestType digestType = signType.getDigestType();
		return digestType;
	}

	protected String getValue(final X509Principal principal) {
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
