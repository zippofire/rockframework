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
package br.net.woodstock.rockframework.security.crypt.impl;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class CrypterOperation {

	private Key		key;

	private Mode	mode;

	private byte[]	data;

	public CrypterOperation(final Key key, final Mode mode, final byte[] data) {
		super();
		this.key = key;
		this.mode = mode;
		this.data = data;
	}

	public byte[] execute() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(this.key.getAlgorithm());
		cipher.init(this.mode.getMode(), this.key);
		// cipher.update(this.data);
		byte[] result = cipher.doFinal(this.data);
		return result;
	}

	enum Mode {
		ENCRYPT(Cipher.ENCRYPT_MODE), DECRYPT(Cipher.DECRYPT_MODE);

		private int	mode;

		private Mode(final int mode) {
			this.mode = mode;
		}

		public int getMode() {
			return this.mode;
		}
	}

}
