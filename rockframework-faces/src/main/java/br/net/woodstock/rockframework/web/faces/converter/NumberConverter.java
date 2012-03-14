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
package br.net.woodstock.rockframework.web.faces.converter;

import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.faces.convert.ConverterException;

import br.net.woodstock.rockframework.util.Assert;

public abstract class NumberConverter extends SimpleFacesConverter<Number> {

	private NumberFormat			format;

	private Class<? extends Number>	clazz;

	public NumberConverter(final String format, final Class<? extends Number> clazz) {
		super();
		Assert.notEmpty(format, "format");
		Assert.notNull(clazz, "clazz");
		this.format = new DecimalFormat(format);
		this.clazz = clazz;
	}

	public NumberConverter(final NumberFormat format, final Class<? extends Number> clazz) {
		super();
		Assert.notNull(format, "format");
		Assert.notNull(clazz, "clazz");
		this.format = format;
		this.clazz = clazz;
	}

	@Override
	protected Number getAsObject(final String value) {
		try {
			Number n = this.format.parse(value);
			Constructor<? extends Number> constructor = this.clazz.getConstructor(new Class[] { String.class });
			return constructor.newInstance(new Object[] { n.toString() });
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	@Override
	protected String getAsString(final Number value) {
		String s = this.format.format(value);
		return s;
	}

}
