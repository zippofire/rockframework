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
package br.net.woodstock.rockframework.security.cert.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extension;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class CRLCertificateVerifier implements CertificateVerifier {

	private URL	url;

	public CRLCertificateVerifier() {
		super();
	}

	public CRLCertificateVerifier(final URL url) {
		super();
		Assert.notNull(url, "url");
		this.url = url;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		try {
			X509Certificate x509Certificate = (X509Certificate) chain[0];
			URL url = null;

			if (this.url == null) {
				URL[] urls = CRLCertificateVerifier.getCrlDistributionPointsURL(x509Certificate);
				if (ConditionUtils.isNotEmpty(urls)) {
					url = urls[0];
				}
			} else {
				url = this.url;
			}

			if (url == null) {
				CoreLog.getInstance().getLog().info("No url found for validation");
				return false;
			}

			X509CRL x509crl = this.getCRLFromURL(url);
			if (x509crl.isRevoked(x509Certificate)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	private X509CRL getCRLFromURL(final URL url) throws GeneralSecurityException, IOException {
		CertificateFactory factory = CertificateFactory.getInstance(CertificateType.X509.getType());
		InputStream inputStream = url.openStream();
		X509CRL x509crl = (X509CRL) factory.generateCRL(inputStream);
		inputStream.close();
		return x509crl;
	}

	public static URL[] getCrlDistributionPointsURL(final Certificate certificate) throws IOException {
		X509Certificate x509Certificate = (X509Certificate) certificate;
		byte[] crldistribuitionPointsBytes = x509Certificate.getExtensionValue(X509Extension.cRLDistributionPoints.getId());

		if (crldistribuitionPointsBytes == null) {
			return new URL[0];
		}

		DERObject crldistribuitionPointsObject = BouncyCastleProviderHelper.toDERObject(crldistribuitionPointsBytes);
		DEROctetString crldistribuitionPointsString = (DEROctetString) crldistribuitionPointsObject;

		crldistribuitionPointsObject = BouncyCastleProviderHelper.toDERObject(crldistribuitionPointsString.getOctets());
		CRLDistPoint distPoint = CRLDistPoint.getInstance(crldistribuitionPointsObject);

		Set<URL> urls = new HashSet<URL>();

		for (DistributionPoint distribuitionPoint : distPoint.getDistributionPoints()) {
			DistributionPointName distribuitionPointName = distribuitionPoint.getDistributionPoint();
			if ((distribuitionPointName != null) && (distribuitionPointName.getType() == DistributionPointName.FULL_NAME)) {
				GeneralName[] genNames = GeneralNames.getInstance(distribuitionPointName.getName()).getNames();
				for (int i = 0; i < genNames.length; i++) {
					if (genNames[i].getTagNo() == GeneralName.uniformResourceIdentifier) {
						String urlStr = DERIA5String.getInstance(genNames[i].getName()).getString();
						URL url = new URL(urlStr);
						urls.add(url);
					}
				}
			}
		}
		return urls.toArray(new URL[urls.size()]);
	}
}
