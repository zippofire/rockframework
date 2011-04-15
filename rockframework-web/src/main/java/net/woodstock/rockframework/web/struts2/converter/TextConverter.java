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
package net.woodstock.rockframework.web.struts2.converter;

import net.woodstock.rockframework.util.StringFormat;
import net.woodstock.rockframework.web.types.TextType;

public abstract class TextConverter<T extends TextType> extends TypeConverter<T> {

	private StringFormat	format;

	public TextConverter(final String pattern) {
		super();
		this.format = new StringFormat(pattern);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected T convertFromString(final String s, final Class toClass) {
		String text = this.format.parse(s);
		return this.wrap(text);
	}

	@Override
	protected String convertToString(final T o) {
		return this.format.format(o.getValue());
	}

	protected abstract T wrap(String text);

}
