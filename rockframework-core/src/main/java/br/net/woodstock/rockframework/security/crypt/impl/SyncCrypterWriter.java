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

import java.io.IOException;
import java.io.OutputStream;

import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.security.crypt.CrypterWriter;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;

public class SyncCrypterWriter implements CrypterWriter {

	private SyncCrypter		crypter;

	private OutputStream	outputStream;

	public SyncCrypterWriter(final SyncCrypter crypter, final OutputStream outputStream) {
		super();
		Assert.notNull(crypter, "crypter");
		Assert.notNull(outputStream, "outputStream");
		this.crypter = crypter;
		this.outputStream = outputStream;
	}

	@Override
	public void write() {
		try {
			byte[] bytes = this.crypter.getSecretKey();
			byte[] base64 = Base64Utils.toBase64(bytes);
			this.outputStream.write(base64);
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

}
