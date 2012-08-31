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

import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificadoPessoaJuridicaICPBrasil extends CertificadoICPBrasil {

	private String	responsavel;

	private String	cnpj;

	private String	cei;

	private String	nomeEmpresarial;

	CertificadoPessoaJuridicaICPBrasil(final X509Certificate certificate) {
		super(certificate);
		this.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
	}

	public String getResponsavel() {
		return this.responsavel;
	}

	void setResponsavel(final String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	void setCnpj(final String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCei() {
		return this.cei;
	}

	void setCei(final String cei) {
		this.cei = cei;
	}

	public String getNomeEmpresarial() {
		return this.nomeEmpresarial;
	}

	void setNomeEmpresarial(final String nomeEmpresarial) {
		this.nomeEmpresarial = nomeEmpresarial;
	}

	// Delegate
	public Date getDataNascimentoResponsavel() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getDataNascimento();
	}

	public String getCpfResponsavel() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getCpf();
	}

	public String getPisResponsavel() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getPis();
	}

	public String getRgResponsavel() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getRg();
	}

	public String getEmissorRGResponsavel() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getEmissorRG();
	}

}
