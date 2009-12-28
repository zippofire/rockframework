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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;
import net.woodstock.rockframework.conversion.common.Format;
import net.woodstock.rockframework.utils.StringUtils;

public class DateConverter extends AbstractTextConverter<Date> {

	@Override
	public Date from(final ConverterContext context, final String s) {
		try {
			if (StringUtils.isEmpty(s)) {
				return null;
			}
			DateFormat dateFormat = null;
			if ((context != null) && (context.isAnnotationPresent(Format.class))) {
				Format format = context.getAnnotation(Format.class);
				dateFormat = new SimpleDateFormat(format.value());
			} else {
				dateFormat = new SimpleDateFormat();
			}
			return dateFormat.parse(s);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
	}

	@Override
	public String to(final ConverterContext context, final Date t) {
		if (t == null) {
			return StringUtils.BLANK;
		}
		DateFormat dateFormat = null;
		if ((context != null) && (context.isAnnotationPresent(Format.class))) {
			Format format = context.getAnnotation(Format.class);
			dateFormat = new SimpleDateFormat(format.value());
		} else {
			dateFormat = new SimpleDateFormat();
		}
		return dateFormat.format(t);
	}

}
