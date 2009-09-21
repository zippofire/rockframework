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

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;
import net.woodstock.rockframework.domain.pojo.converter.common.Ignore;
import net.woodstock.rockframework.domain.pojo.converter.common.impl.AbstractAttributeConverter;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

class PojoConverter extends AbstractAttributeConverter<Pojo> {

	public Pojo fromText(String text, PropertyDescriptor propertyDescriptor) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public Pojo fromText(Class<? extends Pojo> clazz, char delimiter, String text) {
		try {
			Pojo pojo = clazz.newInstance();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(pojo.getClass());
			String[] values = text.split(new Character(delimiter).toString());
			int index = 0;
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
					continue;
				}

				String s = values[index++];

				AttributeConverter converter = TokenConverterHelper.getAttributeConverter(propertyDescriptor.getType());

				Object value = converter.fromText(s, propertyDescriptor);

				propertyDescriptor.setValue(pojo, value);
			}
			return pojo;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toText(Pojo p, char delimiter) {
		try {
			StringBuilder builder = new StringBuilder();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(p.getClass());

			boolean first = true;
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
					continue;
				}
				if (!first) {
					builder.append(delimiter);
				} else {
					first = false;
				}
				Object value = propertyDescriptor.getValue(p);
				AttributeConverter attributeConverter = TokenConverterHelper.getNullAttributeConverter();
				if (value != null) {
					attributeConverter = TokenConverterHelper.getAttributeConverter(value.getClass());
				}
				String s = attributeConverter.toText(value, propertyDescriptor);
				builder.append(s);
			}
			return builder.toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public String toText(Pojo p, PropertyDescriptor propertyDescriptor) {
		throw new UnsupportedOperationException();
	}

}
