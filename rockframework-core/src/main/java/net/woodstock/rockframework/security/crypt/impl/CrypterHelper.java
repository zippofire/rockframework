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
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.security.crypt.KeyPairType;
import net.woodstock.rockframework.security.crypt.KeyType;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.IOUtils;
import net.woodstock.rockframework.utils.StringUtils;

abstract class CrypterHelper {

	private static final String	PRIVATE_KEY_BEFORE	= "-----BEGIN PRIVATE KEY-----\n";

	private static final String	PRIVATE_KEY_AFTER	= "\n-----END PRIVATE KEY-----\n";

	private static final String	PUBLIC_KEY_BEFORE	= "-----BEGIN PUBLIC KEY-----\n";

	private static final String	PUBLIC_KEY_AFTER	= "\n-----END PUBLIC KEY-----\n";

	private CrypterHelper() {
		//
	}

	protected static void store(final Key key, final OutputStream outputStream) throws IOException {
		Assert.notNull(key, "key");
		Assert.notNull(outputStream, "outputStream");

		byte[] encoded = key.getEncoded();
		String hex = new String(Base64Utils.toBase64(encoded));
		outputStream.write(hex.getBytes());
	}

	protected static SecretKey loadSecretKey(final InputStream inputStream, final KeyType type) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		SecretKeySpec keySpec = new SecretKeySpec(bytes, type.getAlgorithm());
		return keySpec;
	}

	// KeyPair
	protected static void save(final KeyPair keyPair, final OutputStream outputStream) throws IOException {
		Assert.notNull(keyPair, "keyPair");
		Assert.notNull(outputStream, "outputStream");
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		CrypterHelper.writePrivate(privateKey, outputStream);
		CrypterHelper.writePublic(publicKey, outputStream);
	}

	protected static KeyPair load(final InputStream inputStream, final KeyPairType type) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");

		String[] content = IOUtils.toString(inputStream).split("\n");

		try {
			PKCS8EncodedKeySpec specPrivate = new PKCS8EncodedKeySpec(CrypterHelper.readPrivate(content));
			X509EncodedKeySpec specPublic = new X509EncodedKeySpec(CrypterHelper.readPublic(content));
			KeyFactory factory = KeyFactory.getInstance(type.getAlgorithm());
			PrivateKey privateKey = factory.generatePrivate(specPrivate);
			PublicKey publicKey = factory.generatePublic(specPublic);

			KeyPair keyPair = new KeyPair(publicKey, privateKey);
			return keyPair;
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	protected static void writePrivate(final Key key, final OutputStream outputStream) throws IOException {
		outputStream.write(CrypterHelper.PRIVATE_KEY_BEFORE.getBytes());
		CrypterHelper.write(key, outputStream);
		outputStream.write(CrypterHelper.PRIVATE_KEY_AFTER.getBytes());
	}

	protected static void writePublic(final Key key, final OutputStream outputStream) throws IOException {
		outputStream.write(CrypterHelper.PUBLIC_KEY_BEFORE.getBytes());
		CrypterHelper.write(key, outputStream);
		outputStream.write(CrypterHelper.PUBLIC_KEY_AFTER.getBytes());
	}

	protected static void write(final Key key, final OutputStream outputStream) throws IOException {
		byte[] encoded = key.getEncoded();
		String hex = new String(Base64Utils.toBase64(encoded));
		outputStream.write(hex.getBytes());
	}

	protected static byte[] readPrivate(final String[] content) {
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
				if ((StringUtils.isNotEmpty(s)) && (s.indexOf(":") == -1)) {
					builder.append(s);
					builder.append("\n");
				}
			}
		}
		String key = builder.toString();
		byte[] bytes = key.getBytes();
		return Base64Utils.fromBase64(bytes);
	}

	protected static byte[] readPublic(final String[] content) {
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
				if ((StringUtils.isNotEmpty(s)) && (s.indexOf(":") == -1)) {
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
