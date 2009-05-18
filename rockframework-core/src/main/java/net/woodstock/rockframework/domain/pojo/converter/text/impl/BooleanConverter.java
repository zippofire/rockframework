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

import net.woodstock.rockframework.domain.pojo.converter.text.TextBoolean;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;

class BooleanConverter extends TextAttributeConverterBase<Boolean> {

	public Boolean fromText(String text, FieldInfo fieldInfo) {
		try {
			Boolean b = null;
			if (!StringUtils.isEmpty(text)) {
				if (TextConverterBase.trim(text).equals(
						fieldInfo.getAnnotation(TextBoolean.class).trueValue())) {
					b = Boolean.TRUE;
				} else {
					b = Boolean.FALSE;
				}
			}
			return b;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Boolean b, FieldInfo fieldInfo) {
		try {
			String s = "";
			if (b != null) {
				if (b.booleanValue()) {
					s = fieldInfo.getAnnotation(TextBoolean.class).trueValue();
				} else {
					s = fieldInfo.getAnnotation(TextBoolean.class).falseValue();
				}
			}
			return TextConverterBase.rdap(s, this.getSize(fieldInfo));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
