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

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

class ImmutableNumberFormat extends NumberFormat {

	private static final long	serialVersionUID	= -8054847418304762488L;

	private NumberFormat		delegate;

	public ImmutableNumberFormat(final NumberFormat delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public StringBuffer format(final double number, final StringBuffer toAppendTo, final FieldPosition pos) {
		return this.delegate.format(number, toAppendTo, pos);
	}

	@Override
	public StringBuffer format(final long number, final StringBuffer toAppendTo, final FieldPosition pos) {
		return this.delegate.format(number, toAppendTo, pos);
	}

	@Override
	public Number parse(final String source, final ParsePosition parsePosition) {
		return this.delegate.parse(source, parsePosition);
	}

}
