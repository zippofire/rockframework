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
package net.woodstock.rockframework.domain.pojo.converter.json.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;

public abstract class JsonConverterHelper {

	private static final Map<Class<?>, AttributeConverter<?>>	converters;

	private static final Map<Class<?>, AttributeConverter<?>>	extraConverters;

	private static final NullConverter							nullConverter		= new NullConverter();

	private static final PojoConverter							pojoConverter		= new PojoConverter();

	private static final CollectionConverter					collectionConverter	= new CollectionConverter();

	private JsonConverterHelper() {
		//
	}

	static {
		converters = new HashMap<Class<?>, AttributeConverter<?>>();
		extraConverters = new HashMap<Class<?>, AttributeConverter<?>>();
		JsonConverterHelper.converters.put(BigDecimal.class, new BigDecimalConverter());
		JsonConverterHelper.converters.put(BigInteger.class, new BigIntegerConverter());
		JsonConverterHelper.converters.put(boolean.class, new BooleanConverter());
		JsonConverterHelper.converters.put(Boolean.class, new BooleanConverter());
		JsonConverterHelper.converters.put(byte.class, new ByteConverter());
		JsonConverterHelper.converters.put(Byte.class, new ByteConverter());
		JsonConverterHelper.converters.put(char.class, new CharacterConverter());
		JsonConverterHelper.converters.put(Character.class, new CharacterConverter());
		JsonConverterHelper.converters.put(Date.class, new DateConverter());
		JsonConverterHelper.converters.put(double.class, new DoubleConverter());
		JsonConverterHelper.converters.put(Double.class, new DoubleConverter());
		JsonConverterHelper.converters.put(float.class, new FloatConverter());
		JsonConverterHelper.converters.put(Float.class, new FloatConverter());
		JsonConverterHelper.converters.put(int.class, new IntegerConverter());
		JsonConverterHelper.converters.put(Integer.class, new IntegerConverter());
		JsonConverterHelper.converters.put(long.class, new LongConverter());
		JsonConverterHelper.converters.put(Long.class, new LongConverter());
		// converters.put(Map.class, new MapConverter());
		// converters.put(Object.class, new ObjectConverter());
		// converters.put(Pojo.class, new PojoConverter());
		// converters.put(Properties.class, new PropertiesConverter());
		// converters.put(Serializable.class, new SerializableConverter());
		JsonConverterHelper.converters.put(short.class, new ShortConverter());
		JsonConverterHelper.converters.put(Short.class, new ShortConverter());
		JsonConverterHelper.converters.put(String.class, new StringConverter());
	}

	protected static AttributeConverter<?> getAttributeConverter(Class<?> clazz) {
		for (Entry<Class<?>, AttributeConverter<?>> entry : JsonConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : JsonConverterHelper.converters.entrySet()) {
			if (entry.getKey().equals(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : JsonConverterHelper.extraConverters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		for (Entry<Class<?>, AttributeConverter<?>> entry : JsonConverterHelper.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return JsonConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getNullAttributeConverter() {
		return JsonConverterHelper.nullConverter;
	}

	protected static AttributeConverter<?> getPojoAttributeConverter() {
		return JsonConverterHelper.pojoConverter;
	}

	protected static AttributeConverter<?> getCollectionAttributeConverter() {
		return JsonConverterHelper.collectionConverter;
	}

	public static String to(Pojo pojo) {
		try {
			return JsonConverterHelper.pojoConverter.toText(pojo, new ArrayList<Pojo>());
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
