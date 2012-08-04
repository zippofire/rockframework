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

public class AsStringCrypter extends DelegateCrypter {

	public AsStringCrypter(final Crypter crypter) {
		super(crypter);
	}

	public String decryptAsString(final byte[] data) {
		return this.decryptAsString(data, null);
	}

	public String decryptAsString(final byte[] data, final String seed) {
		byte[] dec = super.decrypt(data, seed);
		return new String(dec);
	}

	public String decryptAsString(final String str) {
		return this.decryptAsString(str, null);
	}

	public String decryptAsString(final String str, final String seed) {
		Assert.notEmpty(str, "str");

		byte[] data = str.getBytes();

		byte[] dec = super.decrypt(data, seed);
		return new String(dec);
	}

	public String encryptAsString(final byte[] data) {
		return this.encryptAsString(data, null);
	}

	public String encryptAsString(final byte[] data, final String seed) {
		byte[] enc = super.encrypt(data, seed);
		return new String(enc);
	}

	public String encryptAsString(final String str) {
		return this.encryptAsString(str, null);
	}

	public String encryptAsString(final String str, final String seed) {
		Assert.notEmpty(str, "str");

		byte[] data = str.getBytes();

		byte[] enc = super.encrypt(data, seed);
		return new String(enc);
	}

}
