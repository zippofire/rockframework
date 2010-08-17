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

import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.CrypterException;

abstract class AbstractCrypter implements Crypter {

	private String	algorithm;

	private Cipher	encryptCipher;

	private Cipher	decryptCipher;

	protected String getAlgorithm() {
		return this.algorithm;
	}

	protected void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	protected Cipher getEncryptCipher() {
		return this.encryptCipher;
	}

	protected void setEncryptCipher(final Cipher encryptCipher) {
		this.encryptCipher = encryptCipher;
	}

	protected Cipher getDecryptCipher() {
		return this.decryptCipher;
	}

	protected void setDecryptCipher(final Cipher decryptCipher) {
		this.decryptCipher = decryptCipher;
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		try {
			byte[] enc = this.encryptCipher.doFinal(data);
			return enc;
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		try {
			byte[] dec = this.decryptCipher.doFinal(data);
			return dec;
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

}
