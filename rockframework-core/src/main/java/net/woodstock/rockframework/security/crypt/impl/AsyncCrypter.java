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

import javax.crypto.Cipher;

import net.woodstock.rockframework.security.common.Charset;
import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.utils.Base64Utils;

public class AsyncCrypter extends CrypterBase<AsyncAlgorithm> {

	private PrivateKey	privateKey;

	private PublicKey	publicKey;

	protected AsyncCrypter(PrivateKey privateKey, PublicKey publicKey, AsyncAlgorithm algorithm,
			Charset charset) {
		super(algorithm, charset);

		if (publicKey == null) {
			throw new IllegalArgumentException("Public key must be not null");
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

	public String encrypt(String str) {
		if (this.privateKey == null) {
			throw new UnsupportedOperationException("Private key not found");
		}
		try {
			if (this.getEncryptCipher() == null) {
				this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
				this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.privateKey);
			}
			byte[] bytes = str.getBytes(this.getCharset().charset());
			byte[] enc = this.getEncryptCipher().doFinal(bytes);
			return Base64Utils.toBase64String(enc);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public String decrypt(String str) {
		try {
			if (this.getDecryptCipher() == null) {
				this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
				this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.publicKey);
			}
			byte[] dec = Base64Utils.fromBase64(str);
			byte[] bytes = this.getDecryptCipher().doFinal(dec);
			return new String(bytes, this.getCharset().charset());
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public void savePrivateKey(OutputStream outputStream) {
		try {
			Base64Utils.serializeTo(this.privateKey, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public void savePublicKey(OutputStream outputStream) {
		try {
			Base64Utils.serializeTo(this.privateKey, outputStream);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

	public static AsyncCrypter getInstance(InputStream privateKey, InputStream publicKey,
			AsyncAlgorithm algorithm, Charset charset) {
		try {
			if (publicKey == null) {
				throw new IllegalArgumentException("PublicKey must be not null");
			}
			if (algorithm == null) {
				algorithm = AsyncAlgorithm.DEFAULT_ASYNC;
			}
			if (charset == null) {
				charset = Charset.DEFAULT;
			}

			PrivateKey pik = null;
			PublicKey puk = (PublicKey) Base64Utils.unserializeFrom(publicKey);

			if (privateKey != null) {
				pik = (PrivateKey) Base64Utils.unserializeFrom(privateKey);
			}

			return new AsyncCrypter(pik, puk, algorithm, charset);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	public static AsyncCrypter newInstance(AsyncAlgorithm algorithm, Charset charset) {
		try {
			if (algorithm == null) {
				algorithm = AsyncAlgorithm.DEFAULT_ASYNC;
			}
			if (charset == null) {
				charset = Charset.DEFAULT;
			}
			KeyPair key = KeyPairGenerator.getInstance(algorithm.algorithm()).generateKeyPair();
			return new AsyncCrypter(key.getPrivate(), key.getPublic(), algorithm, charset);
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

}
