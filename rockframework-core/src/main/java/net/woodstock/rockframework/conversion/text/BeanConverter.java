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
package net.woodstock.rockframework.conversion.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.Ignore;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.common.PropertyConverterContext;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.utils.StringUtils;

class BeanConverter extends AbstractTextConverter<Object> {

	private static Map<Class<?>, TextConverter<?>>	converters;

	private static TextConverter<?>					nullConverter;

	public BeanConverter() {
		super();
		if (BeanConverter.converters == null) {
			BeanConverter.converters = new HashMap<Class<?>, TextConverter<?>>();
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
	@SuppressWarnings("unchecked")
	public Object from(ConverterContext context, String s) throws ConverterException {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (context.getType() == null) {
			throw new IllegalArgumentException("Type must be not null");
		}
		try {
			Class<?> clazz = context.getType();
			Object obj = clazz.newInstance();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(clazz);

			if (!(context instanceof BeanConverterContext)) {
				context = new BeanConverterContext(context.getParent(), context.getName(), context.getType());
			}

			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
					continue;
				}
				if (!propertyDescriptor.isWriteable()) {
					continue;
				}
				String name = propertyDescriptor.getName();
				Class<?> type = propertyDescriptor.getType();
				if (!propertyDescriptor.isAnnotationPresent(Size.class)) {
					throw new ConverterException("Missing @Size in " + beanDescriptor.getName() + "." + propertyDescriptor.getName());
				}
				int size = propertyDescriptor.getAnnotation(Size.class).value();
				String str = s.substring(0, size);

				ConverterContext subContext = new PropertyConverterContext(context, name, type);
				TextConverter converter = BeanConverter.nullConverter;
				if (!StringUtils.isEmpty(str)) {
					converter = this.getConverter(type);
				}
				Object value = converter.from(subContext, str);
				propertyDescriptor.setValue(obj, value);

				s = s.substring(size);
			}
			return obj;
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public String to(ConverterContext context, Object t) throws ConverterException {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (t == null) {
			throw new IllegalArgumentException("Object must be not null");
		}
		try {
			StringBuilder builder = new StringBuilder();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(t.getClass());

			if (!(context instanceof BeanConverterContext)) {
				context = new BeanConverterContext(context.getParent(), context.getName(), t.getClass());
			}

			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
					continue;
				}
				if (!propertyDescriptor.isReadable()) {
					continue;
				}
				String name = propertyDescriptor.getName();
				Class<?> type = propertyDescriptor.getType();
				Object value = propertyDescriptor.getValue(t);
				ConverterContext subContext = new PropertyConverterContext(context, name, type);
				TextConverter converter = BeanConverter.nullConverter;
				if (value != null) {
					converter = this.getConverter(value.getClass());
				}
				String s = (String) converter.to(subContext, value);
				builder.append(s);
			}
			String s = builder.toString();
			int size = TextConverterHelper.getSize(context);
			return TextConverterHelper.lpad(s, size);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	// Util
	private TextConverter<?> getConverter(Class<?> clazz) {
		for (Entry<Class<?>, TextConverter<?>> entry : BeanConverter.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return this;
	}

}
