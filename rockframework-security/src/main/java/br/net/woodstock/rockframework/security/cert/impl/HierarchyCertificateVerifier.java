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

import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class HierarchyCertificateVerifier implements CertificateVerifier {

	private Certificate[]	certificates;

	public HierarchyCertificateVerifier(final Certificate certificate) {
		super();
		Assert.notNull(certificate, "certificate");
		this.certificates = new Certificate[] { certificate };
	}

	public HierarchyCertificateVerifier(final Certificate[] certificates) {
		super();
		Assert.notEmpty(certificates, "certificates");
		this.certificates = certificates;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		if (chain.length < 2) {
			CoreLog.getInstance().getLog().info("Certificate chain must be greater than 1(certificate and issuer certificate)");
			return false;
		}
		try {
			boolean result = false;
			for (int i = 0; i < chain.length - 1; i++) {
				X509Certificate certificate = (X509Certificate) chain[i];
				X509Certificate issuer = (X509Certificate) chain[i + 1];

				certificate.verify(issuer.getPublicKey());

				for (Certificate required : this.certificates) {
					X509Certificate x509Required = (X509Certificate) required;
					if (Arrays.equals(issuer.getEncoded(), x509Required.getEncoded())) {
						result = true;
					}
				}
			}

			return result;
		} catch (SignatureException e) {
			CoreLog.getInstance().getLog().info("Invalid certificate chain");
			return false;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}
}
