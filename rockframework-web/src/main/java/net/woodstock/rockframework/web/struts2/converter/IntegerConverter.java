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
package net.woodstock.rockframework.web.struts2.converter;

import java.text.NumberFormat;

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.web.types.IntegerType;

public class IntegerConverter extends NumericConverter<IntegerType> {

	private static final String	INTEGER_FORMAT_PROPERTY	= "format.integer";

	private static final String	INTEGER_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(IntegerConverter.INTEGER_FORMAT_PROPERTY);

	public IntegerConverter() {
		super(IntegerConverter.INTEGER_FORMAT_PATTERN);
	}

	public IntegerConverter(final String format) {
		super(format);
	}

	public IntegerConverter(final NumberFormat format) {
		super(format);
	}

	@Override
	protected IntegerType wrap(final Number n) {
		return new IntegerType(Long.valueOf(n.longValue()));
	}

}
