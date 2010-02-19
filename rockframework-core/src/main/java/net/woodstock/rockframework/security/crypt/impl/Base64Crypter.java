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
import net.woodstock.rockframework.utils.Base64Utils;

public class Base64Crypter implements Crypter {

	private Crypter	crypter;

	public Base64Crypter(final Crypter crypter) {
		super();
		this.crypter = crypter;
	}

	@Override
	public String decrypt(final String str) {
		String b64 = Base64Utils.fromBase64(str);
		String dec = this.crypter.decrypt(b64);
		return dec;
	}

	@Override
	public String encrypt(final String str) {
		String enc = this.crypter.encrypt(str);
		String b64 = Base64Utils.toBase64(enc);
		return b64;
	}

}
