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
import net.woodstock.rockframework.domain.pojo.converter.token.TokenIgnore;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;

class PojoConverter extends TokenAttributeConverterBase<Pojo> {

	public Pojo fromText(String text, FieldInfo fieldInfo) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public Pojo fromText(Class<? extends Pojo> clazz, char delimiter, String text) {
		try {
			Pojo pojo = clazz.newInstance();
			BeanInfo beanInfo = BeanInfo.getBeanInfo(pojo.getClass());
			String[] values = text.split(new Character(delimiter).toString());
			int index = 0;
			for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
				if (fieldInfo.isAnnotationPresent(TokenIgnore.class)) {
					continue;
				}

				String s = values[index++];

				TokenAttributeConverter converter = TokenConverterBase.getAttributeConverter(fieldInfo
						.getFieldType());

				Object value = converter.fromText(s, fieldInfo);

				fieldInfo.setFieldValue(pojo, value);
			}
			return pojo;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String toText(Pojo p, char delimiter) {
		try {
			StringBuilder builder = new StringBuilder();
			BeanInfo beanInfo = BeanInfo.getBeanInfo(p.getClass());
			boolean first = true;
			for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
				if (fieldInfo.isAnnotationPresent(TokenIgnore.class)) {
					continue;
				}
				if (!first) {
					builder.append(delimiter);
				} else {
					first = false;
				}
				Object value = fieldInfo.getFieldValue(p);
				TokenAttributeConverter attributeConverter = TokenConverterBase.getNullAttributeConverter();
				if (value != null) {
					attributeConverter = TokenConverterBase.getAttributeConverter(value.getClass());
				}
				String s = attributeConverter.toText(value, fieldInfo);
				builder.append(s);
			}
			return builder.toString();
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

	public String toText(Pojo p, FieldInfo fieldInfo) {
		throw new UnsupportedOperationException();
	}

}
