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

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class CrypterOperation {

	private Key		key;

	private Mode	mode;

	private byte[]	data;

	private String	seed;

	public CrypterOperation(final Key key, final Mode mode, final byte[] data) {
		this(key, mode, data, null);
	}

	public CrypterOperation(final Key key, final Mode mode, final byte[] data, final String seed) {
		super();
		this.key = key;
		this.mode = mode;
		this.data = data;
		this.seed = seed;
	}

	public byte[] execute() {
		try {
			Cipher cipher = Cipher.getInstance(this.key.getAlgorithm());
			if (ConditionUtils.isNotEmpty(this.seed)) {
				SecureRandom random = new SecureRandom(this.seed.getBytes());
				cipher.init(this.mode.getMode(), this.key, random);
			} else {
				cipher.init(this.mode.getMode(), this.key);
			}
			byte[] result = cipher.doFinal(this.data);
			return result;
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	public static enum Mode {
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
