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
package net.woodstock.rockframework.conversion.common.converters;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.common.AbstractConverter;
import net.woodstock.rockframework.utils.StringUtils;

public class CharacterConverter extends AbstractConverter<Character> {

	@Override
	public Character from(ConverterContext context, String s) throws ConverterException {
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		return new Character(s.charAt(0));
	}

	@Override
	public String to(ConverterContext context, Character t) throws ConverterException {
		if (t == null) {
			return StringUtils.BLANK;
		}
		return t.toString();
	}

}
