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
package net.woodstock.rockframework.domain.pojo.converter.text.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.utils.StringUtils;

public class TextConverterBase {

	private static final Map<Class<?>, TextAttributeConverter<?>>	converters;

	private static final Map<Class<?>, TextAttributeConverter<?>>	extraConverters;

	private static final NullConverter								nullConverter	= new NullConverter();

	private static final PojoConverter								pojoConverter	= new PojoConverter();

	protected TextConverterBase() {
		//
	}

	static {
		converters = new HashMap<Class<?>, TextAttributeConverter<?>>();
		extraConverters = new HashMap<Class<?>, TextAttributeConverter<?>>();
		TextConverterBase.converters.put(BigDecimal.class, new BigDecimalConverter());
		TextConverterBase.converters.put(BigInteger.class, new BigIntegerConverter());
		TextConverterBase.converters.put(boolean.class, new BooleanConverter());
		TextConverterBase.converters.put(Boolean.class, new BooleanConverter());
		TextConverterBase.converters.put(byte.class, new ByteConverter());
		TextConverterBase.converters.put(Byte.class, new ByteConverter());
		TextConverterBase.converters.put(Calendar.class, new CalendarConverter());
		TextConverterBase.converters.put(char.class, new CharacterConverter());
		TextConverterBase.converters.put(Character.class, new CharacterConverter());
		TextConverterBase.converters.put(Collection.class, new CollectionConverter());
		TextConverterBase.converters.put(Date.class, new DateConverter());
		TextConverterBase.converters.put(double.class, new DoubleConverter());
		TextConverterBase.converters.put(Double.class, new DoubleConverter());
		TextConverterBase.converters.put(float.class, new FloatConverter());
		TextConverterBase.converters.put(Float.class, new FloatConverter());
		TextConverterBase.converters.put(int.class, new IntegerConverter());
		TextConverterBase.converters.put(Integer.class, new IntegerConverter());
		TextConverterBase.converters.put(long.class, new LongConverter());
		TextConverterBase.converters.put(Long.class, new LongConverter());
		// converters.put(Map.class, new MapConverter());
		// converters.put(Object.class, new ObjectConverter());
		TextConverterBase.converters.put(Pojo.class, new PojoConverter());
		// converters.put(Properties.class, new PropertiesConverter());
		// converters.put(Serializable.class, new SerializableConverter());
		TextConverterBase.converters.put(short.class, new ShortConverter());
		TextConverterBase.converters.put(Short.class, new ShortConverter());
		TextConverterBase.converters.put(String.class, new StringConverter());
	}

	protected static TextAttributeConverter<?> getAttributeConverter(Class<?> clazz) {
		for (Entry<Class<?>, TextAttributeConverter<?>> entry : TextConverterBase.extraConverters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TextAttributeConverter<?>> entry : TextConverterBase.converters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TextAttributeConverter<?>> entry : TextConverterBase.extraConverters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TextAttributeConverter<?>> entry : TextConverterBase.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return TextConverterBase.nullConverter;
	}

	protected static TextAttributeConverter<?> getNullAttributeConverter() {
		return TextConverterBase.nullConverter;
	}

	protected static TextAttributeConverter<?> getPojoAttributeConverter() {
		return TextConverterBase.pojoConverter;
	}

	protected static String ldap(String s, int size) {
		return StringUtils.lpad(s, size, ' ').substring(0, size);
	}

	protected static String rdap(String s, int size) {
		return StringUtils.rpad(s, size, ' ').substring(0, size);
	}

	protected static String trim(String s) {
		String str = s.trim();
		if (str.length() == 0) {
			return null;
		}
		return str;
	}

	protected static void addConverter(Class<?> clazz, TextAttributeConverter<?> converter) {
		TextConverterBase.extraConverters.put(clazz, converter);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> T from(String s, Class<T> clazz) throws TextConverterException {
		try {
			return (T) TextConverterBase.pojoConverter.fromText(clazz, s);
		} catch (TextConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public static String to(Pojo pojo) throws TextConverterException {
		try {
			return TextConverterBase.pojoConverter.toText(pojo);
		} catch (TextConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
