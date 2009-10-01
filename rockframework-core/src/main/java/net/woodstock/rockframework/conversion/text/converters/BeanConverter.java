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
package net.woodstock.rockframework.conversion.text.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.conversion.Converter;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.common.AbstractConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.common.PropertyConverterContext;
import net.woodstock.rockframework.conversion.text.Ignore;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public class BeanConverter extends AbstractConverter<Object> {

	private static Map<Class<?>, Converter<?>>	converters;

	private static Converter<?>					nullConverter;

	public BeanConverter() {
		super();
		if (BeanConverter.converters == null) {
			BeanConverter.converters = new HashMap<Class<?>, Converter<?>>();
			BeanConverter.converters.put(BigDecimal.class, new BigDecimalConverter());
			BeanConverter.converters.put(BigInteger.class, new BigIntegerConverter());
			BeanConverter.converters.put(Boolean.class, new BooleanConverter());
			BeanConverter.converters.put(Byte.class, new ByteConverter());
			BeanConverter.converters.put(Character.class, new CharacterConverter());
			BeanConverter.converters.put(Date.class, new DateConverter());
			BeanConverter.converters.put(Double.class, new DoubleConverter());
			BeanConverter.converters.put(Float.class, new FloatConverter());
			BeanConverter.converters.put(Integer.class, new IntegerConverter());
			BeanConverter.converters.put(Long.class, new LongConverter());
			BeanConverter.converters.put(Short.class, new ShortConverter());
			BeanConverter.converters.put(String.class, new StringConverter());
		}
		if (BeanConverter.nullConverter == null) {
			BeanConverter.nullConverter = new NullConverter();
		}
	}

	@Override
	public Object from(ConverterContext context, String s) throws ConverterException {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String to(ConverterContext context, Object t) throws ConverterException {
		if (context == null) {
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(t.getClass());
			context = new BeanConverterContext(beanDescriptor, t);
		}

		StringBuilder builder = new StringBuilder();
		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(t.getClass());
		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
				continue;
			}
			String name = propertyDescriptor.getName();
			Class<?> type = propertyDescriptor.getType();
			Object value = propertyDescriptor.getValue(t);
			ConverterContext subContext = new PropertyConverterContext(propertyDescriptor, context, name, type, value);
			Converter converter = BeanConverter.nullConverter;
			if (value != null) {
				converter = this.getConverter(value.getClass());
			}
			String s = converter.to(subContext, value);
			builder.append(s);
		}
		String s = builder.toString();
		int size = TextConverterHelper.getSize(context);
		return TextConverterHelper.lpad(s, size);
	}

	// Util
	private Converter<?> getConverter(Class<?> clazz) {
		for (Entry<Class<?>, Converter<?>> entry : BeanConverter.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return this;
	}

}
