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

import net.woodstock.rockframework.domain.pojo.converter.text.TextNumber;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

class DoubleConverter extends TextAttributeConverterBase<Double> {

	public Double fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Double d = null;
			if (!StringUtils.isEmpty(text)) {
				if (propertyDescriptor.isAnnotationPresent(TextNumber.class)) {
					String format = propertyDescriptor.getAnnotation(TextNumber.class).pattern();
					d = new Double(NumberUtils.parse(TextConverterBase.trim(text), format).doubleValue());
				} else {
					d = new Double(TextConverterBase.trim(text));
				}
			}
			return d;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Double d, PropertyDescriptor propertyDescriptor) {
		try {
			String s = StringUtils.BLANK;
			if (d != null) {
				if (propertyDescriptor.isAnnotationPresent(TextNumber.class)) {
					String format = propertyDescriptor.getAnnotation(TextNumber.class).pattern();
					s = NumberUtils.format(d.doubleValue(), format);
				} else {
					s = d.toString();
				}
			}
			return TextConverterBase.ldap(s, this.getSize(propertyDescriptor));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
