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

class BeanConverter extends AbstractTextConverter<Object> {

	private static final String	BEGIN_OBJECT		= "{";

	private static final String	END_OBJECT			= "}";

	private static final String	VALUE_SEPARATOR		= ": ";

	private static final String	PROPERTY_SEPARATOR	= ", ";

	@Override
	public Object from(final ConverterContext context, final String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String to(final ConverterContext context, final Object t) {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (t == null) {
			throw new IllegalArgumentException("Object must be not null");
		}

		try {
			ConverterContext currentContext = context;
			StringBuilder builder = new StringBuilder();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(t.getClass());
			boolean first = true;

			if (!(context instanceof BeanConverterContext)) {
				currentContext = new BeanConverterContext(context.getParent(), context.getName(), t.getClass());
			}

			if ((!currentContext.isQueued(t)) && (!currentContext.isIgnored())) {
				currentContext.getQueue().add(t);

				builder.append(BeanConverter.BEGIN_OBJECT);

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
					ConverterContext subContext = new PropertyConverterContext(currentContext, name, type);
					TextConverter converter = JsonConverterHelper.getNullConverter();

					if (!subContext.isIgnored()) {
						if (value != null) {
							converter = JsonConverterHelper.getConverter(value.getClass());
						}
						String s = (String) converter.to(subContext, value);

						if (!first) {
							builder.append(BeanConverter.PROPERTY_SEPARATOR);
						}

						builder.append(name);
						builder.append(BeanConverter.VALUE_SEPARATOR);
						builder.append(s);

						if (first) {
							first = false;
						}
					}
				}

				builder.append(BeanConverter.END_OBJECT);

				String s = builder.toString();
				return s;
			}
			TextConverter converter = JsonConverterHelper.getNullConverter();
			return (String) converter.to(currentContext, t);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}
}
