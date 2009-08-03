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

import java.util.Date;

import net.woodstock.rockframework.domain.pojo.converter.text.TextDate;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.DateUtils;
import net.woodstock.rockframework.utils.StringUtils;

class DateConverter extends TextAttributeConverterBase<Date> {

	public Date fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Date d = null;
			if (!StringUtils.isEmpty(text)) {
				String format = propertyDescriptor.getAnnotation(TextDate.class).format();
				d = DateUtils.parse(TextConverterBase.trim(text), format);
			}
			return d;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Date d, PropertyDescriptor propertyDescriptor) {
		try {
			String s = StringUtils.BLANK;
			if (d != null) {
				String format = propertyDescriptor.getAnnotation(TextDate.class).format();
				s = DateUtils.format(d, format);
			}
			return TextConverterBase.rdap(s, this.getSize(propertyDescriptor));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
