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

import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.Base64Utils;

public class Base64Crypter implements Crypter {

	private Crypter	crypter;

	public Base64Crypter(final Crypter crypter) {
		super();
		Assert.notNull(crypter, "crypter");
		this.crypter = crypter;
	}

	@Override
	public byte[] decrypt(final byte[] data) {
		byte[] b64 = Base64Utils.fromBase64(data);
		byte[] dec = this.crypter.decrypt(b64);
		return dec;
	}

	@Override
	public byte[] encrypt(final byte[] data) {
		byte[] enc = this.crypter.encrypt(data);
		byte[] b64 = Base64Utils.toBase64(enc);
		return b64;
	}

}
