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

import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.DelegateX509Certificate;

public abstract class CertificadoICPBrasil extends DelegateX509Certificate {

	private TipoPessoa	tipoPessoa;

	private TipoFormato	tipoFormato;

	private DadoPessoa	dadoPessoa;

	private String		email;

	public CertificadoICPBrasil(final X509Certificate certificate) {
		super(certificate);
	}

	public TipoPessoa getTipoPessoa() {
		return this.tipoPessoa;
	}

	void setTipoPessoa(final TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoFormato getTipoFormato() {
		return this.tipoFormato;
	}

	void setTipoFormato(final TipoFormato tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	protected DadoPessoa getDadoPessoa() {
		return this.dadoPessoa;
	}

	void setDadoPessoa(final DadoPessoa dadoPessoa) {
		this.dadoPessoa = dadoPessoa;
	}

	public String getEmail() {
		return this.email;
	}

	void setEmail(final String email) {
		this.email = email;
	}

	public static CertificadoICPBrasil getInstance(final Certificate certificate) {
		return CertificadoICPBrasil.getInstance((X509Certificate) certificate);
	}

	public static CertificadoICPBrasil getInstance(final X509Certificate certificate) {
		try {
			return ICPBrasilHelper.getCertificadoICPBrasil(certificate);
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

}
