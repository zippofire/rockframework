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
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCRLStore;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
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
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.SignRequest;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;

public class BouncyCastlePKCS7Signer implements PKCS7Signer {

	private SignRequest	signRequest;

	public BouncyCastlePKCS7Signer(final SignRequest signRequest) {
		super();
		Assert.notNull(signRequest, "signRequest");
		this.signRequest = signRequest;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] sign(final byte[] data) {
		Assert.notEmpty(data, "data");
		try {
			CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
			TimeStampClient timeStampClient = this.signRequest.getTimeStampClient();

			for (Alias alias : this.signRequest.getAliases()) {
				System.out.println(alias);
				
				
				StoreEntry certificateEntry = this.signRequest.getStore().get(alias, StoreEntryType.CERTIFICATE);
				StoreEntry privateKeyEntry = this.signRequest.getStore().get(alias, StoreEntryType.PRIVATE_KEY);

				Certificate certificate = (Certificate) certificateEntry.getValue();
				PrivateKey privateKey = (PrivateKey) privateKeyEntry.getValue();

				JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(SignType.SHA1_RSA.getAlgorithm());
				contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);

				ContentSigner contentSigner = contentSignerBuilder.build(privateKey);

				JcaDigestCalculatorProviderBuilder digestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder();
				digestCalculatorProviderBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
				DigestCalculatorProvider digestCalculatorProvider = digestCalculatorProviderBuilder.build();

				JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);

				SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuilder.build(contentSigner, (X509Certificate) certificate);
				cmsSignedDataGenerator.addSignerInfoGenerator(signerInfoGenerator);
			}

			cmsSignedDataGenerator.addCertificates(this.getCertificateStore());
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
					Attribute unsignAtt = new Attribute(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, derSet);
					hashtable.put(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, unsignAtt);

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
	public Signature[] getSignatures(final byte[] data) {
		throw new UnsupportedOperationException();
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

	protected Store getCertificateStore() throws CertificateEncodingException {
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		// TODO
		// for (CertificateHolder certificateHolder : this.signRequest.getCertificates()) {
		// list.add(certificateHolder.getCertificate());
		// }
		JcaCertStore certStore = new JcaCertStore(list);
		return certStore;
	}

	protected Store getCRLStore() throws CRLException {
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		// TODO
		// for (SignerInfo signerInfo : this.signerInfo.getSignerInfos()) {
		// list.add(signerInfo.getCertificate());
		// }
		JcaCRLStore crlStore = new JcaCRLStore(list);
		return crlStore;
	}

}
