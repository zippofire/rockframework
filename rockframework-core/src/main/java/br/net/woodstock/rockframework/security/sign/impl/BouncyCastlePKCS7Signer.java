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

import java.security.GeneralSecurityException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.sign.SignerInfo;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;

public class BouncyCastlePKCS7Signer implements PKCS7Signer {

	private static final String	CERT_STORE_TYPE	= "Collection";

	private SignerInfo			signerInfo;

	public BouncyCastlePKCS7Signer(final SignerInfo signerInfo) {
		super();
		this.signerInfo = signerInfo;
	}

	@Override
	@SuppressWarnings("deprecation")
	public byte[] sign(final byte[] data) {
		try {
			CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
			cmsSignedDataGenerator.addSigner(this.signerInfo.getPrivateKey(), (X509Certificate) this.signerInfo.getCertificate(), CMSSignedGenerator.ENCRYPTION_RSA, CMSSignedGenerator.DIGEST_SHA1);
			cmsSignedDataGenerator.addCertificatesAndCRLs(this.toCertStore(this.signerInfo));
			CMSProcessable content = new CMSProcessableByteArray(data);

			CMSSignedData signedData = cmsSignedDataGenerator.generate(content, true, BouncyCastleProviderHelper.PROVIDER_NAME);

			return signedData.getEncoded();
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean verify(final byte[] data, final byte[] signature) {
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

	private CertStore toCertStore(final SignerInfo signerInfo) throws GeneralSecurityException {
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		list.add(signerInfo.getCertificate());
		return CertStore.getInstance(BouncyCastlePKCS7Signer.CERT_STORE_TYPE, new CollectionCertStoreParameters(list), BouncyCastleProviderHelper.PROVIDER_NAME);
	}

}
