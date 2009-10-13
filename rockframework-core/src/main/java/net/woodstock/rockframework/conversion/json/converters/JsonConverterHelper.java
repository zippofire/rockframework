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
package net.woodstock.rockframework.conversion.json.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.conversion.TextConverter;

abstract class JsonConverterHelper {

	private static Collection<String[]>				replacement;

	private static Map<Class<?>, TextConverter<?>>	converters;

	private static TextConverter<?>					nullConverter;

	private static TextConverter<?>					beanConverter;

	private JsonConverterHelper() {
		//
	}

	public static String addEscape(String str) {
		if (JsonConverterHelper.replacement == null) {
			JsonConverterHelper.replacement = new LinkedList<String[]>();
			JsonConverterHelper.replacement.add(new String[] { "\n", "\\n" });
			JsonConverterHelper.replacement.add(new String[] { "\r", "\\r" });
			JsonConverterHelper.replacement.add(new String[] { "\t", "\\t" });
			JsonConverterHelper.replacement.add(new String[] { "'", "\\'" });
			JsonConverterHelper.replacement.add(new String[] { "\"", "\\\"" });
		}
		if (str == null) {
			return null;
		}
		for (String[] s : JsonConverterHelper.replacement) {
			str = str.replaceAll(s[0], s[1]);
		}

		return str;
	}

	public static TextConverter<?> getConverter(Class<?> clazz) {
		if (JsonConverterHelper.converters == null) {
			JsonConverterHelper.converters = new HashMap<Class<?>, TextConverter<?>>();
			JsonConverterHelper.converters.put(BigDecimal.class, new BigDecimalConverter());
			JsonConverterHelper.converters.put(BigInteger.class, new BigIntegerConverter());
			JsonConverterHelper.converters.put(Boolean.class, new BooleanConverter());
			JsonConverterHelper.converters.put(Byte.class, new ByteConverter());
			JsonConverterHelper.converters.put(Character.class, new CharacterConverter());
			JsonConverterHelper.converters.put(Collection.class, new CollectionConverter());
			JsonConverterHelper.converters.put(Date.class, new DateConverter());
			JsonConverterHelper.converters.put(Double.class, new DoubleConverter());
			JsonConverterHelper.converters.put(Float.class, new FloatConverter());
			JsonConverterHelper.converters.put(Integer.class, new IntegerConverter());
			JsonConverterHelper.converters.put(Long.class, new LongConverter());
			JsonConverterHelper.converters.put(Short.class, new ShortConverter());
			JsonConverterHelper.converters.put(String.class, new StringConverter());
		}
		if (JsonConverterHelper.beanConverter == null) {
			JsonConverterHelper.beanConverter = new BeanConverter();
		}
		if (JsonConverterHelper.nullConverter == null) {
			JsonConverterHelper.nullConverter = new NullConverter();
		}

		for (Entry<Class<?>, TextConverter<?>> entry : JsonConverterHelper.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return JsonConverterHelper.beanConverter;
	}

	public static TextConverter<?> getBeanConverter() {
		return JsonConverterHelper.beanConverter;
	}

	public static TextConverter<?> getNullConverter() {
		return JsonConverterHelper.nullConverter;
	}

}
