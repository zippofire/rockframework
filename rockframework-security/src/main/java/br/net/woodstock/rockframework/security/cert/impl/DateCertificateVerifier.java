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

import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class DateCertificateVerifier implements CertificateVerifier {

	@Override
	public boolean verify(final Certificate certificate) {
		Assert.notNull(certificate, "certificate");
		try {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			Date current = new Date();

			x509Certificate.checkValidity(current);

			return true;
		} catch (CertificateExpiredException e) {
			return false;
		} catch (CertificateNotYetValidException e) {
			return false;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}
}
