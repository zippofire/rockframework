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

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.x509.GeneralName;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

abstract class ICPBrasilHelper {

	public static final Charset	DEFAULT_CHARSET				= Charset.forName("ISO-8859-1");

	public static final String	OID_PF_DADOS_TITULAR		= "2.16.76.1.3.1";

	public static final String	OID_PF_TITULO_ELEITOR		= "2.16.76.1.3.5";

	public static final String	OID_PF_NUMERO_CEI			= "2.16.76.1.3.6";

	public static final String	OID_PF_REGISTRO_OAB			= "2.16.76.1.4.2.1";

	public static final String	OID_PJ_NOME_RESPONSAVEL		= "2.16.76.1.3.2";

	public static final String	OID_PJ_NUMERO_CNPJ			= "2.16.76.1.3.3";

	public static final String	OID_PJ_DADOS_RESPONSAVEL	= "2.16.76.1.3.4";

	public static final String	OID_PJ_NUMERO_CEI			= "2.16.76.1.3.7";

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

	public static String toString(final byte[] bytes) {
		return new String(bytes, ICPBrasilHelper.DEFAULT_CHARSET);
	}

	public static DERSequence toDerSequence(final byte[] bytes) throws IOException {
		ASN1InputStream inputStream = new ASN1InputStream(bytes);
		DERSequence sequence = (DERSequence) inputStream.readObject();
		return sequence;
	}

	public static CertificadoICPBrasil getCertificadoICPBrasil(final X509Certificate certificate) throws GeneralSecurityException, IOException, ParseException {
		Collection<List<?>> alternativeNames = certificate.getSubjectAlternativeNames();
		if (ConditionUtils.isNotEmpty(alternativeNames)) {
			CertificadoICPBrasilType type = CertificadoICPBrasilType.INVALIDO;
			// Comum
			String email = null;

			// PF
			Date dataNascimentoPF = null;
			String cpfPF = null;
			String pisPF = null;
			String rgPF = null;
			String emissorRGPF = null;
			String tituloEleitorPF = null;
			String ceiPF = null;
			String registroOABPF = null;

			// PJ
			String responsavelPJ = null;
			String cnpjPJ = null;
			Date dataNascimentoResponsavelPJ = null;
			String cpfResponsavelPJ = null;
			String pisResponsavelPJ = null;
			String rgResponsavelPJ = null;
			String emissorRGResponsavelPJ = null;
			String ceiPJ = null;

			for (List<?> list : alternativeNames) {
				Integer tmp = (Integer) list.get(0);
				int id = tmp != null ? tmp.intValue() : -1;
				if (id == GeneralName.otherName) {
					byte[] bytes = (byte[]) list.get(1);
					DERSequence sequence = ICPBrasilHelper.toDerSequence(bytes);
					DERObjectIdentifier identifier = (DERObjectIdentifier) sequence.getObjectAt(0);
					DERTaggedObject taggedObject = (DERTaggedObject) ((DERTaggedObject) sequence.getObjectAt(1)).getObject();

					String oid = identifier.getId();
					String value = null;

					if (taggedObject.getTagNo() == DERTags.BIT_STRING) {
						DERString string = (DERString) taggedObject.getObject();
						value = string.getString();
					} else if (taggedObject.getTagNo() == DERTags.OCTET_STRING) {
						DEROctetString string = (DEROctetString) taggedObject.getObject();
						value = ICPBrasilHelper.toString(string.getOctets());
					}

					// PF
					if (ICPBrasilHelper.OID_PF_DADOS_TITULAR.equals(oid)) {
						Object[] dadoTitular = ICPBrasilHelper.toDadoPessoal(value);
						dataNascimentoPF = (Date) dadoTitular[0];
						cpfPF = (String) dadoTitular[1];
						pisPF = (String) dadoTitular[2];
						rgPF = (String) dadoTitular[3];
						emissorRGPF = (String) dadoTitular[4];

						type = CertificadoICPBrasilType.PESSOA_FISICA;
					} else if (ICPBrasilHelper.OID_PF_TITULO_ELEITOR.equals(oid)) {
						tituloEleitorPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ICPBrasilHelper.OID_PF_NUMERO_CEI.equals(oid)) {
						ceiPF = ICPBrasilHelper.getValueFromNumeric(value);
					} else if (ICPBrasilHelper.OID_PF_REGISTRO_OAB.equals(oid)) {
						registroOABPF = value;
					}
					// PJ
					else if (ICPBrasilHelper.OID_PJ_NOME_RESPONSAVEL.equals(oid)) {
						responsavelPJ = value;
					} else if (ICPBrasilHelper.OID_PJ_NUMERO_CNPJ.equals(oid)) {
						cnpjPJ = value;
						type = CertificadoICPBrasilType.PESSOA_JURIDICA;
					} else if (ICPBrasilHelper.OID_PJ_DADOS_RESPONSAVEL.equals(oid)) {
						Object[] dadoResponsavel = ICPBrasilHelper.toDadoPessoal(value);
						dataNascimentoResponsavelPJ = (Date) dadoResponsavel[0];
						cpfResponsavelPJ = (String) dadoResponsavel[1];
						pisResponsavelPJ = (String) dadoResponsavel[2];
						rgResponsavelPJ = (String) dadoResponsavel[3];
						emissorRGResponsavelPJ = (String) dadoResponsavel[4];
					} else if (ICPBrasilHelper.OID_PJ_NUMERO_CEI.equals(oid)) {
						ceiPJ = ICPBrasilHelper.getValueFromNumeric(value);
					}
				} else if (id == GeneralName.rfc822Name) {
					Object obj = list.get(1);
					if (obj instanceof String) {
						email = (String) obj;
					} else if (obj instanceof DERIA5String) {
						email = ((DERIA5String) obj).getString();
					} else {
						CoreLog.getInstance().getLog().warning("Unknow RFC822 value " + obj);
					}
				}
			}

			if (type == CertificadoICPBrasilType.PESSOA_FISICA) {
				CertificadoPessoaFisicaICPBrasil certPF = new CertificadoPessoaFisicaICPBrasil(certificate);
				certPF.setCei(ceiPF);
				certPF.setCpf(cpfPF);
				certPF.setDataNascimento(dataNascimentoPF);
				certPF.setEmail(email);
				certPF.setEmissorRG(emissorRGPF);
				certPF.setPis(pisPF);
				certPF.setRegistroOAB(registroOABPF);
				certPF.setRg(rgPF);
				certPF.setTituloEleitor(tituloEleitorPF);
				return certPF;
			} else if (type == CertificadoICPBrasilType.PESSOA_JURIDICA) {
				CertificadoPessoaJuridicaICPBrasil certPJ = new CertificadoPessoaJuridicaICPBrasil(certificate);
				certPJ.setCei(ceiPJ);
				certPJ.setCnpj(cnpjPJ);
				certPJ.setCpfResponsavel(cpfResponsavelPJ);
				certPJ.setDataNascimentoResponsavel(dataNascimentoResponsavelPJ);
				certPJ.setEmail(email);
				certPJ.setEmissorRGResponsavel(emissorRGResponsavelPJ);
				certPJ.setPisResponsavel(pisResponsavelPJ);
				certPJ.setResponsavel(responsavelPJ);
				certPJ.setRgResponsavel(rgResponsavelPJ);
				return certPJ;
			}
		}

		return new CertificadoInvalidoICPBrasil(certificate);
	}

	private static Object[] toDadoPessoal(final String value) throws ParseException {
		Object[] dadoPessoal = new Object[5];

		if (ConditionUtils.isNotEmpty(value)) {
			String dataStr = value.substring(0, 8);
			dadoPessoal[1] = ICPBrasilHelper.getValueFromNumeric(value.substring(8, 19)); // CPF
			dadoPessoal[2] = ICPBrasilHelper.getValueFromNumeric(value.substring(20, 30)); // PIS
			dadoPessoal[3] = ICPBrasilHelper.getValueFromNumeric(value.substring(30, 45)); // RG
			dadoPessoal[4] = value.substring(45).trim(); // Emissor RG

			if ((ConditionUtils.isNotEmpty(dataStr)) && (ConditionUtils.isNotEmpty(dataStr.replaceAll("0", "")))) {
				dadoPessoal[0] = new SimpleDateFormat(ICPBrasilHelper.DATE_FORMAT).parse(dataStr); // Data Nascimento
			}
		}

		return dadoPessoal;
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
