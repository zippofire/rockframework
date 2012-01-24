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
package br.net.woodstock.rockframework.security.digest;

public enum DigestType {

	MD2("MD2"), MD5("MD5"), SHA1("SHA1"), SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512("SHA-512");

	private String	algorithm;

	private DigestType(final String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public static DigestType getDigestType(final String algorithm) {
		for (DigestType digestType : DigestType.values()) {
			if (digestType.getAlgorithm().equalsIgnoreCase(algorithm)) {
				return digestType;
			}
		}
		return null;
	}

}
