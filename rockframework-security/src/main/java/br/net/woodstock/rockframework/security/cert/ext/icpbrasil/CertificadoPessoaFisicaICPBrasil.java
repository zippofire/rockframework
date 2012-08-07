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

public class CertificadoPessoaFisicaICPBrasil extends CertificadoICPBrasil {

	private Date	dataNascimento;

	private String	cpf;

	private String	pis;

	private String	rg;

	private String	emissorRG;

	private String	tituloEleitor;

	private String	cei;

	private String	registroOAB;

	public CertificadoPessoaFisicaICPBrasil(final X509Certificate certificate, final FormatoICPBrasilType formato) {
		super(certificate, TipoICPBrasilType.PESSOA_FISICA, formato);
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}

	public String getPis() {
		return this.pis;
	}

	public void setPis(final String pis) {
		this.pis = pis;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(final String rg) {
		this.rg = rg;
	}

	public String getEmissorRG() {
		return this.emissorRG;
	}

	public void setEmissorRG(final String emissorRG) {
		this.emissorRG = emissorRG;
	}

	public String getTituloEleitor() {
		return this.tituloEleitor;
	}

	public void setTituloEleitor(final String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public String getCei() {
		return this.cei;
	}

	public void setCei(final String cei) {
		this.cei = cei;
	}

	public String getRegistroOAB() {
		return this.registroOAB;
	}

	public void setRegistroOAB(final String registroOAB) {
		this.registroOAB = registroOAB;
	}

}
