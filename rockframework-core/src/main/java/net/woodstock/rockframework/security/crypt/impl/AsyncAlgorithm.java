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
package net.woodstock.rockframework.security.crypt.impl;

public enum AsyncAlgorithm {

	DEFAULT_ASYNC("RSA"),
	DSA("DSA"),
	DIFFIE_HELLMAN("DiffieHellman"),
	RSA("RSA");

	private String	algorithm;

	private AsyncAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String algorithm() {
		return this.algorithm;
	}
	
	public static AsyncAlgorithm fromString(String algorithm) {
		for (AsyncAlgorithm s : AsyncAlgorithm.values()) {
			if(s.algorithm().equalsIgnoreCase(algorithm)) {
				return s;
			}
		}
		return null;
	}

}
