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
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class ICPBrasilCertificateVerifier implements CertificateVerifier {

	private TipoFormato[]	tiposFormato;

	public ICPBrasilCertificateVerifier() {
		super();
	}

	public ICPBrasilCertificateVerifier(final TipoFormato[] tiposFormato) {
		super();
		Assert.notEmpty(tiposFormato, "tiposFormato");
		this.tiposFormato = tiposFormato;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		X509Certificate x509Certificate = (X509Certificate) chain[0];
		CertificadoICPBrasil certificadoICPBrasil = CertificadoICPBrasil.getInstance(x509Certificate);

		if (certificadoICPBrasil.getTipoPessoa() == TipoPessoa.DESCONHECIDO) {
			return false;
		}

		if (ConditionUtils.isNotEmpty(this.tiposFormato)) {
			boolean ok = false;
			for (TipoFormato tipoFormato : this.tiposFormato) {
				if (tipoFormato.equals(certificadoICPBrasil.getTipoFormato())) {
					ok = true;
					break;
				}
			}
			if (!ok) {
				return false;
			}
		}

		return true;
	}
}
