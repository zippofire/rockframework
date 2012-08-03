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
package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class ICPBrasilCertificateVerifier implements CertificateVerifier {

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		X509Certificate x509Certificate = (X509Certificate) chain[0];
		CertificadoICPBrasil certificadoICPBrasil = CertificadoICPBrasil.getInstance(x509Certificate);

		if (certificadoICPBrasil.getICPBrasilType() == CertificadoICPBrasilType.INVALIDO) {
			return false;
		}

		return true;
	}
}
