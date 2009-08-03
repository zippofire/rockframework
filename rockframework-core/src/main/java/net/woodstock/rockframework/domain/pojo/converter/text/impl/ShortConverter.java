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

class ShortConverter extends TextAttributeConverterBase<Short> {

	public Short fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Short s = null;
			if (!StringUtils.isEmpty(text)) {
				if (propertyDescriptor.isAnnotationPresent(TextNumber.class)) {
					String format = propertyDescriptor.getAnnotation(TextNumber.class).pattern();
					s = new Short(NumberUtils.parse(TextConverterBase.trim(text), format).shortValue());
				} else {
					s = new Short(TextConverterBase.trim(text));
				}
			}
			return s;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Short s, PropertyDescriptor propertyDescriptor) {
		try {
			String ss = StringUtils.BLANK;
			if (s != null) {
				if (propertyDescriptor.isAnnotationPresent(TextNumber.class)) {
					String format = propertyDescriptor.getAnnotation(TextNumber.class).pattern();
					ss = NumberUtils.format(s.shortValue(), format);
				} else {
					ss = s.toString();
				}
			}
			return TextConverterBase.ldap(ss, this.getSize(propertyDescriptor));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
