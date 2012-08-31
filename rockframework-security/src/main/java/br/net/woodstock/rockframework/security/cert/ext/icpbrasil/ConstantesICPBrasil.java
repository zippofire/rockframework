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

import java.nio.charset.Charset;

abstract class ConstantesICPBrasil {

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

	public static final String	OID_A1_AC_SERPRO			= ConstantesICPBrasil.PREFIX_OID_A1 + "1";

	public static final String	OID_A2_AC_SERASA			= ConstantesICPBrasil.PREFIX_OID_A2 + "1";

	public static final String	OID_A3_AC_PR				= ConstantesICPBrasil.PREFIX_OID_A3 + "1";

	public static final String	OID_A4_AC_SERASA			= ConstantesICPBrasil.PREFIX_OID_A4 + "1";

	public static final String	FORMATO_DATA				= "ddMMyyyy";

	public static final char	COMPLEMENTO_NUMERO			= '0';

	public static final char	COMPLEMENTO_TEXTO			= ' ';

	private ConstantesICPBrasil() {
		//
	}

}
