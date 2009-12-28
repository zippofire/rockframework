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

import java.math.BigDecimal;

public class BigDecimalConverter extends NumberConverter<BigDecimal> {

	@Override
	protected BigDecimal toNumber(final Number n) {
		return new BigDecimal(n.toString());
	}

	@Override
	protected BigDecimal toNumber(final String s) {
		return new BigDecimal(s);
	}

}
