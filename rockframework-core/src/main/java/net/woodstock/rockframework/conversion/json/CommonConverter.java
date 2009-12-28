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
package net.woodstock.rockframework.conversion.json;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;

public class CommonConverter extends AbstractTextConverter<Object> {

	@Override
	public Object from(final ConverterContext context, final String f) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String to(final ConverterContext context, final Object t) {
		TextConverter converter = JsonConverterHelper.getNullConverter();
		if (t != null) {
			converter = JsonConverterHelper.getConverter(t.getClass());
		}
		return (String) converter.to(context, t);
	}

}
