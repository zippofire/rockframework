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

import javax.crypto.spec.SecretKeySpec;

import net.woodstock.rockframework.security.crypt.CrypterException;
import net.woodstock.rockframework.security.crypt.CrypterReader;
import net.woodstock.rockframework.security.crypt.KeyType;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.Base64Utils;

public class SyncCrypterReader implements CrypterReader<SyncCrypter> {

	private InputStream	inputStream;

	private KeyType		type;

	public SyncCrypterReader(final InputStream inputStream, final KeyType type) {
		super();
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(type, "type");

		this.inputStream = inputStream;
		this.type = type;
	}

	@Override
	public SyncCrypter read() {
		try {
			byte[] base64 = new byte[this.inputStream.available()];

			this.inputStream.read(base64);

			byte[] bytes = Base64Utils.fromBase64(base64);

			SecretKeySpec keySpec = new SecretKeySpec(bytes, this.type.getAlgorithm());
			SyncCrypter crypter = new SyncCrypter(keySpec);
			
			return crypter;
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}
}
