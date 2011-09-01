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

import br.net.woodstock.rockframework.security.crypt.Crypter;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;

public class Base64Crypter extends DelegateCrypter {

	public Base64Crypter(final Crypter crypter) {
		super(crypter);
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		Assert.notNull(data, "data");

		byte[] b64 = Base64Utils.fromBase64(data);
		byte[] dec = super.decrypt(b64);
		return dec;
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		Assert.notNull(data, "data");

		byte[] enc = super.encrypt(data);
		byte[] b64 = Base64Utils.toBase64(enc);
		return b64;
	}

}
