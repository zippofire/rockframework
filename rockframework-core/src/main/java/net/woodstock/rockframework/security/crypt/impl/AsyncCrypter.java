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

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.security.crypt.KeyPairType;
import net.woodstock.rockframework.security.crypt.impl.CrypterOperation.Mode;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.StringUtils;

public class AsyncCrypter extends AbstractCrypter {

	private static final int	DEFAULT_KEY_SIZE	= 1024;

	private KeyPair				keyPair;

	public AsyncCrypter(final KeyPair keyPair) {
		super();
		Assert.notNull(keyPair, "keyPair");
		this.keyPair = keyPair;
	}

	public AsyncCrypter(final KeyPairType type) {
		this(type, null);
	}

	public AsyncCrypter(final KeyPairType type, final String seed) {
		super();
		Assert.notNull(type, "type");
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(type.getAlgorithm());

			if (!StringUtils.isEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes());
				generator.initialize(AsyncCrypter.DEFAULT_KEY_SIZE, random);
			}

			this.keyPair = generator.generateKeyPair();
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		PrivateKey privateKey = this.keyPair.getPrivate();
		if (privateKey == null) {
			throw new IllegalStateException("Private key is null");
		}
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(privateKey, Mode.ENCRYPT, data);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		PublicKey publicKey = this.keyPair.getPublic();
		if (publicKey == null) {
			throw new IllegalStateException("Public key is null");
		}
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(publicKey, Mode.DECRYPT, data);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	protected KeyPair getKeyPair() {
		return this.keyPair;
	}

}
