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

import javax.crypto.Cipher;

import net.woodstock.rockframework.security.common.Charset;
import net.woodstock.rockframework.security.crypt.Crypter;

abstract class CrypterBase<A> implements Crypter {

	private A		algorithm;

	private Charset	charset;

	private Cipher	encryptCipher;

	private Cipher	decryptCipher;

	protected CrypterBase(final A algorithm, final Charset charset) {
		this.algorithm = algorithm;
		this.charset = charset;
	}

	public A getAlgorithm() {
		return this.algorithm;
	}

	protected void setAlgorithm(final A algorithm) {
		this.algorithm = algorithm;
	}

	public Charset getCharset() {
		return this.charset;
	}

	protected void setCharset(final Charset charset) {
		this.charset = charset;
	}

	public Cipher getEncryptCipher() {
		return this.encryptCipher;
	}

	public void setEncryptCipher(final Cipher encryptCipher) {
		this.encryptCipher = encryptCipher;
	}

	public Cipher getDecryptCipher() {
		return this.decryptCipher;
	}

	public void setDecryptCipher(final Cipher decryptCipher) {
		this.decryptCipher = decryptCipher;
	}

}
