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

import java.util.Collection;
import java.util.List;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;
import net.woodstock.rockframework.utils.ArrayUtils;

@SuppressWarnings("unchecked")
public class ArrayConverter extends AbstractTextConverter<Object> {

	@Override
	public Object from(final ConverterContext context, final String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String to(final ConverterContext context, final Object t) {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (t == null) {
			throw new IllegalArgumentException("Object must be not null");
		}

		List<Object> list = ArrayUtils.asList(t);
		TextConverter converter = JsonConverterHelper.getConverter(Collection.class);
		return (String) converter.to(context, list);
	}

}
