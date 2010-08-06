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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.LocaleUtils;
import net.woodstock.rockframework.utils.StringUtils;

public class SyncCrypter extends CrypterBase {

	private SecretKey	key;

	public SyncCrypter(final Algorithm algorithm, final String seed) {
		super();
		try {
			Algorithm a = algorithm;
			if (a == null) {
				a = Algorithm.DEFAULT;
			}

			KeyGenerator generator = KeyGenerator.getInstance(a.algorithm());

			if (!StringUtils.isEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes(LocaleUtils.getCharset()));
				generator.init(random);
			}

			SecretKey key = KeyGenerator.getInstance(a.algorithm()).generateKey();
			this.init(key, a);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public SyncCrypter(final InputStream inputStream) {
		super();
		Assert.notNull(inputStream, "inputStream");
		try {
			KeyData data = (KeyData) Base64Utils.unserialize(inputStream);
			SecretKey key = (SecretKey) data.getKey();
			Algorithm algorithm = Algorithm.fromString(data.getAlgorithm());
			this.init(key, algorithm);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	private void init(final SecretKey key, final Algorithm algorithm) {
		Assert.notNull(key, "key");
		this.setAlgorithm(algorithm.algorithm());
		this.key = key;
	}

	@Override
	public String encrypt(final String str) {
		try {
			if (this.getEncryptCipher() == null) {
				this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm()));
				this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.key);
			}
			byte[] bytes = str.getBytes(LocaleUtils.getCharset());
			byte[] enc = this.getEncryptCipher().doFinal(bytes);
			return new String(enc, LocaleUtils.getCharset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public String decrypt(final String str) {
		try {
			if (this.getDecryptCipher() == null) {
				this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm()));
				this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.key);
			}
			byte[] bytes = this.getDecryptCipher().doFinal(str.getBytes(LocaleUtils.getCharset()));
			return new String(bytes, LocaleUtils.getCharset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public void saveKey(final OutputStream outputStream) {
		try {
			KeyData data = new KeyData(this.key.getAlgorithm(), this.key);
			Base64Utils.serialize(data, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public static enum Algorithm {

		AES("AES"), BLOWFISH("Blowfish"), DEFAULT("DESede"), DESAES("DESAES"), DES("DES"), DESEDE("DESede"), MD5("HmacMD5"), SHA1("HmacSHA1");

		private String	algorithm;

		private Algorithm(final String algorithm) {
			this.algorithm = algorithm;
		}

		public String algorithm() {
			return this.algorithm;
		}

		public static Algorithm fromString(final String algorithm) {
			for (Algorithm s : Algorithm.values()) {
				if (s.algorithm().equalsIgnoreCase(algorithm)) {
					return s;
				}
			}
			return null;
		}

	}
}
