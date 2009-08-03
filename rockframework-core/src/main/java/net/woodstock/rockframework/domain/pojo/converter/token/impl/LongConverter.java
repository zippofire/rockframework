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

import net.woodstock.rockframework.domain.pojo.converter.token.TokenNumber;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

class LongConverter extends TokenAttributeConverterBase<Long> {

	public Long fromText(String text, FieldInfo fieldInfo) {
		try {
			Long l = null;
			if (!StringUtils.isEmpty(text)) {
				if (fieldInfo.isAnnotationPresent(TokenNumber.class)) {
					String format = fieldInfo.getAnnotation(TokenNumber.class).pattern();
					l = new Long(NumberUtils.parse(text, format).longValue());
				} else {
					l = new Long(text);
				}
			}
			return l;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

	public String toText(Long l, FieldInfo fieldInfo) {
		try {
			String s = StringUtils.BLANK;
			if (l != null) {
				if (fieldInfo.isAnnotationPresent(TokenNumber.class)) {
					String format = fieldInfo.getAnnotation(TokenNumber.class).pattern();
					s = NumberUtils.format(l.longValue(), format);
				} else {
					s = l.toString();
				}
			}
			return s;
		} catch (Exception e) {
			throw new TokenConverterException(e);
		}
	}

}
