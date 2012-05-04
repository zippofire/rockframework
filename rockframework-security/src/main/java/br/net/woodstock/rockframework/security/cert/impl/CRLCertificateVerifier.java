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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extension;

import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class CRLCertificateVerifier implements CertificateVerifier {

	@Override
	public boolean verify(final Certificate certificate) {
		Assert.notNull(certificate, "certificate");
		try {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			boolean ok = true;
			List<String> urls = this.getCrlDistributionPointsURL(x509Certificate);
			for (String url : urls) {
				X509CRL x509crl = this.getCRLFromURL(url);
				if (x509crl.isRevoked(x509Certificate)) {
					ok = false;
					break;
				}
			}
			return ok;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	private X509CRL getCRLFromURL(final String url) throws GeneralSecurityException, IOException {
		CertificateFactory factory = CertificateFactory.getInstance(CertificateType.X509.getType());
		URL u = new URL(url);
		InputStream inputStream = u.openStream();
		X509CRL x509crl = (X509CRL) factory.generateCRL(inputStream);
		inputStream.close();
		return x509crl;
	}

	private List<String> getCrlDistributionPointsURL(final X509Certificate certificate) throws IOException {
		byte[] crldistribuitionPointsBytes = certificate.getExtensionValue(X509Extension.cRLDistributionPoints.getId());

		if (crldistribuitionPointsBytes == null) {
			return Collections.emptyList();
		}

		ASN1InputStream crldistribuitionPointsBytesStream = new ASN1InputStream(new ByteArrayInputStream(crldistribuitionPointsBytes));
		DERObject crldistribuitionPointsObject = crldistribuitionPointsBytesStream.readObject();
		DEROctetString crldistribuitionPointsString = (DEROctetString) crldistribuitionPointsObject;

		crldistribuitionPointsBytesStream = new ASN1InputStream(new ByteArrayInputStream(crldistribuitionPointsString.getOctets()));
		crldistribuitionPointsObject = crldistribuitionPointsBytesStream.readObject();
		CRLDistPoint distPoint = CRLDistPoint.getInstance(crldistribuitionPointsObject);

		List<String> urls = new ArrayList<String>();

		for (DistributionPoint distribuitionPoint : distPoint.getDistributionPoints()) {
			DistributionPointName distribuitionPointName = distribuitionPoint.getDistributionPoint();
			if ((distribuitionPointName != null) && (distribuitionPointName.getType() == DistributionPointName.FULL_NAME)) {
				GeneralName[] genNames = GeneralNames.getInstance(distribuitionPointName.getName()).getNames();
				for (int i = 0; i < genNames.length; i++) {
					if (genNames[i].getTagNo() == GeneralName.uniformResourceIdentifier) {
						String url = DERIA5String.getInstance(genNames[i].getName()).getString();
						urls.add(url);
					}
				}
			}
		}
		return urls;

	}
}
