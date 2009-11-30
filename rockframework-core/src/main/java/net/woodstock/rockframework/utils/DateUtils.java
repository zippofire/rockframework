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
package net.woodstock.rockframework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.woodstock.rockframework.config.CoreConfig;

public abstract class DateUtils {

	private static final String		DATE_FORMAT_PROPERTY	= "format.date";

	private static final DateFormat	DATE_FORMAT				= new SimpleDateFormat(CoreConfig.getInstance().getValue(DateUtils.DATE_FORMAT_PROPERTY));

	private DateUtils() {
		//
	}

	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return DateUtils.DATE_FORMAT.format(date);
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parse(String date) throws ParseException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		return DateUtils.DATE_FORMAT.parse(date);
	}

	public static Date parse(String date, String format) throws ParseException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		return new SimpleDateFormat(format).parse(date);
	}

}
