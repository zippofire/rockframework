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
package net.woodstock.rockframework.conversion.text;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;

class StringConverter extends net.woodstock.rockframework.conversion.common.converters.StringConverter {

	@Override
	public String from(ConverterContext context, String s) throws ConverterException {
		s = TextConverterHelper.trim(s);
		return super.from(context, s);
	}

	@Override
	public String to(ConverterContext context, String t) throws ConverterException {
		String s = super.to(context, t);
		int size = TextConverterHelper.getSize(context);
		return TextConverterHelper.rpad(s, size);
	}
}
