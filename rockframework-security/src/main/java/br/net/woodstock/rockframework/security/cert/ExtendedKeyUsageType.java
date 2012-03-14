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
package br.net.woodstock.rockframework.security.cert;


public enum ExtendedKeyUsageType {

	ANY,
	SERVER_AUTH,
	CLIENT_AUTH,
	CODE_SIGN,
	EMAIL_PROTECTION,
	IPSEC_END_SYSTEM,
	IPSEC_TUNNEL,
	IPSEC_USER,
	TIMESTAMPING,
	OCSP_SIGNING,
	DVCS,
	SBGP_CERT_AA_SERVER_AUTH,
	SCVP_RESPONDER,
	EAP_OVER_PPP,
	EAP_OVER_LAN,
	SCVP_SERVER,
	SCVP_CLIENT,
	IPSEC_IKE,
	CAP_WAP_AC,
	CAP_WAP_WTP,
	SMART_CARD_LOGIN;

}
