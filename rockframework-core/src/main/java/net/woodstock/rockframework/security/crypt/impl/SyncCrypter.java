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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.security.crypt.KeyType;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.StringUtils;

public class SyncCrypter extends AbstractCrypter {

	private SecretKey	key;

	public SyncCrypter(final KeyType type) {
		this(type, null);
	}

	public SyncCrypter(final KeyType type, final String seed) {
		super();
		Assert.notNull(type, "type");
		try {
			this.initKeys(type, seed);
			this.initCiphers();
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

	private void initKeys(final KeyType type, final String seed) throws NoSuchAlgorithmException {
		KeyGenerator generator = KeyGenerator.getInstance(type.getType());

		if (!StringUtils.isEmpty(seed)) {
			SecureRandom random = new SecureRandom(seed.getBytes());
			generator.init(random);
		}

		SecretKey key = generator.generateKey();
		this.key = key;
		this.setAlgorithm(type.getType());
	}

	private void initCiphers() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		this.setEncryptCipher(Cipher.getInstance(this.getAlgorithm()));
		this.getEncryptCipher().init(Cipher.ENCRYPT_MODE, this.key);

		this.setDecryptCipher(Cipher.getInstance(this.getAlgorithm()));
		this.getDecryptCipher().init(Cipher.DECRYPT_MODE, this.key);
	}
}
