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
package net.woodstock.rockframework.security.digest.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.DigesterException;
import net.woodstock.rockframework.utils.LocaleUtils;

public class SimpleDigester implements Digester {

	private MessageDigest	digest;

	public SimpleDigester(final Algorithm algorithm) {
		super();
		try {
			this.digest = MessageDigest.getInstance(algorithm.algorithm());
		} catch (NoSuchAlgorithmException e) {
			throw new DigesterException(e);
		}
	}

	private synchronized byte[] digestInternal(final String data) {
		byte[] b = this.digest.digest(data.getBytes(LocaleUtils.getCharset()));
		this.digest.reset();
		return b;
	}

	@Override
	public String digest(final String data) {
		byte[] b = this.digestInternal(data);
		return new String(b, LocaleUtils.getCharset());
	}

	public static enum Algorithm {

		DEFAULT("SHA-1"), MD2("MD2"), MD5("MD5"), SHA_1("SHA-1"), SHA_256("SHA-256"), SHA_384("SHA-384,"), SHA_512("SHA-512");

		private String	algorithm;

		private Algorithm(final String algorithm) {
			this.algorithm = algorithm;
		}

		public String algorithm() {
			return this.algorithm;
		}

	}
}
