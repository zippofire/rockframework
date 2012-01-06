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
package br.net.woodstock.rockframework.security.sign;

import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public enum SignType {

	MD2_RSA("MD2withRSA", KeyPairType.RSA, DigestType.MD2),
	MD5_RSA("MD5withRSA", KeyPairType.RSA, DigestType.MD5),
	SHA1_RSA("SHA1withRSA", KeyPairType.RSA, DigestType.SHA1),
	SHA256_RSA("SHA256withRSA", KeyPairType.RSA, DigestType.SHA_256),
	SHA384_RSA("SHA384withRSA", KeyPairType.RSA, DigestType.SHA_384),
	SHA512_RSA("SHA512withRSA", KeyPairType.RSA, DigestType.SHA_512),
	SHA1_DSA("SHA1withDSA", KeyPairType.DSA, DigestType.SHA1),
	SHA1_ECDSA("SHA1withECDSA", KeyPairType.EC, DigestType.SHA1),
	SHA256_ECDSA("SHA256withECDSA", KeyPairType.EC, DigestType.SHA_256),
	SHA384_ECDSA("SHA384withECDSA", KeyPairType.EC, DigestType.SHA_384),
	SHA512_ECDSA("SHA512withECDSA", KeyPairType.EC, DigestType.SHA_512),
	NONE_DSA("NONEwithDSA", KeyPairType.DSA, null),
	NONE_ECDSA("NONEwithECDSA", KeyPairType.EC, null);

	private String		algorithm;

	private KeyPairType keyPairType;
	
	private DigestType	digestType;

	private SignType(final String algorithm, final KeyPairType keyPairType, final DigestType digestType) {
		this.algorithm = algorithm;
		this.keyPairType = keyPairType;
		this.digestType = digestType;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}
	
	
	public KeyPairType getKeyPairType() {
		return this.keyPairType;
	}	

	public DigestType getDigestType() {
		return this.digestType;
	}

	public static SignType getSignTypeFromAlgorithm(final String algorithm) {
		if (ConditionUtils.isNotEmpty(algorithm)) {
			for (SignType signType : SignType.values()) {
				if (algorithm.equalsIgnoreCase(signType.getAlgorithm())) {
					return signType;
				}
				if (algorithm.toLowerCase().startsWith(signType.getAlgorithm().toLowerCase())) {
					return signType;
				}
			}
		}
		return null;
	}

}
