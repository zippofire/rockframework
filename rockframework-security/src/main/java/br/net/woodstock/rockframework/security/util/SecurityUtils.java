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
package br.net.woodstock.rockframework.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.util.Assert;

public abstract class SecurityUtils {

	private SecurityUtils() {
		//
	}

	public static Certificate getCertificateFromFile(final InputStream inputStream, final CertificateType type) throws CertificateException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");
		CertificateFactory certFactory = CertificateFactory.getInstance(type.getType());
		Certificate certificate = certFactory.generateCertificate(inputStream);
		return certificate;
	}

	public static Certificate getCertificateFromFile(final byte[] bytes, final CertificateType type) throws CertificateException {
		Assert.notEmpty(bytes, "bytes");
		Assert.notNull(type, "type");
		InputStream inputStream = new ByteArrayInputStream(bytes);

		return SecurityUtils.getCertificateFromFile(inputStream, type);
	}

	public static PrivateKey getPrivateKeyFromPKCS8File(final InputStream inputStream, final KeyPairType type) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		return SecurityUtils.getPrivateKeyFromPKCS8File(bytes, type);
	}

	public static PrivateKey getPrivateKeyFromPKCS8File(final byte[] bytes, final KeyPairType type) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.notEmpty(bytes, "bytes");
		Assert.notNull(type, "type");

		PKCS8EncodedKeySpec specPrivate = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance(type.getAlgorithm());
		PrivateKey privateKey = factory.generatePrivate(specPrivate);
		return privateKey;
	}

	public static PublicKey getPublicKeyFromX509File(final InputStream inputStream, final KeyPairType type) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		return SecurityUtils.getPublicKeyFromX509File(bytes, type);
	}

	public static PublicKey getPublicKeyFromX509File(final byte[] bytes, final KeyPairType type) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.notEmpty(bytes, "bytes");
		Assert.notNull(type, "type");

		X509EncodedKeySpec specPublic = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance(type.getAlgorithm());
		PublicKey privateKey = factory.generatePublic(specPublic);
		return privateKey;
	}

	public static SecretKey getSecretKeyFromFile(final byte[] bytes, final KeyType type) {
		Assert.notEmpty(bytes, "bytes");
		Assert.notNull(type, "type");

		SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, type.getAlgorithm());
		return secretKeySpec;
	}

}
