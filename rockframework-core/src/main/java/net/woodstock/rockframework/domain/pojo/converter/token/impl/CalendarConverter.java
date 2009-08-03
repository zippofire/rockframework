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

import java.util.Calendar;

import net.woodstock.rockframework.domain.pojo.converter.token.TokenDate;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.DateUtils;
import net.woodstock.rockframework.utils.StringUtils;

class CalendarConverter extends TokenAttributeConverterBase<Calendar> {

	public Calendar fromText(String text, FieldInfo fieldInfo) {
		try {
			Calendar c = null;
			if (!StringUtils.isEmpty(text)) {
				String format = fieldInfo.getAnnotation(TokenDate.class).format();
				c = Calendar.getInstance();
				c.setTime(DateUtils.parse(text, format));
			}
			return c;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

	public String toText(Calendar c, FieldInfo fieldInfo) {
		try {
			String s = StringUtils.BLANK;
			if (c != null) {
				String format = fieldInfo.getAnnotation(TokenDate.class).format();
				s = DateUtils.format(c.getTime(), format);
			}
			return s;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}
}
