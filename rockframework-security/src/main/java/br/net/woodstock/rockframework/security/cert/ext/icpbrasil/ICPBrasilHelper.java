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
import java.nio.charset.Charset;
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

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

abstract class ICPBrasilHelper {

	// OIDs baseados no documento do ITI, versao 2.11 de 05/06/212
	// http://www.iti.gov.br/twiki/pub/Certificacao/AdeIcp/ADE_ICP-04.01.pdf

	public static final Charset	DEFAULT_CHARSET				= Charset.forName("ISO-8859-1");

	public static final String	OID_PF_DADOS_TITULAR		= "2.16.76.1.3.1";

	public static final String	OID_PF_TITULO_ELEITOR		= "2.16.76.1.3.5";

	public static final String	OID_PF_NUMERO_CEI			= "2.16.76.1.3.6";

	// Registro de Identidade Civil
	public static final String	OID_PF_NUMERO_RIC			= "2.16.76.1.3.9";

	public static final String	OID_PF_REGISTRO_SINCOR		= "2.16.76.1.4.1.1.1";

	public static final String	OID_PF_REGISTRO_OAB			= "2.16.76.1.4.2.1.1";

	public static final String	OID_PJ_NOME_RESPONSAVEL		= "2.16.76.1.3.2";

	public static final String	OID_PJ_NUMERO_CNPJ			= "2.16.76.1.3.3";

	public static final String	OID_PJ_DADOS_RESPONSAVEL	= "2.16.76.1.3.4";

	public static final String	OID_PJ_NUMERO_CEI			= "2.16.76.1.3.7";

	public static final String	OID_PJ_NOME_EMPRESARIAL		= "2.16.76.1.3.8";

	public static final String	PREFIX_OID_A1				= "2.16.76.1.2.1.";

	public static final String	PREFIX_OID_A2				= "2.16.76.1.2.2.";

	public static final String	PREFIX_OID_A3				= "2.16.76.1.2.3.";

	public static final String	PREFIX_OID_A4				= "2.16.76.1.2.4.";

	public static final String	PREFIX_OID_AC_ICPBRASIL		= "2.16.76.1.2.201";

	public static final String	OID_A1_AC_SERPRO			= ICPBrasilHelper.PREFIX_OID_A1 + "1";

	public static final String	OID_A2_AC_SERASA			= ICPBrasilHelper.PREFIX_OID_A2 + "1";

	public static final String	OID_A3_AC_PR				= ICPBrasilHelper.PREFIX_OID_A3 + "1";

	public static final String	OID_A4_AC_SERASA			= ICPBrasilHelper.PREFIX_OID_A4 + "1";

	private static final String	DATE_FORMAT					= "ddMMyyyy";

	private static final char	NUMBER_PAD					= '0';

	private static final char	TEXT_PAD					= ' ';

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
		return ICPBrasilHelper.lpad(s, ICPBrasilHelper.NUMBER_PAD, size);
	}

	public static String getTextValue(final String value, final int size) {
		String s = value;
		if (s == null) {
			s = "";
		}
		return ICPBrasilHelper.rpad(s, ICPBrasilHelper.TEXT_PAD, size);
	}

	public static String getDateValue(final Date value) {
		Date d = value;
		if (d == null) {
			d = new Date();
		}
		return new SimpleDateFormat(ICPBrasilHelper.DATE_FORMAT).format(d);
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
			} else if (array[i] != ICPBrasilHelper.NUMBER_PAD) {
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
		return new SimpleDateFormat(ICPBrasilHelper.DATE_FORMAT).parse(date);
	}

	public static String toString(final byte[] bytes) {
		return new String(bytes, ICPBrasilHelper.DEFAULT_CHARSET);
	}

	public static DERObject toDerObject(final byte[] bytes) throws IOException {
		return BouncyCastleProviderHelper.toDERObject(bytes);
	}

	public static DERSequence toDerSequence(final byte[] bytes) throws IOException {
		DERSequence sequence = (DERSequence) ICPBrasilHelper.toDerObject(bytes);
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
		CoreLog.getInstance().getLog().warning("Unknow value (" + obj.getClass() + ") " + obj);
		return null;
	}

	public static CertificadoICPBrasil getCertificadoICPBrasil(final X509Certificate certificate) throws GeneralSecurityException, IOException {
		Collection<List<?>> alternativeNames = certificate.getSubjectAlternativeNames();
		TipoICPBrasilType tipo = TipoICPBrasilType.INVALIDO;
		FormatoICPBrasilType formato = FormatoICPBrasilType.INVALIDO;
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

					if (ICPBrasilHelper.OID_PF_DADOS_TITULAR.equals(oid)) { // PF
						dadoPessoa = DadoPessoa.fromOtherNameString(value);
						tipo = TipoICPBrasilType.PESSOA_FISICA;
					} else if (ICPBrasilHelper.OID_PF_TITULO_ELEITOR.equals(oid)) {
						tituloEleitorPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ICPBrasilHelper.OID_PF_NUMERO_CEI.equals(oid)) {
						ceiPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ICPBrasilHelper.OID_PF_NUMERO_RIC.equals(oid)) {
						ricPF = value;
					} else if (ICPBrasilHelper.OID_PF_REGISTRO_SINCOR.equals(oid)) {
						registroSINCORPF = value;
					} else if (ICPBrasilHelper.OID_PF_REGISTRO_OAB.equals(oid)) {
						registroOABPF = value;
					} else if (ICPBrasilHelper.OID_PJ_NOME_RESPONSAVEL.equals(oid)) { // PJ
						responsavelPJ = value;
					} else if (ICPBrasilHelper.OID_PJ_NUMERO_CNPJ.equals(oid)) {
						cnpjPJ = value;
						tipo = TipoICPBrasilType.PESSOA_JURIDICA;
					} else if (ICPBrasilHelper.OID_PJ_DADOS_RESPONSAVEL.equals(oid)) {
						dadoPessoa = DadoPessoa.fromOtherNameString(value);
					} else if (ICPBrasilHelper.OID_PJ_NUMERO_CEI.equals(oid)) {
						ceiPJ = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ICPBrasilHelper.OID_PJ_NOME_EMPRESARIAL.equals(oid)) {
						nomeEmpresarialPJ = value;
					}
				} else if (id == GeneralName.rfc822Name) {
					Object obj = list.get(1);
					email = ICPBrasilHelper.getStringFromObject(obj);
				}
			}

			formato = ICPBrasilHelper.getFormato(certificate);

			if ((tipo == TipoICPBrasilType.PESSOA_FISICA) && (dadoPessoa != null)) {
				CertificadoPessoaFisicaICPBrasil certPF = new CertificadoPessoaFisicaICPBrasil(certificate);
				certPF.setCei(ceiPF);
				certPF.setDadoPessoa(dadoPessoa);
				certPF.setEmail(email);
				certPF.setFormato(formato);
				certPF.setRegistroOAB(registroOABPF);
				certPF.setRegistroSINCOR(registroSINCORPF);
				certPF.setRic(ricPF);
				certPF.setTipo(tipo);
				certPF.setTituloEleitor(tituloEleitorPF);
				certificadoICPBrasil = certPF;
			} else if ((tipo == TipoICPBrasilType.PESSOA_JURIDICA) && (dadoPessoa != null)) {
				CertificadoPessoaJuridicaICPBrasil certPJ = new CertificadoPessoaJuridicaICPBrasil(certificate);
				certPJ.setCei(ceiPJ);
				certPJ.setCnpj(cnpjPJ);
				certPJ.setDadoPessoa(dadoPessoa);
				certPJ.setEmail(email);
				certPJ.setFormato(formato);
				certPJ.setNomeEmpresarial(nomeEmpresarialPJ);
				certPJ.setResponsavel(responsavelPJ);
				certPJ.setTipo(tipo);
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

	private static FormatoICPBrasilType getFormato(final X509Certificate certificate) throws IOException {
		FormatoICPBrasilType formato = FormatoICPBrasilType.INVALIDO;
		byte[] policiesBytes = certificate.getExtensionValue(X509Extension.certificatePolicies.getId());
		if (policiesBytes != null) {
			DEROctetString string = (DEROctetString) ICPBrasilHelper.toDerObject(policiesBytes);
			byte[] octets = string.getOctets();
			DERSequence sequence = ICPBrasilHelper.toDerSequence(octets);
			PolicyInformation information = PolicyInformation.getInstance(sequence.getObjectAt(0));
			DERObjectIdentifier identifier = information.getPolicyIdentifier();
			String oid = identifier.getId();
			if (oid.startsWith(ICPBrasilHelper.PREFIX_OID_A1)) {
				formato = FormatoICPBrasilType.A1;
			} else if (oid.startsWith(ICPBrasilHelper.PREFIX_OID_A2)) {
				formato = FormatoICPBrasilType.A2;
			} else if (oid.startsWith(ICPBrasilHelper.PREFIX_OID_A3)) {
				formato = FormatoICPBrasilType.A3;
			} else if (oid.startsWith(ICPBrasilHelper.PREFIX_OID_A4)) {
				formato = FormatoICPBrasilType.A4;
			}
		}
		return formato;
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
