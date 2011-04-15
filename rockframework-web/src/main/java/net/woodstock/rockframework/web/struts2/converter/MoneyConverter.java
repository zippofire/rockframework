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
import net.woodstock.rockframework.web.types.MoneyType;

public class MoneyConverter extends NumericConverter<MoneyType> {

	private static final String	DECIMAL_FORMAT_PROPERTY	= "format.decimal";

	private static final String	DECIMAL_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(MoneyConverter.DECIMAL_FORMAT_PROPERTY);

	public MoneyConverter() {
		super(MoneyConverter.DECIMAL_FORMAT_PATTERN);
	}

	public MoneyConverter(final String format) {
		super(format);
	}

	public MoneyConverter(final NumberFormat format) {
		super(format);
	}

	@Override
	protected MoneyType wrap(final Number n) {
		return new MoneyType(Double.valueOf(n.longValue()));
	}

}
