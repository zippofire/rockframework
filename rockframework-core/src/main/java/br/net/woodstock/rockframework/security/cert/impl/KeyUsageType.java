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

import org.bouncycastle.asn1.x509.KeyUsage;

public enum KeyUsageType {

	CRL_SIGN(KeyUsage.cRLSign),
	DATA_ENCIPHERMENT(KeyUsage.dataEncipherment),
	DECIPHER_ONLY(KeyUsage.decipherOnly),
	DIGITAL_SIGNATURE(KeyUsage.digitalSignature),
	ENCIPHER_ONLY(KeyUsage.encipherOnly),
	KEY_AGREEMENT(KeyUsage.keyAgreement),
	KEY_CERT_SIGN(KeyUsage.keyCertSign),
	KEY_ENCIPHERMENT(KeyUsage.keyEncipherment),
	NON_REPUDIATION(KeyUsage.nonRepudiation);

	private int	usage;

	private KeyUsageType(final int usage) {
		this.usage = usage;
	}

	public int getUsage() {
		return this.usage;
	}

}
