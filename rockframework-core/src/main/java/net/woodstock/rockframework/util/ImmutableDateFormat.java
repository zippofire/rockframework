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
package net.woodstock.rockframework.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

class ImmutableDateFormat extends DateFormat {

	private static final long	serialVersionUID	= 3851905427842569833L;

	private DateFormat			delegate;

	public ImmutableDateFormat(final DateFormat delegate) {
		super();
		Assert.notNull(delegate, "delegate");
		this.delegate = delegate;
	}

	@Override
	public StringBuffer format(final Date date, final StringBuffer toAppendTo, final FieldPosition fieldPosition) {
		return this.delegate.format(date, toAppendTo, fieldPosition);
	}

	@Override
	public Date parse(final String source, final ParsePosition pos) {
		return this.delegate.parse(source, pos);
	}

}
