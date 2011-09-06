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

import br.net.woodstock.rockframework.security.crypt.Crypter;
import br.net.woodstock.rockframework.web.types.EncriptedType;
import br.net.woodstock.rockframework.web.util.SecurityHolder;

public class EncriptedConverter extends TypeConverter<EncriptedType> {

	private Crypter	crypter;

	public EncriptedConverter() {
		super();
		this.crypter = SecurityHolder.getSyncCrypter();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected EncriptedType convertFromString(final String s, final Class toClass) {
		byte[] bytes = s.getBytes();
		byte[] enc = this.crypter.decrypt(bytes);
		String text = new String(enc);
		return this.wrap(text);
	}

	@Override
	protected String convertToString(final EncriptedType o) {
		byte[] bytes = o.getValue().getBytes();
		byte[] dec = this.crypter.encrypt(bytes);
		String text = new String(dec);
		return text;
	}

	protected EncriptedType wrap(final String text) {
		return new EncriptedType(text);
	}

}
