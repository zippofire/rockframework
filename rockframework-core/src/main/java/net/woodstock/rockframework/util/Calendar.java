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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import net.woodstock.rockframework.utils.LocaleUtils;

public final class Calendar extends GregorianCalendar {

	private static final long		serialVersionUID			= -5683383305791225831L;

	public static final String		AM_PM_FORMAT				= "%a";

	public static final String		ERA_FORMAT					= "%G";

	public static final String		DAY_IN_MONTH_FORMAT			= "%d";

	public static final String		DAY_IN_MONTH_LONG_FORMAT	= "%dd";

	public static final String		DAY_NAME_FORMAT				= "%EEEEE";

	public static final String		DAY_OF_WEEK_IN_MONTH_FORMAT	= "%F";

	public static final String		DAY_IN_YEAR_FORMAT			= "%D";

	public static final String		HOUR12_FORMAT				= "%hh";

	public static final String		HOUR24_FORMAT				= "%HH";

	public static final String		MILLISECOND_FORMAT			= "%S";

	public static final String		MINUTE_FORMAT				= "%mm";

	public static final String		MONTH_FORMAT				= "%MM";

	public static final String		MONTH_NAME_FORMAT			= "%MMMMM";

	public static final String		SECOND_FORMAT				= "%ss";

	public static final String		TIMEZONE_FORMAT				= "%Z";

	public static final String		YEAR_FORMAT					= "%y";

	public static final String		YEAR_LONG_FORMAT			= "%yyyy";

	public static final String		WEEK_IN_YEAR_FORMAT			= "%w";

	public static final String		WEEK_IN_MONTH_FORMAT		= "%W";

	private static final String[]	ALL_FORMAT					= new String[] { Calendar.AM_PM_FORMAT, Calendar.ERA_FORMAT, Calendar.DAY_IN_MONTH_FORMAT, Calendar.DAY_IN_MONTH_LONG_FORMAT, Calendar.DAY_NAME_FORMAT, Calendar.DAY_OF_WEEK_IN_MONTH_FORMAT, Calendar.DAY_IN_YEAR_FORMAT, Calendar.HOUR12_FORMAT, Calendar.HOUR24_FORMAT, Calendar.MILLISECOND_FORMAT, Calendar.MINUTE_FORMAT, Calendar.MONTH_FORMAT, Calendar.MONTH_NAME_FORMAT, Calendar.SECOND_FORMAT, Calendar.TIMEZONE_FORMAT, Calendar.YEAR_FORMAT, Calendar.YEAR_LONG_FORMAT, Calendar.WEEK_IN_YEAR_FORMAT, Calendar.WEEK_IN_MONTH_FORMAT };

	private Locale					locale;

	private Calendar(final TimeZone tz, final Locale l) {
		super(tz, l);
		this.locale = l;
	}

	private Calendar(final Date date, final TimeZone tz, final Locale l) {
		this(tz, l);
		this.setTime(date);
	}

	// Locale
	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	// Utils
	public void addDays(final int days) {
		if (days < 0) {
			this.removeDays(Math.abs(days));
		} else {
			int maxDays = this.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
			int currentDays = days;
			while (currentDays > maxDays) {
				this.addYears(1);
				currentDays -= maxDays;
				maxDays = this.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
			}
			if (currentDays + this.get(java.util.Calendar.DAY_OF_YEAR) > maxDays) {
				this.addYears(1);
				currentDays = maxDays - currentDays;
				this.roll(currentDays, java.util.Calendar.DAY_OF_YEAR, false);
			} else {
				this.roll(currentDays, java.util.Calendar.DAY_OF_YEAR, true);
			}
		}
	}

	public void addHours(final int hours) {
		if (hours < 0) {
			this.removeHours(Math.abs(hours));
		} else {
			int maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
			int currentHours = hours;
			while (currentHours > maxHours) {
				this.addDays(1);
				currentHours -= maxHours;
				maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
			}
			if (currentHours + this.get(java.util.Calendar.HOUR_OF_DAY) > maxHours) {
				this.addDays(1);
				currentHours = maxHours - currentHours;
				this.roll(currentHours, java.util.Calendar.HOUR_OF_DAY, false);
			} else {
				this.roll(currentHours, java.util.Calendar.HOUR_OF_DAY, true);
			}
		}
	}

	public void addMinutes(final int minutes) {
		if (minutes < 0) {
			this.removeMinutes(Math.abs(minutes));
		} else {
			int maxMinutes = this.getActualMaximum(java.util.Calendar.MINUTE) + 1;
			int currentMinutes = minutes;
			while (currentMinutes > maxMinutes) {
				this.addHours(1);
				currentMinutes -= maxMinutes;
			}
			if (currentMinutes + this.get(java.util.Calendar.MINUTE) > maxMinutes) {
				this.addHours(1);
				currentMinutes = maxMinutes - currentMinutes;
				this.roll(currentMinutes, java.util.Calendar.MINUTE, false);
			} else {
				this.roll(currentMinutes, java.util.Calendar.MINUTE, true);
			}
		}
	}

	public void addMonths(final int months) {
		if (months < 0) {
			this.removeMonths(Math.abs(months));
		} else {
			int maxMonths = this.getActualMaximum(java.util.Calendar.MONTH) + 1;
			int currentMonths = months;
			while (currentMonths > maxMonths) {
				this.addYears(1);
				currentMonths -= maxMonths;
				maxMonths = this.getActualMaximum(java.util.Calendar.MONTH) + 1;
			}
			if (currentMonths + this.get(java.util.Calendar.MONTH) > maxMonths) {
				this.addYears(1);
				currentMonths = maxMonths - currentMonths;
				this.roll(currentMonths, java.util.Calendar.MONTH, false);
			} else {
				this.roll(currentMonths, java.util.Calendar.MONTH, true);
			}
		}
	}

	public void addYears(final int years) {
		if (years < 0) {
			this.removeYears(Math.abs(years));
		} else {
			this.roll(years, java.util.Calendar.YEAR, true);
		}
	}

	public long diffDays(final java.util.Calendar calendar) {
		long diff = 0;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(calendar.getTime());
		if (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) > 0) {
			while (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) != 0) {
				diff += c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				c.roll(java.util.Calendar.YEAR, true);
			}
			diff += this.get(java.util.Calendar.DAY_OF_YEAR) - calendar.get(java.util.Calendar.DAY_OF_YEAR);
		} else if (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) < 0) {
			while (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) != 0) {
				diff -= c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				c.roll(java.util.Calendar.YEAR, false);
			}
			diff -= this.get(java.util.Calendar.DAY_OF_YEAR) - c.get(java.util.Calendar.DAY_OF_YEAR);
		} else {
			diff += this.get(java.util.Calendar.DAY_OF_YEAR) - c.get(java.util.Calendar.DAY_OF_YEAR);
		}
		return diff;
	}

	public long diffHours(final java.util.Calendar calendar) {
		long diff = 0;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(calendar.getTime());
		diff = this.diffDays(calendar) * (this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1);
		diff += this.get(java.util.Calendar.HOUR_OF_DAY) - c.get(java.util.Calendar.HOUR_OF_DAY);
		return diff;
	}

	public long diffMonths(final java.util.Calendar calendar) {
		long diff = 0;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(calendar.getTime());
		if (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) > 0) {
			while (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) != 0) {
				diff += c.getActualMaximum(java.util.Calendar.MONTH) + 1;
				c.roll(java.util.Calendar.YEAR, true);
			}
			diff += (this.get(java.util.Calendar.MONTH) - c.get(java.util.Calendar.MONTH)) + 1;
		} else if (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) < 0) {
			while (this.get(java.util.Calendar.YEAR) - c.get(java.util.Calendar.YEAR) != 0) {
				diff -= c.getActualMaximum(java.util.Calendar.MONTH) + 1;
				c.roll(java.util.Calendar.YEAR, false);
			}
			diff -= (this.get(java.util.Calendar.MONTH) - c.get(java.util.Calendar.MONTH)) + 1;
		} else {
			diff += (this.get(java.util.Calendar.MONTH) - c.get(java.util.Calendar.MONTH)) + 1;
		}
		return diff;
	}

	public long diffYears(final java.util.Calendar calendar) {
		long diff = this.get(java.util.Calendar.YEAR) - calendar.get(java.util.Calendar.YEAR);
		return diff;
	}

	public int getDay() {
		return this.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public int getDayOfMonth() {
		return this.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public int getDayOfWeek() {
		return this.get(java.util.Calendar.DAY_OF_WEEK);
	}

	public int getDayOfWeekInMonth() {
		return this.get(java.util.Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	public int getDayOfYear() {
		return this.get(java.util.Calendar.DAY_OF_YEAR);
	}

	public int getHour() {
		return this.get(java.util.Calendar.HOUR_OF_DAY);
	}

	public int getHour24() {
		return this.get(java.util.Calendar.HOUR_OF_DAY);
	}

	public int getHourAmPm() {
		return this.get(java.util.Calendar.HOUR);
	}

	public int getMinute() {
		return this.get(java.util.Calendar.MINUTE);
	}

	public int getMonth() {
		return this.get(java.util.Calendar.MONTH) + 1;
	}

	public int getSecond() {
		return this.get(java.util.Calendar.SECOND);
	}

	public int getYear() {
		return this.get(java.util.Calendar.YEAR);
	}

	public String getFormated(final String format) {
		String date = format;
		for (String s : Calendar.ALL_FORMAT) {
			while (format.indexOf(s) != -1) {
				SimpleDateFormat sdf = new SimpleDateFormat(s.replace("%", ""), this.locale);
				date = format.replace(s, sdf.format(this.getTime()));
			}
		}
		return date;
	}

	public void removeDays(final int days) {
		Calendar c = (Calendar) this.clone();
		c.roll(java.util.Calendar.YEAR, false);
		int maxDays = c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
		int currentDays = days;
		while (currentDays > maxDays) {
			this.removeYears(1);
			currentDays -= maxDays;
			c.setTime(this.getTime());
			c.roll(java.util.Calendar.YEAR, false);
			maxDays = c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
		}
		if (currentDays >= this.get(java.util.Calendar.DAY_OF_YEAR)) {
			this.removeYears(1);
			currentDays = maxDays - currentDays;
			this.roll(currentDays, java.util.Calendar.DAY_OF_YEAR, true);
		} else {
			this.roll(currentDays, java.util.Calendar.DAY_OF_YEAR, false);
		}
	}

	public void removeHours(final int hours) {
		int maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
		int currentHours = hours;
		while (currentHours > maxHours) {
			this.removeDays(1);
			currentHours -= maxHours;
			maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
		}
		if (currentHours >= this.get(java.util.Calendar.HOUR_OF_DAY)) {
			this.removeDays(1);
			currentHours = maxHours - currentHours;
			this.roll(currentHours, java.util.Calendar.HOUR_OF_DAY, true);
		} else {
			this.roll(currentHours, java.util.Calendar.HOUR_OF_DAY, false);
		}
	}

	public void removeMinutes(final int minutes) {
		int maxMinutes = this.getActualMaximum(java.util.Calendar.MINUTE) + 1;
		int currentMinutes = minutes;
		while (currentMinutes > maxMinutes) {
			this.removeHours(1);
			currentMinutes -= maxMinutes;
		}
		if (currentMinutes >= this.get(java.util.Calendar.MINUTE)) {
			this.removeHours(1);
			currentMinutes = maxMinutes - currentMinutes;
			this.roll(currentMinutes, java.util.Calendar.MINUTE, true);
		} else {
			this.roll(currentMinutes, java.util.Calendar.MINUTE, false);
		}
	}

	public void removeMonths(final int months) {
		Calendar c = (Calendar) this.clone();
		c.roll(java.util.Calendar.YEAR, false);
		int maxMonths = c.getActualMaximum(java.util.Calendar.MONTH) + 1;
		int currentMonths = months;
		while (currentMonths > maxMonths) {
			this.removeYears(1);
			currentMonths -= maxMonths;
			c.setTime(this.getTime());
			c.roll(java.util.Calendar.YEAR, false);
			maxMonths = c.getActualMaximum(java.util.Calendar.MONTH) + 1;
		}
		if (currentMonths >= this.get(java.util.Calendar.MONTH)) {
			this.removeYears(1);
			currentMonths = maxMonths - currentMonths;
			this.roll(currentMonths, java.util.Calendar.MONTH, true);
		} else {
			this.roll(currentMonths, java.util.Calendar.MONTH, false);
		}
	}

	public void removeYears(final int years) {
		this.roll(years, java.util.Calendar.YEAR, false);
	}

	private void roll(final int count, final int field, final boolean up) {
		for (int i = 0; i < count; i++) {
			this.roll(field, up);
		}
	}

	@Override
	public Object clone() {
		Calendar c = Calendar.getInstance(this.getTimeZone(), this.getLocale());
		c.setTime(this.getTime());
		return c;
	}

	@Override
	public int compareTo(final java.util.Calendar anotherCalendar) {
		return super.compareTo(anotherCalendar);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof java.util.Calendar)) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return this.getTime().toString();
	}

	// Instance
	public static Calendar getInstance() {
		return new Calendar(TimeZone.getDefault(), LocaleUtils.getLocale());
	}

	public static Calendar getInstance(final Locale l) {
		return new Calendar(TimeZone.getDefault(), l);
	}

	public static Calendar getInstance(final TimeZone tz) {
		return new Calendar(tz, Locale.getDefault());
	}

	public static Calendar getInstance(final TimeZone tz, final Locale l) {
		return new Calendar(tz, l);
	}

	public static Calendar toCalendar(final java.util.Calendar c) {
		return new Calendar(c.getTime(), TimeZone.getDefault(), LocaleUtils.getLocale());
	}

	public static Calendar toCalendar(final java.util.Calendar c, final TimeZone tz, final Locale l) {
		return new Calendar(c.getTime(), tz, l);
	}

	public static Calendar toCalendar(final Date d) {
		return new Calendar(d, TimeZone.getDefault(), LocaleUtils.getLocale());
	}

	public static Calendar toCalendar(final Date d, final TimeZone tz, final Locale l) {
		return new Calendar(d, tz, l);
	}

	public static Calendar toCalendar(final long d) {
		return new Calendar(new Date(d), TimeZone.getDefault(), LocaleUtils.getLocale());
	}

	public static Calendar toCalendar(final long d, final TimeZone tz, final Locale l) {
		return new Calendar(new Date(d), tz, l);
	}

}
