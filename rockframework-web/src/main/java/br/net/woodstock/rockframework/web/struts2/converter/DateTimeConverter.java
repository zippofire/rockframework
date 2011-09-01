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
package br.net.woodstock.rockframework.web.struts2.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.types.DateTimeType;


public abstract class DateTimeConverter<T extends DateTimeType> extends TypeConverter<T> {

	private DateFormat	format;

	public DateTimeConverter(final String pattern) {
		super();
		this.format = new SimpleDateFormat(pattern);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected T convertFromString(final String s, final Class toClass) {
		try {
			Date d = this.format.parse(s);
			return this.wrap(d);
		} catch (ParseException e) {
			WebLog.getInstance().getLog().warn(e.getMessage(), e);
			return null;
		}
	}

	@Override
	protected String convertToString(final T o) {
		if (o == null) {
			return null;
		}
		String s = this.format.format(o.getValue());
		return s;
	}

	protected abstract T wrap(Date d);

}
