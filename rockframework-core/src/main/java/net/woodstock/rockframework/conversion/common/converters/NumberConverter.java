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

import java.text.DecimalFormat;
import java.text.ParseException;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.common.AbstractConverter;
import net.woodstock.rockframework.conversion.common.Format;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class NumberConverter<T extends Number> extends AbstractConverter<T> {

	@Override
	public T from(ConverterContext context, String s) throws ConverterException {
		try {
			if (StringUtils.isEmpty(s)) {
				return null;
			}
			if ((context != null) && (context.isAnnotationPresent(Format.class))) {
				Format format = context.getAnnotation(Format.class);
				DecimalFormat decimalFormat = new DecimalFormat(format.value());
				Number n = decimalFormat.parse(s);
				return this.toNumber(n);
			}
			return null;
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
	}

	@Override
	public String to(ConverterContext context, Number t) throws ConverterException {
		if (t == null) {
			return StringUtils.BLANK;
		}
		if ((context != null) && (context.isAnnotationPresent(Format.class))) {
			Format format = context.getAnnotation(Format.class);
			DecimalFormat decimalFormat = new DecimalFormat(format.value());
			return decimalFormat.format(t);
		}
		return t.toString();
	}

	protected abstract T toNumber(Number n);

	protected abstract T toNumber(String s);

}
