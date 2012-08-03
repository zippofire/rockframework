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

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class SelfSignedCertificateVerifier implements CertificateVerifier {

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		try {
			X509Certificate x509Certificate = (X509Certificate) chain[0];
			if (this.isSelfSigned(x509Certificate)) {
				return false;
			}

			return true;
		} catch (GeneralSecurityException e) {
			throw new CertificateException(e);
		}
	}

	protected boolean isSelfSigned(final X509Certificate certificate) throws GeneralSecurityException {
		try {
			PublicKey publicKey = certificate.getPublicKey();
			certificate.verify(publicKey);
			return true;
		} catch (SignatureException e) {
			return false;
		}
	}

}
