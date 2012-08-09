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

	private String	tituloEleitor;

	private String	cei;

	private String	ric;

	private String	registroSINCOR;

	private String	registroOAB;

	public CertificadoPessoaFisicaICPBrasil(final X509Certificate certificate) {
		super(certificate);
		this.setTipo(TipoICPBrasilType.PESSOA_FISICA);
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

	public String getRic() {
		return this.ric;
	}

	public void setRic(final String ric) {
		this.ric = ric;
	}

	public String getRegistroSINCOR() {
		return this.registroSINCOR;
	}

	public void setRegistroSINCOR(final String registroSINCOR) {
		this.registroSINCOR = registroSINCOR;
	}

	public String getRegistroOAB() {
		return this.registroOAB;
	}

	public void setRegistroOAB(final String registroOAB) {
		this.registroOAB = registroOAB;
	}

	// Delegate
	public Date getDataNascimento() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getDataNascimento();
	}

	public String getCpf() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getCpf();
	}

	public String getPis() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getPis();
	}

	public String getRg() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getRg();
	}

	public String getEmissorRG() {
		if (this.getDadoPessoa() == null) {
			return null;
		}
		return this.getDadoPessoa().getEmissorRG();
	}

}
