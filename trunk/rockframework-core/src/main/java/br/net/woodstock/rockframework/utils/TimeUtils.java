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
package br.net.woodstock.rockframework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TimeUtils {

	private static final String	TIME_FORMAT_PROPERTY	= "format.time";

	private TimeUtils() {
		//
	}

	public static String format(final Date date) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(TimeUtils.TIME_FORMAT_PROPERTY);
		return df.format(date);
	}

	public static String format(final Date date, final String format) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date parse(final String date) throws ParseException {
		if (ConditionUtils.isEmpty(date)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(TimeUtils.TIME_FORMAT_PROPERTY);
		return df.parse(date);
	}

	public static Date parse(final String date, final String format) throws ParseException {
		if (ConditionUtils.isEmpty(date)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

}
