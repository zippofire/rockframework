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
package net.woodstock.rockframework.domain.pojo.converter.common.impl;

import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.domain.pojo.converter.common.BooleanFormat;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.StringUtils;

public class BooleanConverter extends AbstractAttributeConverter<Boolean> {

	public Boolean fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Boolean b = null;
			if (!StringUtils.isEmpty(text)) {
				if (propertyDescriptor.isAnnotationPresent(BooleanFormat.class)) {
					BooleanFormat booleanFormat = propertyDescriptor.getAnnotation(BooleanFormat.class);
					if (text.equals(booleanFormat.trueValue())) {
						b = Boolean.TRUE;
					} else if (text.equals(booleanFormat.falseValue())) {
						b = Boolean.FALSE;
					}
				}
			}
			return b;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public String toText(Boolean b, PropertyDescriptor propertyDescriptor) {
		try {
			String s = StringUtils.BLANK;
			if (b != null) {
				if (propertyDescriptor.isAnnotationPresent(BooleanFormat.class)) {
					BooleanFormat booleanFormat = propertyDescriptor.getAnnotation(BooleanFormat.class);
					if (b.booleanValue()) {
						s = booleanFormat.trueValue();
					} else {
						s = booleanFormat.falseValue();
					}
				} else {
					s = b.toString();
				}
			}
			return s;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
