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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.security.crypt.KeyPairHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.impl.CrypterOperation.Mode;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class AsyncCrypter extends AbstractCrypter implements KeyPairHolder {

	private static final int	DEFAULT_KEY_SIZE	= 1024;

	private KeyPair				keyPair;

	private KeyPairType			keyPairType;

	public AsyncCrypter(final KeyPair keyPair) {
		super();
		Assert.notNull(keyPair, "keyPair");
		this.keyPair = keyPair;
		for (KeyPairType keyPairType : KeyPairType.values()) {
			Key key = null;
			if (this.keyPair.getPrivate() != null) {
				key = this.keyPair.getPrivate();
			} else if (this.keyPair.getPublic() != null) {
				key = this.keyPair.getPublic();
			}

			if (key != null) {
				if (keyPairType.getAlgorithm().equals(key.getAlgorithm())) {
					this.keyPairType = keyPairType;
					break;
				}
			}
		}
	}

	public AsyncCrypter(final KeyPairType type) {
		this(type, null);
	}

	public AsyncCrypter(final KeyPairType type, final String seed) {
		super();
		Assert.notNull(type, "type");
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(type.getAlgorithm());

			if (ConditionUtils.isNotEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes());
				generator.initialize(AsyncCrypter.DEFAULT_KEY_SIZE, random);
			}

			this.keyPairType = type;
			this.keyPair = generator.generateKeyPair();
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		return this.encrypt(data, null);
	}

	@Override
	public byte[] encrypt(final byte[] data, final String seed) {
		PrivateKey privateKey = this.keyPair.getPrivate();
		if (privateKey == null) {
			throw new IllegalStateException("Private key is null");
		}
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(privateKey, Mode.ENCRYPT, data, seed);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		return this.decrypt(data, null);
	}

	@Override
	public byte[] decrypt(final byte[] data, final String seed) {
		PublicKey publicKey = this.keyPair.getPublic();
		if (publicKey == null) {
			throw new IllegalStateException("Public key is null");
		}
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(publicKey, Mode.DECRYPT, data, seed);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public String getAlgorithm() {
		if (this.keyPairType == null) {
			return null;
		}
		return this.keyPairType.getAlgorithm();
	}

	@Override
	public byte[] getPrivateKey() {
		return this.keyPair.getPrivate().getEncoded();
	}

	@Override
	public byte[] getPublicKey() {
		return this.keyPair.getPublic().getEncoded();
	}

}
