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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.X509Extension;

import br.net.woodstock.rockframework.security.config.SecurityLog;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

abstract class ICPBrasilHelper {

	public static String getValue(final String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public static String getNumericValue(final String value, final int size) {
		String s = value;
		if (s == null) {
			s = "";
		}
		return ICPBrasilHelper.lpad(s, ConstantesICPBrasil.COMPLEMENTO_NUMERO, size);
	}

	public static String getTextValue(final String value, final int size) {
		String s = value;
		if (s == null) {
			s = "";
		}
		return ICPBrasilHelper.rpad(s, ConstantesICPBrasil.COMPLEMENTO_TEXTO, size);
	}

	public static String getDateValue(final Date value) {
		Date d = value;
		if (d == null) {
			d = new Date();
		}
		return new SimpleDateFormat(ConstantesICPBrasil.FORMATO_DATA).format(d);
	}

	public static String getValueFromNumeric(final String numeric) {
		if (ConditionUtils.isEmpty(numeric)) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		char[] array = numeric.toCharArray();
		boolean add = false;
		for (int i = 0; i < array.length; i++) {
			if (add) {
				builder.append(array[i]);
			} else if (array[i] != ConstantesICPBrasil.COMPLEMENTO_NUMERO) {
				add = true;
				builder.append(array[i]);
			}
		}
		return builder.toString();
	}

	public static Date getDateFromString(final String date) throws ParseException {
		if (ConditionUtils.isEmpty(date)) {
			return null;
		}
		return new SimpleDateFormat(ConstantesICPBrasil.FORMATO_DATA).parse(date);
	}

	public static String toString(final byte[] bytes) {
		return new String(bytes, ConstantesICPBrasil.DEFAULT_CHARSET);
	}

	public static DERSequence toDerSequence(final byte[] bytes) throws IOException {
		DERSequence sequence = (DERSequence) BouncyCastleProviderHelper.toDERObject(bytes);
		return sequence;
	}

	public static String getStringFromTag(final DERTaggedObject tag) {
		DERObject obj = tag.getObject();
		return ICPBrasilHelper.getStringFromObject(obj);
	}

	private static String getStringFromObject(final Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof DERString) {
			return ((DERString) obj).getString();
		}
		if (obj instanceof DEROctetString) {
			return ICPBrasilHelper.toString(((DEROctetString) obj).getOctets());
		}
		SecurityLog.getInstance().getLogger().warn("Unknow value (" + obj.getClass() + ") " + obj);
		return null;
	}

	public static CertificadoICPBrasil getCertificadoICPBrasil(final X509Certificate certificate) throws GeneralSecurityException, IOException {
		Collection<List<?>> alternativeNames = certificate.getSubjectAlternativeNames();
		TipoPessoa tipoPessoa = TipoPessoa.DESCONHECIDO;
		TipoFormato tipoFormato = TipoFormato.DESCONHECIDO;
		CertificadoICPBrasil certificadoICPBrasil = null;

		if (ConditionUtils.isNotEmpty(alternativeNames)) {
			// Comum
			DadoPessoa dadoPessoa = null;
			String email = null;

			// PF
			String tituloEleitorPF = null;
			String ceiPF = null;
			String ricPF = null;
			String registroSINCORPF = null;
			String registroOABPF = null;

			// PJ
			String responsavelPJ = null;
			String cnpjPJ = null;
			String ceiPJ = null;
			String nomeEmpresarialPJ = null;

			for (List<?> list : alternativeNames) {
				Integer tmp = (Integer) list.get(0);
				int id = tmp != null ? tmp.intValue() : -1;
				if (id == GeneralName.otherName) {
					byte[] bytes = (byte[]) list.get(1);
					DERSequence sequence = ICPBrasilHelper.toDerSequence(bytes);
					DERObjectIdentifier identifier = (DERObjectIdentifier) sequence.getObjectAt(0);
					DERTaggedObject taggedObject = (DERTaggedObject) ((DERTaggedObject) sequence.getObjectAt(1)).getObject();

					String oid = identifier.getId();
					String value = ICPBrasilHelper.getStringFromTag(taggedObject);

					if (ConstantesICPBrasil.OID_PF_DADOS_TITULAR.equals(oid)) { // PF
						dadoPessoa = DadoPessoa.fromOtherNameString(value);
						tipoPessoa = TipoPessoa.PESSOA_FISICA;
					} else if (ConstantesICPBrasil.OID_PF_TITULO_ELEITOR.equals(oid)) {
						tituloEleitorPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ConstantesICPBrasil.OID_PF_NUMERO_CEI.equals(oid)) {
						ceiPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ConstantesICPBrasil.OID_PF_NUMERO_RIC.equals(oid)) {
						ricPF = value;
					} else if (ConstantesICPBrasil.OID_PF_REGISTRO_SINCOR.equals(oid)) {
						registroSINCORPF = value;
					} else if (ConstantesICPBrasil.OID_PF_REGISTRO_OAB.equals(oid)) {
						registroOABPF = value;
					} else if (ConstantesICPBrasil.OID_PJ_NOME_RESPONSAVEL.equals(oid)) { // PJ
						responsavelPJ = value;
					} else if (ConstantesICPBrasil.OID_PJ_NUMERO_CNPJ.equals(oid)) {
						cnpjPJ = value;
						tipoPessoa = TipoPessoa.PESSOA_JURIDICA;
					} else if (ConstantesICPBrasil.OID_PJ_DADOS_RESPONSAVEL.equals(oid)) {
						dadoPessoa = DadoPessoa.fromOtherNameString(value);
					} else if (ConstantesICPBrasil.OID_PJ_NUMERO_CEI.equals(oid)) {
						ceiPJ = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ConstantesICPBrasil.OID_PJ_NOME_EMPRESARIAL.equals(oid)) {
						nomeEmpresarialPJ = value;
					}
				} else if (id == GeneralName.rfc822Name) {
					Object obj = list.get(1);
					email = ICPBrasilHelper.getStringFromObject(obj);
				}
			}

			tipoFormato = ICPBrasilHelper.getTipoFormato(certificate);

			if ((tipoPessoa == TipoPessoa.PESSOA_FISICA) && (dadoPessoa != null)) {
				CertificadoPessoaFisicaICPBrasil certPF = new CertificadoPessoaFisicaICPBrasil(certificate);
				certPF.setCei(ceiPF);
				certPF.setDadoPessoa(dadoPessoa);
				certPF.setEmail(email);
				certPF.setTipoFormato(tipoFormato);
				certPF.setRegistroOAB(registroOABPF);
				certPF.setRegistroSINCOR(registroSINCORPF);
				certPF.setRic(ricPF);
				certPF.setTipoPessoa(tipoPessoa);
				certPF.setTituloEleitor(tituloEleitorPF);
				certificadoICPBrasil = certPF;
			} else if ((tipoPessoa == TipoPessoa.PESSOA_JURIDICA) && (dadoPessoa != null)) {
				CertificadoPessoaJuridicaICPBrasil certPJ = new CertificadoPessoaJuridicaICPBrasil(certificate);
				certPJ.setCei(ceiPJ);
				certPJ.setCnpj(cnpjPJ);
				certPJ.setDadoPessoa(dadoPessoa);
				certPJ.setEmail(email);
				certPJ.setTipoFormato(tipoFormato);
				certPJ.setNomeEmpresarial(nomeEmpresarialPJ);
				certPJ.setResponsavel(responsavelPJ);
				certPJ.setTipoPessoa(tipoPessoa);
				certificadoICPBrasil = certPJ;
			} else {
				certificadoICPBrasil = new CertificadoInvalidoICPBrasil(certificate);
				certificadoICPBrasil.setEmail(email);
			}
		} else {
			certificadoICPBrasil = new CertificadoInvalidoICPBrasil(certificate);
		}

		return certificadoICPBrasil;
	}

	private static TipoFormato getTipoFormato(final X509Certificate certificate) throws IOException {
		TipoFormato tipoFormato = TipoFormato.DESCONHECIDO;
		byte[] policiesBytes = certificate.getExtensionValue(X509Extension.certificatePolicies.getId());
		if (policiesBytes != null) {
			DEROctetString string = (DEROctetString) BouncyCastleProviderHelper.toDERObject(policiesBytes);
			byte[] octets = string.getOctets();
			DERSequence sequence = ICPBrasilHelper.toDerSequence(octets);
			PolicyInformation information = PolicyInformation.getInstance(sequence.getObjectAt(0));
			DERObjectIdentifier identifier = information.getPolicyIdentifier();
			String oid = identifier.getId();
			if (oid.startsWith(ConstantesICPBrasil.PREFIX_OID_A1)) {
				tipoFormato = TipoFormato.A1;
			} else if (oid.startsWith(ConstantesICPBrasil.PREFIX_OID_A2)) {
				tipoFormato = TipoFormato.A2;
			} else if (oid.startsWith(ConstantesICPBrasil.PREFIX_OID_A3)) {
				tipoFormato = TipoFormato.A3;
			} else if (oid.startsWith(ConstantesICPBrasil.PREFIX_OID_A4)) {
				tipoFormato = TipoFormato.A4;
			}
		}
		return tipoFormato;
	}

	private static String rpad(final String value, final char pad, final int size) {
		Assert.notNull(value, "value");
		if (value.length() > size) {
			return value.substring(0, size);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		while (builder.length() < size) {
			builder.append(pad);
		}
		return builder.toString();
	}

	private static String lpad(final String value, final char pad, final int size) {
		Assert.notNull(value, "value");
		if (value.length() > size) {
			return value.substring(0, size);
		}
		String str = value;
		while (str.length() < size) {
			str = pad + str;
		}
		return str;
	}

}
