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
package net.woodstock.rockframework.conversion.json;

import java.util.Date;

import net.woodstock.rockframework.conversion.ConverterContext;

class DateConverter extends net.woodstock.rockframework.conversion.common.converters.DateConverter {

	@Override
	public Date from(final ConverterContext context, final String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String to(final ConverterContext context, final Date t) {
		if (t == null) {
			return null;
		}
		return "new Date(" + Long.toString(t.getTime()) + ")";
	}

}
