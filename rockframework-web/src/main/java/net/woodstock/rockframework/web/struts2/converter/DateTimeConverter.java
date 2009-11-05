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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.util.TypeConversionException;

public class DateTimeConverter extends SimpleValueConverter<Date> {

	private DateFormat	format;

	public DateTimeConverter(String pattern) {
		super();
		this.format = new SimpleDateFormat(pattern);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Date convertFromString(String s, Class toClass) {
		try {
			Date d = this.format.parse(s);
			return d;
		} catch (ParseException e) {
			throw new TypeConversionException(e);
		}
	}

	@Override
	protected String convertToString(Date o) {
		String s = this.format.format(o);
		return s;
	}

}
