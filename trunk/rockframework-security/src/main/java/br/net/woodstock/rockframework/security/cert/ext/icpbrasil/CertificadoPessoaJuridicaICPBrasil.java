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

	private Date	dataNascimentoResponsavel;

	private String	cpfResponsavel;

	private String	pisResponsavel;

	private String	rgResponsavel;

	private String	emissorRGResponsavel;

	private String	cei;

	public CertificadoPessoaJuridicaICPBrasil(final X509Certificate certificate) {
		super(certificate, CertificadoICPBrasilType.PESSOA_JURIDICA);
	}

	public String getResponsavel() {
		return this.responsavel;
	}

	public void setResponsavel(final String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(final String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataNascimentoResponsavel() {
		return this.dataNascimentoResponsavel;
	}

	public void setDataNascimentoResponsavel(final Date dataNascimentoResponsavel) {
		this.dataNascimentoResponsavel = dataNascimentoResponsavel;
	}

	public String getCpfResponsavel() {
		return this.cpfResponsavel;
	}

	public void setCpfResponsavel(final String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getPisResponsavel() {
		return this.pisResponsavel;
	}

	public void setPisResponsavel(final String pisResponsavel) {
		this.pisResponsavel = pisResponsavel;
	}

	public String getRgResponsavel() {
		return this.rgResponsavel;
	}

	public void setRgResponsavel(final String rgResponsavel) {
		this.rgResponsavel = rgResponsavel;
	}

	public String getEmissorRGResponsavel() {
		return this.emissorRGResponsavel;
	}

	public void setEmissorRGResponsavel(final String emissorRGResponsavel) {
		this.emissorRGResponsavel = emissorRGResponsavel;
	}

	public String getCei() {
		return this.cei;
	}

	public void setCei(final String cei) {
		this.cei = cei;
	}

}
