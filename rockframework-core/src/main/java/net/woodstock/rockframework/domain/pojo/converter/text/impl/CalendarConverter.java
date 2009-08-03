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

import java.util.Calendar;

import net.woodstock.rockframework.domain.pojo.converter.text.TextDate;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.DateUtils;
import net.woodstock.rockframework.utils.StringUtils;

class CalendarConverter extends TextAttributeConverterBase<Calendar> {

	public Calendar fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Calendar c = null;
			if (!StringUtils.isEmpty(text)) {
				String format = propertyDescriptor.getAnnotation(TextDate.class).format();
				c = Calendar.getInstance();
				c.setTime(DateUtils.parse(TextConverterBase.trim(text), format));
			}
			return c;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Calendar c, PropertyDescriptor propertyDescriptor) {
		try {
			String s = StringUtils.BLANK;
			if (c != null) {
				String format = propertyDescriptor.getAnnotation(TextDate.class).format();
				s = DateUtils.format(c.getTime(), format);
			}
			return TextConverterBase.ldap(s, this.getSize(propertyDescriptor));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}
}
