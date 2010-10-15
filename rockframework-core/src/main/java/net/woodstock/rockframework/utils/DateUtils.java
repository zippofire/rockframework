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
import java.util.Calendar;
import java.util.Date;

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.util.DateFormatFactory;

import org.springframework.util.Assert;

public abstract class DateUtils {

	private static final String	DATE_FORMAT_PROPERTY	= "format.date";

	private static final String	DATE_FORMAT_PATTERN		= CoreConfig.getInstance().getValue(DateUtils.DATE_FORMAT_PROPERTY);

	private DateUtils() {
		//
	}

	public static String format(final Date date) {
		if (date == null) {
			return null;
		}
		DateFormat df = DateFormatFactory.getInstance().getFormat(DateUtils.DATE_FORMAT_PATTERN);
		return df.format(date);
	}

	public static String format(final Date date, final String format) {
		if (date == null) {
			return null;
		}
		DateFormat df = DateFormatFactory.getInstance().getFormat(format);
		return df.format(date);
	}

	public static Date parse(final String date) throws ParseException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat df = DateFormatFactory.getInstance().getFormat(DateUtils.DATE_FORMAT_PATTERN);
		return df.parse(date);
	}

	public static Date parse(final String date, final String format) throws ParseException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat df = DateFormatFactory.getInstance().getFormat(format);
		return df.parse(date);
	}

	// Diff
	public static long diffDays(final Date date1, final Date date2) {
		Assert.notNull(date1, "date1");
		Assert.notNull(date2, "date2");
		long diff = 0;
		Calendar cMin = Calendar.getInstance();
		Calendar cMax = Calendar.getInstance();

		if (date1.compareTo(date2) > 0) {
			cMin.setTime(date2);
			cMax.setTime(date1);
		} else {
			cMin.setTime(date1);
			cMax.setTime(date2);
		}

		if (cMax.get(Calendar.YEAR) - cMin.get(Calendar.YEAR) > 0) {
			while (cMax.get(Calendar.YEAR) - cMin.get(Calendar.YEAR) != 0) {
				diff += cMin.getActualMaximum(Calendar.DAY_OF_YEAR);
				cMin.roll(Calendar.YEAR, true);
			}
			int days = cMax.get(Calendar.DAY_OF_YEAR) - cMin.get(Calendar.DAY_OF_YEAR);
			diff += days;
		} else {
			diff += cMax.get(Calendar.DAY_OF_YEAR) - cMin.get(Calendar.DAY_OF_YEAR);
		}
		return diff;
	}

	public static long diffHours(final Date date1, final Date date2) {
		Assert.notNull(date1, "date1");
		Assert.notNull(date2, "date2");
		long diff = 0;
		Calendar cMin = Calendar.getInstance();
		Calendar cMax = Calendar.getInstance();

		if (date1.compareTo(date2) > 0) {
			cMin.setTime(date2);
			cMax.setTime(date1);
		} else {
			cMin.setTime(date1);
			cMax.setTime(date2);
		}

		diff = DateUtils.diffDays(date1, date2) * (cMin.getActualMaximum(Calendar.HOUR_OF_DAY) + 1);

		int hours = cMax.get(Calendar.HOUR_OF_DAY) - cMin.get(Calendar.HOUR_OF_DAY);

		diff += hours;
		return diff;
	}

	public static long diffMonths(final Date date1, final Date date2) {
		Assert.notNull(date1, "date1");
		Assert.notNull(date2, "date2");
		long diff = 0;
		Calendar cMin = Calendar.getInstance();
		Calendar cMax = Calendar.getInstance();

		if (date1.compareTo(date2) > 0) {
			cMin.setTime(date2);
			cMax.setTime(date1);
		} else {
			cMin.setTime(date1);
			cMax.setTime(date2);
		}

		if (cMax.get(Calendar.YEAR) - cMin.get(Calendar.YEAR) > 0) {
			while (cMax.get(Calendar.YEAR) - cMin.get(Calendar.YEAR) != 0) {
				diff += cMin.getActualMaximum(Calendar.MONTH) + 1;
				cMin.roll(Calendar.YEAR, true);
			}
			int months = cMax.get(Calendar.MONTH) - cMin.get(Calendar.MONTH);
			diff += months;
		} else {
			diff += cMax.get(Calendar.MONTH) - cMin.get(Calendar.MONTH);
		}
		return diff;
	}

	public static long diffYears(final Date date1, final Date date2) {
		Assert.notNull(date1, "date1");
		Assert.notNull(date2, "date2");
		long months = DateUtils.diffMonths(date1, date2);
		Calendar c = Calendar.getInstance();
		int max = c.getActualMaximum(Calendar.MONTH) + 1;

		return months / max;
	}

}
