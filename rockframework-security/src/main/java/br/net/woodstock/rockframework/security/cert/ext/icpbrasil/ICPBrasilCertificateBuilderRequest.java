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

import java.util.Map;

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public abstract class ICPBrasilCertificateBuilderRequest extends CertificateBuilderRequest {

	private static final long		serialVersionUID	= 5197446419645941177L;

	private static final String		URL_CRL_LOCAL		= "http://www.woodstock.net.br/rockframework/crl.html";

	private FormatoICPBrasilType	formato;

	public ICPBrasilCertificateBuilderRequest(final String subject) {
		super(subject);
	}

	public ICPBrasilCertificateBuilderRequest(final String subject, final String issuer) {
		super(subject, issuer);
	}

	// Get
	public FormatoICPBrasilType getFormato() {
		return this.formato;
	}

	@Override
	public Map<String, String> getCertificatePolicies() {
		Map<String, String> map = super.getCertificatePolicies();

		FormatoICPBrasilType formato = this.getFormato();
		if (formato != null) {
			String key = null;
			switch (formato) {
				case A1:
					key = ICPBrasilHelper.OID_A1_AC_SERPRO;
					break;
				case A2:
					key = ICPBrasilHelper.OID_A2_AC_SERASA;
					break;
				case A3:
					key = ICPBrasilHelper.OID_A3_AC_PR;
					break;
				case A4:
					key = ICPBrasilHelper.OID_A4_AC_SERASA;
					break;
				default:
					break;
			}
			if (ConditionUtils.isNotEmpty(key)) {
				map.put(key, ICPBrasilCertificateBuilderRequest.URL_CRL_LOCAL);
			}
		}

		return map;
	}

	// Set
	public ICPBrasilCertificateBuilderRequest withFormato(final FormatoICPBrasilType formato) {
		this.formato = formato;
		return this;
	}

}
