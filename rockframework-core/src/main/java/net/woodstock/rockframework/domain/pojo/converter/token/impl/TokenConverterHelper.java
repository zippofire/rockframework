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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;

public abstract class TokenConverterHelper {

	private static final Map<Class<?>, AttributeConverter<?>>	converters;

	private static final Map<Class<?>, AttributeConverter<?>>	extraConverters;

	private static final NullConverter							nullConverter	= new NullConverter();

	private static final PojoConverter							pojoConverter	= new PojoConverter();

	private TokenConverterHelper() {
		//
	}

	static {
		converters = new HashMap<Class<?>, AttributeConverter<?>>();
		extraConverters = new HashMap<Class<?>, AttributeConverter<?>>();
		TokenConverterHelper.converters.put(BigDecimal.class, new BigDecimalConverter());
		TokenConverterHelper.converters.put(BigInteger.class, new BigIntegerConverter());
		TokenConverterHelper.converters.put(boolean.class, new BooleanConverter());
		TokenConverterHelper.converters.put(Boolean.class, new BooleanConverter());
		TokenConverterHelper.converters.put(byte.class, new ByteConverter());
		TokenConverterHelper.converters.put(Byte.class, new ByteConverter());
		TokenConverterHelper.converters.put(char.class, new CharacterConverter());
		TokenConverterHelper.converters.put(Character.class, new CharacterConverter());
		TokenConverterHelper.converters.put(Date.class, new DateConverter());
		TokenConverterHelper.converters.put(double.class, new DoubleConverter());
		TokenConverterHelper.converters.put(Double.class, new DoubleConverter());
		TokenConverterHelper.converters.put(float.class, new FloatConverter());
		TokenConverterHelper.converters.put(Float.class, new FloatConverter());
		TokenConverterHelper.converters.put(int.class, new IntegerConverter());
		TokenConverterHelper.converters.put(Integer.class, new IntegerConverter());
		TokenConverterHelper.converters.put(long.class, new LongConverter());
		TokenConverterHelper.converters.put(Long.class, new LongConverter());
		// converters.put(Map.class, new MapConverter());
		// converters.put(Object.class, new ObjectConverter());
		TokenConverterHelper.converters.put(Pojo.class, new PojoConverter());
		// converters.put(Properties.class, new PropertiesConverter());
		// converters.put(Serializable.class, new SerializableConverter());
		TokenConverterHelper.converters.put(short.class, new ShortConverter());
		TokenConverterHelper.converters.put(Short.class, new ShortConverter());
		TokenConverterHelper.converters.put(String.class, new StringConverter());
	}

	protected static AttributeConverter<?> getAttributeConverter(Class<?> clazz) {
		for (Entry<Class<?>, AttributeConverter<?>> entry : TokenConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TokenConverterHelper.converters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TokenConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TokenConverterHelper.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return TokenConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getNullAttributeConverter() {
		return TokenConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getPojoAttributeConverter() {
		return TokenConverterHelper.pojoConverter;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> T from(String s, Class<T> clazz, char delimiter) {
		try {
			return (T) TokenConverterHelper.pojoConverter.fromText(clazz, delimiter, s);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public static String to(Pojo pojo, char delimiter) {
		try {
			return TokenConverterHelper.pojoConverter.toText(pojo, delimiter);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
