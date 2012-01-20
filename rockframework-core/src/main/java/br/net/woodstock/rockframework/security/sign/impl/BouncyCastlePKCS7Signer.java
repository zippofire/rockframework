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
import java.security.GeneralSecurityException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
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
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.PKCS7SignerInfo;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.sign.SignerInfo;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;

public class BouncyCastlePKCS7Signer implements PKCS7Signer {

	private static final String	CERT_STORE_TYPE	= "Collection";

	private PKCS7SignerInfo		signerInfo;

	public BouncyCastlePKCS7Signer(final PKCS7SignerInfo signerInfo) {
		super();
		Assert.notNull(signerInfo, "signerInfo");
		this.signerInfo = signerInfo;
	}

	@Override
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public byte[] sign(final byte[] data) {
		Assert.notEmpty(data, "data");
		try {
			CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
			TimeStampClient timeStampClient = this.signerInfo.getTimeStampClient();
			for (SignerInfo signerInfo : this.signerInfo.getSignerInfos()) {
				cmsSignedDataGenerator.addSigner(signerInfo.getPrivateKey(), (X509Certificate) signerInfo.getCertificate(), CMSSignedGenerator.ENCRYPTION_RSA, CMSSignedGenerator.DIGEST_SHA1);
			}

			cmsSignedDataGenerator.addCertificatesAndCRLs(this.getCertStore());
			CMSProcessable content = new CMSProcessableByteArray(data);

			CMSSignedData signedData = cmsSignedDataGenerator.generate(content, false, BouncyCastleProviderHelper.PROVIDER_NAME);

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
	@SuppressWarnings("deprecation")
	public boolean verify(final byte[] data, final byte[] signature) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(signature, "signature");
		try {
			CMSSignedData signedData = new CMSSignedData(signature);
			CertStore certStore = signedData.getCertificatesAndCRLs(BouncyCastlePKCS7Signer.CERT_STORE_TYPE, BouncyCastleProviderHelper.PROVIDER_NAME);
			SignerInformationStore signerInformationStore = signedData.getSignerInfos();
			boolean verified = false;
			for (Object o : signerInformationStore.getSigners()) {
				SignerInformation signerInformation = (SignerInformation) o;
				Collection<? extends Certificate> collection = certStore.getCertificates(signerInformation.getSID());
				if (!collection.isEmpty()) {
					X509Certificate cert = (X509Certificate) collection.iterator().next();
					if (signerInformation.verify(cert.getPublicKey(), BouncyCastleProviderHelper.PROVIDER_NAME)) {
						verified = true;
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

	private CertStore getCertStore() throws GeneralSecurityException {
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		for (SignerInfo signerInfo : this.signerInfo.getSignerInfos()) {
			list.add(signerInfo.getCertificate());
		}
		return CertStore.getInstance(BouncyCastlePKCS7Signer.CERT_STORE_TYPE, new CollectionCertStoreParameters(list), BouncyCastleProviderHelper.PROVIDER_NAME);
	}

}
