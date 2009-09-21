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
import net.woodstock.rockframework.domain.pojo.converter.common.impl.AbstractAttributeConverter;
import net.woodstock.rockframework.reflection.PropertyDescriptor;

class CollectionConverter extends AbstractAttributeConverter<Collection<?>> {

	public Collection<?> fromText(String text, PropertyDescriptor propertyDescriptor) {
		throw new UnsupportedOperationException();
	}

	public String toText(Collection<?> c, PropertyDescriptor propertyDescriptor) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public String toText(Collection<?> c, PropertyDescriptor propertyDescriptor, List<Pojo> stack) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			if (c != null) {
				boolean first = true;
				for (Object o : c) {
					if (!first) {
						builder.append(", ");
					}
					if (o instanceof Pojo) {
						Pojo p = (Pojo) o;
						PojoConverter pojoConverter = (PojoConverter) JsonConverterHelper.getPojoAttributeConverter();
						builder.append(pojoConverter.toText(p, stack));
					} else {
						AttributeConverter attributeConverter = JsonConverterHelper.getAttributeConverter(o.getClass());
						builder.append(attributeConverter.toText(o, propertyDescriptor));
					}
					if (first) {
						first = false;
					}
				}
			}
			builder.append("]");
			return builder.toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
