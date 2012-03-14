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
package br.net.woodstock.rockframework.web.struts2.converter;

import br.net.woodstock.rockframework.security.SecurityHolder;
import br.net.woodstock.rockframework.security.crypt.Crypter;
import br.net.woodstock.rockframework.web.types.EncryptedType;

public class EncryptedConverter extends TypeConverter<EncryptedType> {

	private Crypter	crypter;

	public EncryptedConverter() {
		super();
		this.crypter = SecurityHolder.getSyncCrypter();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected EncryptedType convertFromString(final String s, final Class toClass) {
		Crypter crypter = this.crypter;
		byte[] bytes = s.getBytes();
		byte[] enc = crypter.decrypt(bytes);
		String text = new String(enc);
		return this.wrap(text);
	}

	@Override
	protected String convertToString(final EncryptedType o) {
		Crypter crypter = this.crypter;
		byte[] bytes = o.getValue().getBytes();
		byte[] dec = crypter.encrypt(bytes);
		String text = new String(dec);
		return text;
	}

	protected EncryptedType wrap(final String text) {
		return new EncryptedType(text);
	}

}
