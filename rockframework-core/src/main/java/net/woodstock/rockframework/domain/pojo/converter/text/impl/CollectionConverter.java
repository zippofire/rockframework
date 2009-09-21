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

import java.util.Collection;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;
import net.woodstock.rockframework.domain.pojo.converter.common.Ignore;
import net.woodstock.rockframework.domain.pojo.converter.common.Size;
import net.woodstock.rockframework.domain.pojo.converter.common.impl.AbstractAttributeConverter;
import net.woodstock.rockframework.domain.pojo.converter.text.TextCollection;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

class CollectionConverter extends AbstractAttributeConverter<Collection<?>> {

	@SuppressWarnings("unchecked")
	public Collection<?> fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			TextCollection textCollection = propertyDescriptor.getAnnotation(TextCollection.class);
			Collection collection = textCollection.type().newInstance();

			while ((text != null) && (text.length() >= textCollection.itemSize())) {
				String s = text.substring(0, textCollection.itemSize());

				if (text.length() == textCollection.itemSize()) {
					text = null;
				} else {
					text = text.substring(textCollection.itemSize());
				}

				if (Pojo.class.isAssignableFrom(textCollection.itemType())) {
					Pojo pojo = (Pojo) textCollection.itemType().newInstance();

					BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(pojo.getClass());
					for (PropertyDescriptor p : beanDescriptor.getProperties()) {
						if (p.isAnnotationPresent(Ignore.class)) {
							continue;
						}

						int size = p.getAnnotation(Size.class).value();

						String ss = s.substring(0, size);
						s = s.substring(size);

						AttributeConverter converter = TextConverterHelper.getAttributeConverter(p.getType());

						Object value = converter.fromText(ss, p);

						p.setValue(pojo, value);
					}
					collection.add(pojo);
				} else {
					AttributeConverter converter = TextConverterHelper.getAttributeConverter(textCollection.itemType());
					Object o = converter.fromText(s, propertyDescriptor);
					collection.add(o);
				}
			}
			return collection;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toText(Collection<?> c, PropertyDescriptor propertyDescriptor) {
		try {
			TextCollection textCollection = propertyDescriptor.getAnnotation(TextCollection.class);
			StringBuilder builder = new StringBuilder();
			if (c != null) {
				for (Object o : c) {
					if (Pojo.class.isAssignableFrom(textCollection.itemType())) {
						Pojo p = (Pojo) o;
						BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(p.getClass());
						for (PropertyDescriptor pd : beanDescriptor.getProperties()) {
							if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
								continue;
							}
							Object value = pd.getValue(p);
							AttributeConverter attributeConverter = TextConverterHelper.getNullAttributeConverter();
							if (value != null) {
								attributeConverter = TextConverterHelper.getAttributeConverter(value.getClass());
							}
							String s = attributeConverter.toText(value, pd);
							builder.append(s);
						}
					} else {
						AttributeConverter attributeConverter = TextConverterHelper.getAttributeConverter(textCollection.itemType());
						builder.append(attributeConverter.toText(o, propertyDescriptor));
					}
				}
			}
			if (propertyDescriptor.isAnnotationPresent(Size.class)) {
				Size size = propertyDescriptor.getAnnotation(Size.class);
				return this.rpad(builder.toString(), size.value());
			}
			return builder.toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
