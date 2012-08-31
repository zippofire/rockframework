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
import java.security.PrivateKey;
import java.security.PublicKey;

import br.net.woodstock.rockframework.security.config.SecurityLog;
import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.security.crypt.CrypterWriter;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;

public class AsyncCrypterWriter implements CrypterWriter {

	private AsyncCrypter	crypter;

	private OutputStream	privateKeyOutputStream;

	private OutputStream	publicKeyOutputStream;

	public AsyncCrypterWriter(final AsyncCrypter crypter, final OutputStream privateKeyOutputStream, final OutputStream publicKeyOutputStream) {
		super();
		Assert.notNull(crypter, "crypter");
		this.crypter = crypter;
		this.privateKeyOutputStream = privateKeyOutputStream;
		this.publicKeyOutputStream = publicKeyOutputStream;
	}

	@Override
	public void write() {
		try {
			PrivateKey privateKey = this.crypter.getPrivateKey();
			PublicKey publicKey = this.crypter.getPublicKey();
			if ((privateKey != null) && (this.privateKeyOutputStream != null)) {
				byte[] base64 = Base64Utils.toBase64(privateKey.getEncoded());
				this.privateKeyOutputStream.write(base64);
			} else {
				SecurityLog.getInstance().getLogger().info("Private or privateKeyOutputStream is null and cold not be writed");
			}
			if ((publicKey != null) && (this.publicKeyOutputStream != null)) {
				byte[] base64 = Base64Utils.toBase64(publicKey.getEncoded());
				this.publicKeyOutputStream.write(base64);
			} else {
				SecurityLog.getInstance().getLogger().info("Public or publicKeyOutputStream is null and cold not be writed");
			}
		} catch (IOException e) {
			throw new CrypterException(e);
		}
	}

}
