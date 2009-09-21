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

import java.util.Collection;
import java.util.List;

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
	public String toText(Pojo p, List<Pojo> stack) {
		if (stack.contains(p)) {
			this.getLogger().info("Object " + p + " already parsed");
			return "null";
		}

		stack.add(p);

		try {
			StringBuilder builder = new StringBuilder();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(p.getClass());

			boolean first = true;

			builder.append("{");
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
					continue;
				}

				if (!first) {
					builder.append(", ");
				} else {
					first = false;
				}
				String name = propertyDescriptor.getName();
				Object value = propertyDescriptor.getValue(p);
				String text = null;
				if (value != null) {
					if (value instanceof Pojo) {
						PojoConverter attributeConverter = (PojoConverter) JsonConverterHelper.getPojoAttributeConverter();
						text = attributeConverter.toText((Pojo) value, stack);
					} else if (value instanceof Collection) {
						CollectionConverter attributeConverter = (CollectionConverter) JsonConverterHelper.getCollectionAttributeConverter();
						text = attributeConverter.toText((Collection<?>) value, propertyDescriptor, stack);
					} else {
						AttributeConverter attributeConverter = JsonConverterHelper.getAttributeConverter(value.getClass());
						text = attributeConverter.toText(value, propertyDescriptor);
					}
				} else {
					AttributeConverter attributeConverter = JsonConverterHelper.getNullAttributeConverter();
					text = attributeConverter.toText(value, propertyDescriptor);
				}

				builder.append(name);
				builder.append(": ");
				builder.append(text);
			}
			builder.append("}");
			return builder.toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public String toText(Pojo p, PropertyDescriptor propertyDescriptor) {
		throw new UnsupportedOperationException();
	}

}
