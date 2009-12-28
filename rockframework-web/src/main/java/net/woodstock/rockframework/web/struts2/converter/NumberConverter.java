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

import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.opensymphony.xwork2.util.TypeConversionException;

public abstract class NumberConverter extends SimpleValueConverter<Number> {

	private NumberFormat	format;

	public NumberConverter(final String format) {
		super();
		this.format = new DecimalFormat(format);
	}

	public NumberConverter(final NumberFormat format) {
		super();
		this.format = format;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Number convertFromString(final String s, final Class toClass) {
		try {
			Number n = this.format.parse(s);
			Constructor<? extends Number> constructor = toClass.getConstructor(new Class[] { String.class });
			return constructor.newInstance(new Object[] { n.toString() });
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}

	@Override
	protected String convertToString(final Number o) {
		String s = this.format.format(o);
		return s;
	}

}
