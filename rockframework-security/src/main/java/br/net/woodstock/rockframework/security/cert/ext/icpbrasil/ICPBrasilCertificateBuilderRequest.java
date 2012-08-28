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

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;

public abstract class ICPBrasilCertificateBuilderRequest extends CertificateBuilderRequest {

	private static final long		serialVersionUID	= 5197446419645941177L;

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

	// Set
	public ICPBrasilCertificateBuilderRequest withFormato(final FormatoICPBrasilType formato) {
		if (FormatoICPBrasilType.INVALIDO.equals(formato)) {
			throw new IllegalArgumentException("Formato inválido " + formato);
		}
		this.formato = formato;
		return this;
	}

}
