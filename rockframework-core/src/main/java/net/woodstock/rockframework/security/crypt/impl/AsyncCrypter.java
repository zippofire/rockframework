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
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.security.crypt.KeyPairType;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.IOUtils;
import net.woodstock.rockframework.utils.LocaleUtils;
import net.woodstock.rockframework.utils.StringUtils;

public class AsyncCrypter extends AbstractCrypter {

	private static final int	DEFAULT_KEY_SIZE	= 1024;

	private static final String	PRIVATE_KEY_BEFORE	= "-----BEGIN PRIVATE KEY-----\n";

	private static final String	PRIVATE_KEY_AFTER	= "\n-----END PRIVATE KEY-----\n";

	private static final String	PUBLIC_KEY_BEFORE	= "-----BEGIN PUBLIC KEY-----\n";

	private static final String	PUBLIC_KEY_AFTER	= "\n-----END PUBLIC KEY-----\n";

	private PrivateKey			privateKey;

	private PublicKey			publicKey;

	private AsyncCrypter(final PrivateKey privateKey, final PublicKey publicKey, final KeyPairType type) {
		super();
		Assert.notNull(type, "type");
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.setAlgorithm(type.getType());
		try {
			this.initCiphers();
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	public AsyncCrypter(final KeyPairType type) {
		this(type, null);
	}

	public AsyncCrypter(final KeyPairType type, final String seed) {
		super();
		Assert.notNull(type, "type");
		try {
			this.initKeys(type, seed);
			this.initCiphers();
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	private void initKeys(final KeyPairType type, final String seed) throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(type.getType());

		if (!StringUtils.isEmpty(seed)) {
			SecureRandom random = new SecureRandom(seed.getBytes(LocaleUtils.getCharset()));
			generator.initialize(AsyncCrypter.DEFAULT_KEY_SIZE, random);
		}

		KeyPair key = generator.generateKeyPair();
		this.privateKey = key.getPrivate();
		this.publicKey = key.getPublic();
		this.setAlgorithm(type.getType());
	}

	private void initCiphers() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		if (this.privateKey != null) {
			this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm()));
			this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.privateKey);
		}

		if (this.publicKey != null) {
			this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm()));
			this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.publicKey);
		}
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		if (this.privateKey == null) {
			throw new IllegalStateException("Private key is null");
		}
		return super.encrypt(data);
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		if (this.publicKey == null) {
			throw new IllegalStateException("Public key is null");
		}
		return super.decrypt(data);
	}

	public static void save(final AsyncCrypter crypter, final OutputStream outputStream) throws IOException {
		Assert.notNull(crypter, "crypter");
		Assert.notNull(outputStream, "outputStream");
		PrivateKey privateKey = crypter.privateKey;
		PublicKey publicKey = crypter.publicKey;
		AsyncCrypter.writePrivate(privateKey, outputStream);
		AsyncCrypter.writePublic(publicKey, outputStream);
	}

	public static AsyncCrypter load(final InputStream inputStream, final KeyPairType type) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");

		String[] content = IOUtils.toString(inputStream).split("\n");

		try {
			PKCS8EncodedKeySpec specPrivate = new PKCS8EncodedKeySpec(AsyncCrypter.readPrivate(content));
			X509EncodedKeySpec specPublic = new X509EncodedKeySpec(AsyncCrypter.readPublic(content));
			KeyFactory factory = KeyFactory.getInstance(type.getType());
			PrivateKey privateKey = factory.generatePrivate(specPrivate);
			PublicKey publicKey = factory.generatePublic(specPublic);

			AsyncCrypter crypter = new AsyncCrypter(privateKey, publicKey, type);
			return crypter;
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	private static void writePrivate(final Key key, final OutputStream outputStream) throws IOException {
		outputStream.write(AsyncCrypter.PRIVATE_KEY_BEFORE.getBytes());
		AsyncCrypter.write(key, outputStream);
		outputStream.write(AsyncCrypter.PRIVATE_KEY_AFTER.getBytes());
	}

	private static void writePublic(final Key key, final OutputStream outputStream) throws IOException {
		outputStream.write(AsyncCrypter.PUBLIC_KEY_BEFORE.getBytes());
		AsyncCrypter.write(key, outputStream);
		outputStream.write(AsyncCrypter.PUBLIC_KEY_AFTER.getBytes());
	}

	private static void write(final Key key, final OutputStream outputStream) throws IOException {
		byte[] encoded = key.getEncoded();
		String hex = new String(Base64Utils.toBase64(encoded));
		outputStream.write(hex.getBytes());
	}

	private static byte[] readPrivate(final String[] content) {
		StringBuilder builder = new StringBuilder();
		boolean valid = false;
		for (String s : content) {
			if (((s.indexOf("--END") != -1) && (s.indexOf("PRIVATE") != -1))) {
				break;
			}
			if (((s.indexOf("--BEGIN") != -1) && (s.indexOf("PRIVATE") != -1))) {
				valid = true;
				continue;
			}

			if (valid) {
				if ((!StringUtils.isEmpty(s)) && (s.indexOf(":") == -1)) {
					builder.append(s);
					builder.append("\n");
				}
			}
		}
		String key = builder.toString();
		byte[] bytes = key.getBytes();
		return Base64Utils.fromBase64(bytes);
	}

	private static byte[] readPublic(final String[] content) {
		StringBuilder builder = new StringBuilder();
		boolean valid = false;
		for (String s : content) {
			if (((s.indexOf("--END") != -1) && (s.indexOf("PUBLIC") != -1))) {
				break;
			}
			if (((s.indexOf("--BEGIN") != -1) && (s.indexOf("PUBLIC") != -1))) {
				valid = true;
				continue;
			}

			if (valid) {
				if ((!StringUtils.isEmpty(s)) && (s.indexOf(":") == -1)) {
					builder.append(s);
					builder.append("\n");
				}
			}
		}
		String key = builder.toString();
		byte[] bytes = key.getBytes();
		return Base64Utils.fromBase64(bytes);
	}

}
