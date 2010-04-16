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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.LocaleUtils;
import net.woodstock.rockframework.utils.StringUtils;

public class AsyncCrypter extends CrypterBase {

	private static final int	DEFAULT_KEY_SIZE	= 1024;

	private PrivateKey			privateKey;

	private PublicKey			publicKey;

	public AsyncCrypter(final Algorithm algorithm, final String seed) {
		try {
			Algorithm a = algorithm;
			if (a == null) {
				a = Algorithm.DEFAULT_ASYNC;
			}

			KeyPairGenerator generator = KeyPairGenerator.getInstance(a.algorithm());

			if (!StringUtils.isEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes(LocaleUtils.getCharset()));
				generator.initialize(AsyncCrypter.DEFAULT_KEY_SIZE, random);
			}

			KeyPair key = generator.generateKeyPair();
			this.init(key.getPrivate(), key.getPublic(), a);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public AsyncCrypter(final InputStream privateKey, final InputStream publicKey) {
		try {
			if (publicKey == null) {
				throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_NULL, "Public Key"));
			}

			KeyData publicData = (KeyData) Base64Utils.unserialize(publicKey);

			PrivateKey pik = null;
			PublicKey puk = (PublicKey) publicData.getKey();
			Algorithm algorithm = Algorithm.fromString(publicData.getAlgorithm());

			if (privateKey != null) {
				KeyData privateData = (KeyData) Base64Utils.unserialize(publicKey);
				pik = (PrivateKey) privateData.getKey();
			}

			this.init(pik, puk, algorithm);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	private void init(final PrivateKey privateKey, final PublicKey publicKey, final Algorithm algorithm) {
		this.setAlgorithm(algorithm.algorithm());

		if (publicKey == null) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_NULL, "Public Key"));
		}

		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public String encrypt(final String str) {
		if (this.privateKey == null) {
			throw new UnsupportedOperationException("Private key not found");
		}
		try {
			if (this.getEncryptCipher() == null) {
				this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm()));
				this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.privateKey);
			}
			byte[] bytes = str.getBytes(LocaleUtils.getCharset());
			byte[] enc = this.getEncryptCipher().doFinal(bytes);
			return new String(enc, LocaleUtils.getCharset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public String decrypt(final String str) {
		try {
			if (this.getDecryptCipher() == null) {
				this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm()));
				this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.publicKey);
			}
			byte[] bytes = this.getDecryptCipher().doFinal(str.getBytes(LocaleUtils.getCharset()));
			return new String(bytes, LocaleUtils.getCharset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public void savePrivateKey(final OutputStream outputStream) {
		try {
			Base64Utils.serialize(this.privateKey, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public void savePublicKey(final OutputStream outputStream) {
		try {
			Base64Utils.serialize(this.privateKey, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public static enum Algorithm {

		DEFAULT_ASYNC("RSA"), DSA("DSA"), DIFFIE_HELLMAN("DiffieHellman"), RSA("RSA");

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
