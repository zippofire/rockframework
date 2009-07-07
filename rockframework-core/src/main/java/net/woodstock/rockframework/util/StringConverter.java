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
package net.woodstock.rockframework.util;

import java.util.List;

import net.woodstock.rockframework.util.AbstractConverter.ByteConverter;
import net.woodstock.rockframework.util.AbstractConverter.DoubleConverter;
import net.woodstock.rockframework.util.AbstractConverter.FloatConverter;
import net.woodstock.rockframework.util.AbstractConverter.IntegerConverter;
import net.woodstock.rockframework.util.AbstractConverter.LongConverter;
import net.woodstock.rockframework.util.AbstractConverter.ShortConverter;

public abstract class StringConverter<T> {

	private static List<StringConverter<?>>	converters;

	static {
		StringConverter.converters.add(new ByteConverter());
		StringConverter.converters.add(new DoubleConverter());
		StringConverter.converters.add(new FloatConverter());
		StringConverter.converters.add(new IntegerConverter());
		StringConverter.converters.add(new LongConverter());
		StringConverter.converters.add(new ShortConverter());
	}

	public abstract boolean isValid(Class<?> clazz);

	public abstract T convert(String value);

	@SuppressWarnings("unchecked")
	public static <T> T convert(Class<T> clazz, String value) {
		for (StringConverter<?> converter : StringConverter.converters) {
			if (converter.isValid(clazz)) {
				return (T) converter.convert(value);
			}
		}
		throw new IllegalArgumentException("Converter not found for type '" + clazz.getCanonicalName() + "'");
	}

	public static void addConverter(StringConverter<?> converter) {
		StringConverter.converters.add(converter);
	}

}
