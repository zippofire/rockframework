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

import net.woodstock.rockframework.security.common.Charset;
import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.StringUtils;

public class SyncCrypter extends CrypterBase<SyncAlgorithm> {

	private SecretKey	key;

	private SyncCrypter(SecretKey key, SyncAlgorithm algorithm, Charset charset) {
		super(algorithm, charset);
		if (key == null) {
			throw new IllegalArgumentException("Key must be not null");
		}
		this.key = key;
	}

	public SecretKey getKey() {
		return this.key;
	}

	public String encrypt(String str) {
		try {
			if (this.getEncryptCipher() == null) {
				this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
				this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.key);
			}
			byte[] bytes = str.getBytes(this.getCharset().charset());
			byte[] enc = this.getEncryptCipher().doFinal(bytes);
			return new String(Base64Utils.toBase64(enc));
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public String decrypt(String str) {
		try {
			if (this.getDecryptCipher() == null) {
				this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
				this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.key);
			}
			byte[] dec = Base64Utils.fromBase64(str.getBytes());
			byte[] bytes = this.getDecryptCipher().doFinal(dec);
			return new String(bytes, this.getCharset().charset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public void saveKey(OutputStream outputStream) {
		try {
			KeyData data = new KeyData(this.key.getAlgorithm(), this.key, this.getCharset());
			Base64Utils.serializeTo(data, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public static SyncCrypter getInstance(InputStream inputStream) {
		try {
			if (inputStream == null) {
				throw new IllegalArgumentException("InputStream must be not null");
			}

			KeyData data = (KeyData) Base64Utils.unserializeFrom(inputStream);
			SecretKey key = (SecretKey) data.getKey();
			SyncAlgorithm algorithm = SyncAlgorithm.fromString(data.getAlgorithm());
			return new SyncCrypter(key, algorithm, data.getCharset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public static SyncCrypter newInstance(String seed) {
		return SyncCrypter.newInstance(null, null, seed);
	}

	public static SyncCrypter newInstance(SyncAlgorithm algorithm, Charset charset, String seed) {
		try {
			if (algorithm == null) {
				algorithm = SyncAlgorithm.DEFAULT_SYNC;
			}
			if (charset == null) {
				charset = Charset.DEFAULT;
			}

			KeyGenerator generator = KeyGenerator.getInstance(algorithm.algorithm());

			if (!StringUtils.isEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes());
				generator.init(random);
			}

			SecretKey key = KeyGenerator.getInstance(algorithm.algorithm()).generateKey();
			return new SyncCrypter(key, algorithm, charset);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}
}
