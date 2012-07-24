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
package br.net.woodstock.rockframework.security.cert.impl;

import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;

import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;

public abstract class BouncyCastleCertificateHelper {

	private BouncyCastleCertificateHelper() {
		//
	}

	public static int toKeyUsage(final KeyUsageType keyUsageType) {
		switch (keyUsageType) {
			case CRL_SIGN:
				return KeyUsage.cRLSign;
			case DATA_ENCIPHERMENT:
				return KeyUsage.dataEncipherment;
			case DECIPHER_ONLY:
				return KeyUsage.decipherOnly;
			case DIGITAL_SIGNATURE:
				return KeyUsage.digitalSignature;
			case ENCIPHER_ONLY:
				return KeyUsage.encipherOnly;
			case KEY_AGREEMENT:
				return KeyUsage.keyAgreement;
			case KEY_CERT_SIGN:
				return KeyUsage.keyCertSign;
			case KEY_ENCIPHERMENT:
				return KeyUsage.keyEncipherment;
			case NON_REPUDIATION:
				return KeyUsage.nonRepudiation;
			default:
				return 0;
		}
	}

	public static KeyPurposeId toExtendedKeyUsage(final ExtendedKeyUsageType keyUsageType) {
		switch (keyUsageType) {
			case ANY:
				return KeyPurposeId.anyExtendedKeyUsage;
			case CAP_WAP_AC:
				return KeyPurposeId.id_kp_capwapAC;
			case CAP_WAP_WTP:
				return KeyPurposeId.id_kp_capwapWTP;
			case CLIENT_AUTH:
				return KeyPurposeId.id_kp_clientAuth;
			case CODE_SIGN:
				return KeyPurposeId.id_kp_codeSigning;
			case DVCS:
				return KeyPurposeId.id_kp_dvcs;
			case EAP_OVER_LAN:
				return KeyPurposeId.id_kp_eapOverLAN;
			case EAP_OVER_PPP:
				return KeyPurposeId.id_kp_eapOverPPP;
			case EMAIL_PROTECTION:
				return KeyPurposeId.id_kp_emailProtection;
			case IPSEC_END_SYSTEM:
				return KeyPurposeId.id_kp_ipsecEndSystem;
			case IPSEC_IKE:
				return KeyPurposeId.id_kp_ipsecIKE;
			case IPSEC_TUNNEL:
				return KeyPurposeId.id_kp_ipsecTunnel;
			case IPSEC_USER:
				return KeyPurposeId.id_kp_ipsecUser;
			case OCSP_SIGNING:
				return KeyPurposeId.id_kp_OCSPSigning;
			case SBGP_CERT_AA_SERVER_AUTH:
				return KeyPurposeId.id_kp_sbgpCertAAServerAuth;
			case SCVP_CLIENT:
				return KeyPurposeId.id_kp_scvpClient;
			case SCVP_RESPONDER:
				return KeyPurposeId.id_kp_scvp_responder;
			case SCVP_SERVER:
				return KeyPurposeId.id_kp_scvpServer;
			case SERVER_AUTH:
				return KeyPurposeId.id_kp_serverAuth;
			case SMART_CARD_LOGIN:
				return KeyPurposeId.id_kp_smartcardlogon;
			case TIMESTAMPING:
				return KeyPurposeId.id_kp_timeStamping;
			default:
				return null;
		}
	}

}
