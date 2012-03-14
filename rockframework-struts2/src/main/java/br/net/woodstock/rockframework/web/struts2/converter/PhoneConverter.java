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

import br.net.woodstock.rockframework.web.config.WebConfig;
import br.net.woodstock.rockframework.web.types.PhoneType;

public class PhoneConverter extends TextConverter<PhoneType> {

	private static final String	PHONE_FORMAT_PROPERTY	= "format.phone";

	private static final String	PHONE_FORMAT_PATTERN	= WebConfig.getInstance().getValue(PhoneConverter.PHONE_FORMAT_PROPERTY);

	public PhoneConverter() {
		super(PhoneConverter.PHONE_FORMAT_PATTERN);
	}

	public PhoneConverter(final String pattern) {
		super(pattern);
	}

	public PhoneConverter(final String pattern, final char character) {
		super(pattern, character);
	}

	@Override
	protected PhoneType wrap(final String text) {
		return new PhoneType(text);
	}

}
