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
package net.woodstock.rockframework.security.crypt;

public enum SignatureType {

	MD2_RSA("MD2withRSA"),
	MD5_RSA("MD5withRSA"),
	SHA1_RSA("SHA1withRSA"),
	SHA256_RSA("SHA256withRSA"),
	SHA384_RSA("SHA384withRSA"),
	SHA512_RSA("SHA512withRSA"),
	SHA1_DSA("SHA1withDSA"),
	NONE_DSA("NONEwithDSA"),
	SHA1_ECDSA("SHA1withECDSA"),
	SHA256_ECDSA("SHA256withECDSA"),
	SHA384_ECDSA("SHA384withECDSA"),
	SHA512_ECDSA("SHA512withECDSA"),
	NONE_ECDSA("NONEwithECDSA");

	private String	algorithm;

	private SignatureType(final String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

}
