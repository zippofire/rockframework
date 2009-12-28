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
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;
import net.woodstock.rockframework.utils.StringUtils;

class NullConverter extends AbstractTextConverter<Object> {

	@Override
	public Object from(final ConverterContext context, final String s) {
		return null;
	}

	@Override
	public String to(final ConverterContext context, final Object t) {
		String s = StringUtils.BLANK;
		int size = TextConverterHelper.getSize(context);
		return TextConverterHelper.lpad(s, size);
	}

}
