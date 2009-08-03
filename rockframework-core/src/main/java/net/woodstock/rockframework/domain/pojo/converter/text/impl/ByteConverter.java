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
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

class ByteConverter extends TextAttributeConverterBase<Byte> {

	public Byte fromText(String text, FieldInfo fieldInfo) {
		try {
			Byte b = null;
			if (!StringUtils.isEmpty(text)) {
				if (fieldInfo.isAnnotationPresent(TextNumber.class)) {
					String format = fieldInfo.getAnnotation(TextNumber.class).pattern();
					b = new Byte(NumberUtils.parse(TextConverterBase.trim(text), format).byteValue());
				} else {
					b = new Byte(TextConverterBase.trim(text));
				}
			}
			return b;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Byte b, FieldInfo fieldInfo) {
		try {
			String s = StringUtils.BLANK;
			if (b != null) {
				if (fieldInfo.isAnnotationPresent(TextNumber.class)) {
					String format = fieldInfo.getAnnotation(TextNumber.class).pattern();
					s = NumberUtils.format(b.byteValue(), format);
				} else {
					s = b.toString();
				}
			}
			return TextConverterBase.ldap(s, this.getSize(fieldInfo));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
