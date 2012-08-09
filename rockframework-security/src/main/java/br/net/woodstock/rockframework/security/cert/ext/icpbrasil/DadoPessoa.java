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

import java.io.Serializable;
import java.util.Date;

import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class DadoPessoa implements Serializable {

	private static final long	serialVersionUID	= -6128483342965724474L;

	private Date				dataNascimento;

	private String				cpf;

	private String				pis;

	private String				rg;

	private String				emissorRG;

	public DadoPessoa() {
		super();
	}

	public DadoPessoa(final Date dataNascimento, final String cpf, final String pis, final String rg, final String emissorRG) {
		super();
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.pis = pis;
		this.rg = rg;
		this.emissorRG = emissorRG;
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

	// Aux
	@Override
	public String toString() {
		return DadoPessoa.toOtherNameString(this);
	}

	public static DadoPessoa fromOtherNameString(final String str) {
		if (ConditionUtils.isEmpty(str)) {
			return null;
		}
		try {
			Date dataNascimento = null;
			String dataStr = str.substring(0, 8); // Data Nascimento
			String cpf = ICPBrasilHelper.getValueFromNumeric(str.substring(8, 19)); // CPF
			String pis = ICPBrasilHelper.getValueFromNumeric(str.substring(20, 30)); // PIS
			String rg = ICPBrasilHelper.getValueFromNumeric(str.substring(30, 45)); // RG
			String emissorRG = str.substring(45).trim(); // Emissor RG

			if ((ConditionUtils.isNotEmpty(dataStr)) && (ConditionUtils.isNotEmpty(dataStr.replaceAll("0", "")))) {
				dataNascimento = ICPBrasilHelper.getDateFromString(dataStr);
			}

			return new DadoPessoa(dataNascimento, cpf, pis, rg, emissorRG);
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	public static String toOtherNameString(final DadoPessoa dadoPessoa) {
		if (dadoPessoa == null) {
			return null;
		}
		String dataNascimento = ICPBrasilHelper.getDateValue(dadoPessoa.getDataNascimento());
		String cpf = ICPBrasilHelper.getNumericValue(dadoPessoa.getCpf(), 11);
		String pis = ICPBrasilHelper.getNumericValue(dadoPessoa.getPis(), 11);
		String rg = ICPBrasilHelper.getNumericValue(dadoPessoa.getRg(), 15);
		String emissorRG = ICPBrasilHelper.getValue(dadoPessoa.getEmissorRG());

		String str = dataNascimento + cpf + pis + rg + emissorRG;
		return str;
	}

}
