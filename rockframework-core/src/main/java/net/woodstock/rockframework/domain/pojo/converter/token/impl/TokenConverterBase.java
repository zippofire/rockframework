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
package net.woodstock.rockframework.domain.pojo.converter.token.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class TokenConverterBase {

	private static final Map<Class<?>, TokenAttributeConverter<?>>	converters;

	private static final Map<Class<?>, TokenAttributeConverter<?>>	extraConverters;

	private static final NullConverter								nullConverter	= new NullConverter();

	private static final PojoConverter								pojoConverter	= new PojoConverter();

	private TokenConverterBase() {
		//
	}

	static {
		converters = new HashMap<Class<?>, TokenAttributeConverter<?>>();
		extraConverters = new HashMap<Class<?>, TokenAttributeConverter<?>>();
		TokenConverterBase.converters.put(BigDecimal.class, new BigDecimalConverter());
		TokenConverterBase.converters.put(BigInteger.class, new BigIntegerConverter());
		TokenConverterBase.converters.put(boolean.class, new BooleanConverter());
		TokenConverterBase.converters.put(Boolean.class, new BooleanConverter());
		TokenConverterBase.converters.put(byte.class, new ByteConverter());
		TokenConverterBase.converters.put(Byte.class, new ByteConverter());
		TokenConverterBase.converters.put(Calendar.class, new CalendarConverter());
		TokenConverterBase.converters.put(char.class, new CharacterConverter());
		TokenConverterBase.converters.put(Character.class, new CharacterConverter());
		TokenConverterBase.converters.put(Date.class, new DateConverter());
		TokenConverterBase.converters.put(double.class, new DoubleConverter());
		TokenConverterBase.converters.put(Double.class, new DoubleConverter());
		TokenConverterBase.converters.put(float.class, new FloatConverter());
		TokenConverterBase.converters.put(Float.class, new FloatConverter());
		TokenConverterBase.converters.put(int.class, new IntegerConverter());
		TokenConverterBase.converters.put(Integer.class, new IntegerConverter());
		TokenConverterBase.converters.put(long.class, new LongConverter());
		TokenConverterBase.converters.put(Long.class, new LongConverter());
		// converters.put(Map.class, new MapConverter());
		// converters.put(Object.class, new ObjectConverter());
		TokenConverterBase.converters.put(Pojo.class, new PojoConverter());
		// converters.put(Properties.class, new PropertiesConverter());
		// converters.put(Serializable.class, new SerializableConverter());
		TokenConverterBase.converters.put(short.class, new ShortConverter());
		TokenConverterBase.converters.put(Short.class, new ShortConverter());
		TokenConverterBase.converters.put(String.class, new StringConverter());
	}

	protected static TokenAttributeConverter<?> getAttributeConverter(Class<?> clazz) {
		for (Entry<Class<?>, TokenAttributeConverter<?>> entry : TokenConverterBase.extraConverters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TokenAttributeConverter<?>> entry : TokenConverterBase.converters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TokenAttributeConverter<?>> entry : TokenConverterBase.extraConverters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, TokenAttributeConverter<?>> entry : TokenConverterBase.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return TokenConverterBase.nullConverter;
	}

	protected static TokenAttributeConverter<?> getNullAttributeConverter() {
		return TokenConverterBase.nullConverter;
	}

	protected static TokenAttributeConverter<?> getPojoAttributeConverter() {
		return TokenConverterBase.pojoConverter;
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

	protected static void addConverter(Class<?> clazz, TokenAttributeConverter<?> converter) {
		TokenConverterBase.extraConverters.put(clazz, converter);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> T from(String s, Class<T> clazz, char delimiter) throws TokenConverterException {
		try {
			return (T) TokenConverterBase.pojoConverter.fromText(clazz, delimiter, s);
		} catch (TokenConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

	public static String to(Pojo pojo, char delimiter) throws TokenConverterException {
		try {
			return TokenConverterBase.pojoConverter.toText(pojo, delimiter);
		} catch (TokenConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

}
