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
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.security.crypt.impl.CrypterOperation.Mode;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;


public class SyncCrypter extends AbstractCrypter {

	private SecretKey	key;

	public SyncCrypter(final SecretKey key) {
		super();
		Assert.notNull(key, "key");
		this.key = key;
	}

	public SyncCrypter(final KeyType type) {
		this(type, null);
	}

	public SyncCrypter(final KeyType type, final String seed) {
		super();
		Assert.notNull(type, "type");

		try {
			KeyGenerator generator = KeyGenerator.getInstance(type.getAlgorithm());

			if (ConditionUtils.isNotEmpty(seed)) {
				SecureRandom random = new SecureRandom(seed.getBytes());
				generator.init(random);
			}

			this.key = generator.generateKey();
		} catch (GeneralSecurityException e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(this.key, Mode.ENCRYPT, data);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		try {
			Assert.notNull(data, "data");
			CrypterOperation operation = new CrypterOperation(this.key, Mode.DECRYPT, data);
			return operation.execute();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	protected SecretKey getKey() {
		return this.key;
	}

}
