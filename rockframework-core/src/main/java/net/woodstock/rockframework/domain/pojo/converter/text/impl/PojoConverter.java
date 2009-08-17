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

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.pojo.converter.text.TextCollection;
import net.woodstock.rockframework.domain.pojo.converter.text.TextField;
import net.woodstock.rockframework.domain.pojo.converter.text.TextIgnore;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.utils.StringUtils;

class PojoConverter extends TextAttributeConverterBase<Pojo> {

	@SuppressWarnings("unchecked")
	public Pojo fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Pojo pojo = (Pojo) propertyDescriptor.getType().newInstance();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(pojo.getClass());
			for (PropertyDescriptor p : beanDescriptor.getProperties()) {
				if (p.isAnnotationPresent(TextIgnore.class)) {
					continue;
				}
				TextField textField = p.getAnnotation(TextField.class);
				int size = textField.size();

				String s = text.substring(0, size);
				text = text.substring(size);

				TextAttributeConverter converter = TextConverterBase.getAttributeConverter(p.getType());

				Object value = converter.fromText(s, p);

				p.setValue(pojo, value);
			}
			return pojo;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Pojo fromText(Class<? extends Pojo> clazz, String text) {
		try {
			Pojo pojo = clazz.newInstance();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(pojo.getClass());
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(TextIgnore.class)) {
					continue;
				}

				int size = -1;

				if (propertyDescriptor.isAnnotationPresent(TextField.class)) {
					TextField textField = propertyDescriptor.getAnnotation(TextField.class);
					size = textField.size();
				}

				if ((size == -1) && (!propertyDescriptor.isAnnotationPresent(TextCollection.class))) {
					String msg = CoreMessage.getInstance().getMessage(TextAttributeConverterBase.MESSAGE_ERROR_CONVERTER_SIZE, propertyDescriptor.getName());
					throw new TextConverterException(msg);
				}

				String s = StringUtils.BLANK;

				if (size != -1) {
					s = text.substring(0, size);
					text = text.substring(size);
				} else {
					s = text.substring(0);
					text = null;
				}

				TextAttributeConverter converter = TextConverterBase.getAttributeConverter(propertyDescriptor.getType());

				Object value = converter.fromText(s, propertyDescriptor);

				propertyDescriptor.setValue(pojo, value);
			}
			return pojo;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toText(Pojo p) {
		try {
			StringBuilder builder = new StringBuilder();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(p.getClass());
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(TextIgnore.class)) {
					continue;
				}
				Object value = propertyDescriptor.getValue(p);
				TextAttributeConverter attributeConverter = TextConverterBase.getNullAttributeConverter();
				if (value != null) {
					attributeConverter = TextConverterBase.getAttributeConverter(value.getClass());
				}
				String s = attributeConverter.toText(value, propertyDescriptor);
				builder.append(s);
			}
			return builder.toString();
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Pojo p, PropertyDescriptor propertyDescriptor) {
		try {
			return TextConverterBase.ldap(this.toText(p), this.getSize(propertyDescriptor));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
