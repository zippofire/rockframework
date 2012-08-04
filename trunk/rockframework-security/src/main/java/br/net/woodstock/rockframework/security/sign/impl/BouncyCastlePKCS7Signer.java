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

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCRLStore;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.sign.PKCS7SignatureParameters;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.BouncyCastleTimeStampHelper;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.util.Require;
import br.net.woodstock.rockframework.utils.CollectionUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class BouncyCastlePKCS7Signer implements PKCS7Signer {

	private PKCS7SignatureParameters	parameters;

	public BouncyCastlePKCS7Signer(final PKCS7SignatureParameters parameters) {
		super();
		this.parameters = parameters;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] sign(final byte[] data) {
		Require.notNull(this.parameters, "parameters");
		Assert.notEmpty(data, "data");
		try {
			CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
			TimeStampClient timeStampClient = this.parameters.getTimeStampClient();

			for (Alias alias : this.parameters.getAliases()) {
				PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) this.parameters.getStore().get(alias, StoreEntryType.PRIVATE_KEY);

				if (privateKeyEntry == null) {
					throw new SignerException("PrivateKey not found for alias '" + alias.getName() + "'");
				}

				PrivateKey privateKey = privateKeyEntry.getValue();
				Certificate[] chain = privateKeyEntry.getChain();
				Certificate certificate = chain[0];

				JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(SignatureType.SHA1_RSA.getAlgorithm());
				contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);

				ContentSigner contentSigner = contentSignerBuilder.build(privateKey);

				JcaDigestCalculatorProviderBuilder digestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder();
				digestCalculatorProviderBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
				DigestCalculatorProvider digestCalculatorProvider = digestCalculatorProviderBuilder.build();

				JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);

				// if (timeStampClient == null) {
				// DERObject derObject = new ASN1UTCTime(new Date());
				// DERSet derSet = new DERSet(derObject);
				//
				// Attribute attribute = new Attribute(PKCSObjectIdentifiers.pkcs_9_at_signingTime, derSet);
				// Hashtable hashtable = new Hashtable();
				// hashtable.put(PKCSObjectIdentifiers.pkcs_9_at_signingTime, attribute);
				//
				// AttributeTable table = new AttributeTable(hashtable);
				// CMSAttributeTableGenerator attributeTableGenerator = new DefaultSignedAttributeTableGenerator(table);
				// signerInfoGeneratorBuilder.setSignedAttributeGenerator(attributeTableGenerator);
				// }

				SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuilder.build(contentSigner, (X509Certificate) certificate);

				cmsSignedDataGenerator.addSignerInfoGenerator(signerInfoGenerator);
				cmsSignedDataGenerator.addCertificates(this.getCertificateStore(chain));
			}

			CMSTypedData content = new CMSProcessableByteArray(data);

			CMSSignedData signedData = cmsSignedDataGenerator.generate(content, true);

			if (timeStampClient != null) {
				SignerInformationStore signerInformationStore = signedData.getSignerInfos();
				List list = new ArrayList();
				for (Object o : signerInformationStore.getSigners()) {
					SignerInformation signerInformation = (SignerInformation) o;
					TimeStamp timeStamp = timeStampClient.getTimeStamp(signerInformation.getSignature());
					DERObject derObject = new ASN1InputStream(timeStamp.getEncoded()).readObject();
					DERSet derSet = new DERSet(derObject);

					Hashtable hashtable = new Hashtable();
					Attribute attribute = new Attribute(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, derSet);
					hashtable.put(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, attribute);

					AttributeTable unsignedAtts = new AttributeTable(hashtable);

					list.add(SignerInformation.replaceUnsignedAttributes(signerInformation, unsignedAtts));
				}

				SignerInformationStore tmpSignerInformationStore = new SignerInformationStore(list);

				signedData = CMSSignedData.replaceSigners(signedData, tmpSignerInformationStore);
			}

			return signedData.getEncoded();
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean verify(final byte[] data, final byte[] signature) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(signature, "signature");
		try {
			CMSSignedData signedData = new CMSSignedData(signature);
			CollectionStore certificatesStore = (CollectionStore) signedData.getCertificates();
			// CollectionStore crlStore = (CollectionStore) signedData.getCRLs();

			SignerInformationStore signerInformationStore = signedData.getSignerInfos();
			boolean verified = true;
			for (Object o : signerInformationStore.getSigners()) {
				SignerInformation signerInformation = (SignerInformation) o;

				Collection<Certificate> collection = certificatesStore.getMatches(null);
				if (!collection.isEmpty()) {
					for (Certificate cert : collection) {

						JcaContentVerifierProviderBuilder jcaContentVerifierProviderBuilder = new JcaContentVerifierProviderBuilder();
						jcaContentVerifierProviderBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);

						ContentVerifierProvider contentVerifierProvider = jcaContentVerifierProviderBuilder.build((X509Certificate) cert);

						JcaDigestCalculatorProviderBuilder digestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder();
						digestCalculatorProviderBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
						DigestCalculatorProvider digestCalculatorProvider = digestCalculatorProviderBuilder.build();

						SignerInformationVerifier signerInformationVerifier = new SignerInformationVerifier(contentVerifierProvider, digestCalculatorProvider);

						if (!signerInformation.verify(signerInformationVerifier)) {
							verified = false;
						}
					}
				}
			}
			if (verified) {
				CMSProcessable signedContent = signedData.getSignedContent();
				byte[] content = (byte[]) signedContent.getContent();
				verified = Arrays.equals(data, content);
			}
			return verified;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public Signature[] getSignatures(final byte[] data) {
		try {
			CMSSignedData signedData = new CMSSignedData(data);
			Collection<X509CertificateHolder> certificates = signedData.getCertificates().getMatches(null);
			SignerInformationStore signerInformationStore = signedData.getSignerInfos();
			Collection<SignerInformation> informations = signerInformationStore.getSigners();
			Collection<Signature> signatures = new ArrayList<Signature>();

			// 1.2.840.113549.1.9.3 -- content type
			// 1.2.840.113549.1.9.4 -- messagedigest
			// 1.2.840.113549.1.9.5 -- sign time
			// 1.2.840.113549.1.9.16.2.12 -- signcertificate

			if (ConditionUtils.isNotEmpty(informations)) {
				for (SignerInformation information : informations) {
					Signature signature = new Signature();
					signature.setEncoded(null); // FIXME
					signature.setLocation(null); // FIXME
					signature.setReason(null); // FIXME
					signature.setSignatories(new ArrayList<Signatory>());
					signature.setValid(Boolean.TRUE);

					// TimeStamp
					AttributeTable signedAttributeTable = information.getSignedAttributes();
					AttributeTable unsignedAttributeTable = information.getUnsignedAttributes();

					DERSequence timeStampDerSequence = this.getAttribute(signedAttributeTable, unsignedAttributeTable, PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
					// DERSequence contentTypeDerSequence = this.getAttribute(signedAttributeTable, unsignedAttributeTable, PKCSObjectIdentifiers.pkcs_9_at_contentType);
					// DERSequence messageDigestDerSequence = this.getAttribute(signedAttributeTable, unsignedAttributeTable, PKCSObjectIdentifiers.pkcs_9_at_messageDigest);
					DERSequence signTimeDerSequence = this.getAttribute(signedAttributeTable, unsignedAttributeTable, PKCSObjectIdentifiers.pkcs_9_at_signingTime);

					if (timeStampDerSequence != null) {
						if (timeStampDerSequence.size() == 2) {
							DERObject derObjectIdentifier = ((DERObject) timeStampDerSequence.getObjectAt(0)).toASN1Object();
							DERObject derObjectValue = ((DERObject) timeStampDerSequence.getObjectAt(1)).toASN1Object();
							if ((derObjectIdentifier instanceof ASN1ObjectIdentifier) && (derObjectValue instanceof DERSet)) {
								// ASN1ObjectIdentifier asn1ObjectIdentifier = (ASN1ObjectIdentifier) derObjectIdentifier;
								DERSet set = (DERSet) derObjectValue;
								DEREncodable encodable = set.getObjectAt(0);
								TimeStampToken timeStampToken = new TimeStampToken(new CMSSignedData(encodable.getDERObject().getEncoded()));
								TimeStamp timeStamp = BouncyCastleTimeStampHelper.toTimeStamp(timeStampToken);
								signature.setTimeStamp(timeStamp);
							}
						}
					}

					if (signTimeDerSequence != null) {
						DERObject derObjectIdentifier = ((DERObject) signTimeDerSequence.getObjectAt(0)).toASN1Object();
						DERObject derObjectValue = ((DERObject) signTimeDerSequence.getObjectAt(1)).toASN1Object();
						if ((derObjectIdentifier instanceof ASN1ObjectIdentifier) && (derObjectValue instanceof DERSet)) {
							DERSet set = (DERSet) derObjectValue;
							ASN1UTCTime time = (ASN1UTCTime) set.getObjectAt(0);
							signature.setDate(time.getAdjustedDate());
						}
					}

					SignerId signerId = information.getSID();
					if (signerId != null) {
						BigInteger serialNumber = signerId.getSerialNumber();
						X509CertificateHolder certificateHolder = null;
						for (X509CertificateHolder tmp : certificates) {
							if (tmp.getSerialNumber().equals(serialNumber)) {
								certificateHolder = tmp;
								break;
							}
						}

						if (certificateHolder != null) {
							Signatory signatory = new Signatory();
							signatory.setSubject(BouncyCastleProviderHelper.getName(certificateHolder.getSubject()));
							signatory.setIssuer(BouncyCastleProviderHelper.getName(certificateHolder.getIssuer()));
							signatory.setCertificate(BouncyCastleProviderHelper.getCertificate(certificateHolder));
							signature.getSignatories().add(signatory);
						}
					}

					signatures.add(signature);
				}
			}

			return CollectionUtils.toArray(signatures, Signature.class);
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	protected byte[] encapsulateContent(final byte[] data, final byte[] signature) throws IOException {
		ASN1InputStream inputStream = new ASN1InputStream(signature);
		DERSequence derSequence = (DERSequence) inputStream.readObject();
		ContentInfo signaturecontentInfo = new ContentInfo(derSequence);
		SignedData signatureSignedData = new SignedData((ASN1Sequence) signaturecontentInfo.getContent());
		ContentInfo dataContentInfo = new ContentInfo(CMSObjectIdentifiers.data, new BERConstructedOctetString(data));
		SignedData datasignedData = new SignedData(signatureSignedData.getDigestAlgorithms(), dataContentInfo, signatureSignedData.getCertificates(), signatureSignedData.getCRLs(), signatureSignedData.getSignerInfos());
		ContentInfo fullContentInfo = new ContentInfo(PKCSObjectIdentifiers.signedData, datasignedData);
		return fullContentInfo.getDEREncoded();
	}

	protected Store getCertificateStore(final Certificate[] chain) throws CertificateEncodingException {
		List<Certificate> list = Arrays.asList(chain);
		JcaCertStore certStore = new JcaCertStore(list);
		return certStore;
	}

	protected Store getCRLStore() throws CRLException {
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		JcaCRLStore crlStore = new JcaCRLStore(list);
		return crlStore;
	}

	private DERSequence getAttribute(final AttributeTable signedAttributeTable, final AttributeTable unsignedAttributeTable, final DERObjectIdentifier identifier) {
		DERSequence object = null;
		if ((signedAttributeTable != null) && (signedAttributeTable.get(identifier) != null)) {
			object = (DERSequence) signedAttributeTable.get(identifier).getDERObject();
		} else if ((unsignedAttributeTable != null) && (unsignedAttributeTable.get(identifier) != null)) {
			object = (DERSequence) unsignedAttributeTable.get(identifier).getDERObject();
		}
		return object;
	}

}
