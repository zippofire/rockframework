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
import net.woodstock.rockframework.domain.pojo.converter.text.TextCollection;
import net.woodstock.rockframework.domain.pojo.converter.text.TextField;
import net.woodstock.rockframework.domain.pojo.converter.text.TextIgnore;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;

class CollectionConverter implements TextAttributeConverter<Collection<?>> {

	@SuppressWarnings("unchecked")
	public Collection<?> fromText(String text, FieldInfo fieldInfo) {
		try {
			TextCollection textCollection = fieldInfo.getAnnotation(TextCollection.class);
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
					BeanInfo beanInfo = BeanInfo.getBeanInfo(pojo.getClass());
					for (FieldInfo f : beanInfo.getFieldsInfo()) {
						if (f.isAnnotationPresent(TextIgnore.class)) {
							continue;
						}

						int size = f.getAnnotation(TextField.class).size();

						String ss = s.substring(0, size);
						s = s.substring(size);

						TextAttributeConverter converter = TextConverterBase.getAttributeConverter(f
								.getFieldType());

						Object value = converter.fromText(ss, f);

						f.setFieldValue(pojo, value);
					}
					collection.add(pojo);
				} else {
					TextAttributeConverter converter = TextConverterBase.getAttributeConverter(textCollection
							.itemType());
					Object o = converter.fromText(s, fieldInfo);
					collection.add(o);
				}
			}
			return collection;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toText(Collection<?> c, FieldInfo fieldInfo) {
		try {
			TextCollection textCollection = fieldInfo.getAnnotation(TextCollection.class);
			StringBuilder builder = new StringBuilder();
			if (c != null) {
				for (Object o : c) {
					if (Pojo.class.isAssignableFrom(textCollection.itemType())) {
						Pojo p = (Pojo) o;
						BeanInfo beanInfo = BeanInfo.getBeanInfo(p.getClass());
						for (FieldInfo f : beanInfo.getFieldsInfo()) {
							if (fieldInfo.isAnnotationPresent(TextIgnore.class)) {
								continue;
							}
							Object value = f.getFieldValue(p);
							TextAttributeConverter attributeConverter = TextConverterBase
									.getNullAttributeConverter();
							if (value != null) {
								attributeConverter = TextConverterBase
										.getAttributeConverter(value.getClass());
							}
							String s = attributeConverter.toText(value, f);
							builder.append(s);
						}
					} else {
						TextAttributeConverter attributeConverter = TextConverterBase
								.getAttributeConverter(textCollection.itemType());
						builder.append(attributeConverter.toText(o, fieldInfo));
					}
				}
			}
			if (fieldInfo.isAnnotationPresent(TextField.class)) {
				TextField textField = fieldInfo.getAnnotation(TextField.class);
				return TextConverterBase.rdap(builder.toString(), textField.size());
			}
			return builder.toString();
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
