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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;

public class TextConverterHelper {

	private static final Map<Class<?>, AttributeConverter<?>>	converters;

	private static final Map<Class<?>, AttributeConverter<?>>	extraConverters;

	private static final NullConverter							nullConverter	= new NullConverter();

	private static final PojoConverter							pojoConverter	= new PojoConverter();

	protected TextConverterHelper() {
		//
	}

	static {
		converters = new HashMap<Class<?>, AttributeConverter<?>>();
		extraConverters = new HashMap<Class<?>, AttributeConverter<?>>();
		TextConverterHelper.converters.put(BigDecimal.class, new BigDecimalConverter());
		TextConverterHelper.converters.put(BigInteger.class, new BigIntegerConverter());
		TextConverterHelper.converters.put(boolean.class, new BooleanConverter());
		TextConverterHelper.converters.put(Boolean.class, new BooleanConverter());
		TextConverterHelper.converters.put(byte.class, new ByteConverter());
		TextConverterHelper.converters.put(Byte.class, new ByteConverter());
		TextConverterHelper.converters.put(char.class, new CharacterConverter());
		TextConverterHelper.converters.put(Character.class, new CharacterConverter());
		TextConverterHelper.converters.put(Collection.class, new CollectionConverter());
		TextConverterHelper.converters.put(Date.class, new DateConverter());
		TextConverterHelper.converters.put(double.class, new DoubleConverter());
		TextConverterHelper.converters.put(Double.class, new DoubleConverter());
		TextConverterHelper.converters.put(float.class, new FloatConverter());
		TextConverterHelper.converters.put(Float.class, new FloatConverter());
		TextConverterHelper.converters.put(int.class, new IntegerConverter());
		TextConverterHelper.converters.put(Integer.class, new IntegerConverter());
		TextConverterHelper.converters.put(long.class, new LongConverter());
		TextConverterHelper.converters.put(Long.class, new LongConverter());
		// converters.put(Map.class, new MapConverter());
		// converters.put(Object.class, new ObjectConverter());
		TextConverterHelper.converters.put(Pojo.class, new PojoConverter());
		// converters.put(Properties.class, new PropertiesConverter());
		// converters.put(Serializable.class, new SerializableConverter());
		TextConverterHelper.converters.put(short.class, new ShortConverter());
		TextConverterHelper.converters.put(Short.class, new ShortConverter());
		TextConverterHelper.converters.put(String.class, new StringConverter());
	}

	protected static AttributeConverter<?> getAttributeConverter(Class<?> clazz) {
		for (Entry<Class<?>, AttributeConverter<?>> entry : TextConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TextConverterHelper.converters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TextConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : TextConverterHelper.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return TextConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getNullAttributeConverter() {
		return TextConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getPojoAttributeConverter() {
		return TextConverterHelper.pojoConverter;
	}

	protected static void addConverter(Class<?> clazz, AttributeConverter<?> converter) {
		TextConverterHelper.extraConverters.put(clazz, converter);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> T from(String s, Class<T> clazz) throws ConverterException {
		try {
			return (T) TextConverterHelper.pojoConverter.fromText(clazz, s);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public static String to(Pojo pojo) throws ConverterException {
		try {
			return TextConverterHelper.pojoConverter.toText(pojo);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
