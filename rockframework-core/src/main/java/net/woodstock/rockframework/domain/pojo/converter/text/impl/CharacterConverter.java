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

import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;

class CharacterConverter extends TextAttributeConverterBase<Character> {

	public Character fromText(String text, FieldInfo fieldInfo) {
		try {
			Character c = null;
			if (!StringUtils.isEmpty(text)) {
				c = new Character(text.charAt(0));
			}
			return c;
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

	public String toText(Character c, FieldInfo fieldInfo) {
		try {
			String s = "";
			if (c != null) {
				s = c.toString();
			}
			return TextConverterBase.ldap(s, this.getSize(fieldInfo));
		} catch (Exception e) {
			throw new TextConverterException(e);
		}
	}

}
