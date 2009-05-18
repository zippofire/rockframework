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

public class Calendar extends GregorianCalendar {

	private static final long		serialVersionUID			= -5683383305791225831L;

	public static final String		AM_PM_FORMAT				= "%a";

	public static final String		ERA_FORMAT					= "%G";

	public static final String		DAY_IN_MONTH_FORMAT			= "%d";

	public static final String		DAY_NAME_FORMAT				= "%EEEEE";

	public static final String		DAY_OF_WEEK_IN_MONTH_FORMAT	= "%F";

	public static final String		DAY_IN_YEAR_FORMAT			= "%D";

	public static final String		HOUR12_FORMAT				= "%K";

	public static final String		HOUR24_FORMAT				= "%H";

	public static final String		MILLISECOND_FORMAT			= "%S";

	public static final String		MINUTE_FORMAT				= "%m";

	public static final String		MONTH_FORMAT				= "%MM";

	public static final String		MONTH_NAME_FORMAT			= "%MMMMM";

	public static final String		SECOND_FORMAT				= "%s";

	public static final String		TIMEZONE_FORMAT				= "%Z";

	public static final String		YEAR_FORMAT					= "%y";

	public static final String		WEEK_IN_YEAR_FORMAT			= "%w";

	public static final String		WEEK_IN_MONTH_FORMAT		= "%W";

	private static final String[]	ALL_FORMAT					= new String[] { Calendar.AM_PM_FORMAT,
			Calendar.ERA_FORMAT, Calendar.DAY_IN_MONTH_FORMAT, Calendar.DAY_NAME_FORMAT,
			Calendar.DAY_OF_WEEK_IN_MONTH_FORMAT, Calendar.DAY_IN_YEAR_FORMAT, Calendar.HOUR12_FORMAT,
			Calendar.HOUR24_FORMAT, Calendar.MILLISECOND_FORMAT, Calendar.MINUTE_FORMAT,
			Calendar.MONTH_FORMAT, Calendar.MONTH_NAME_FORMAT, Calendar.SECOND_FORMAT,
			Calendar.TIMEZONE_FORMAT, Calendar.YEAR_FORMAT, Calendar.WEEK_IN_YEAR_FORMAT,
			Calendar.WEEK_IN_MONTH_FORMAT

																};

	private Locale					locale;

	private Calendar(TimeZone tz, Locale l) {
		super(tz, l);
		this.locale = l;
	}

	private Calendar(Date date, TimeZone tz, Locale l) {
		this(tz, l);
		this.setTime(date);
	}

	// Locale
	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void addDays(int days) {
		if (days < 0) {
			this.removeDays(Math.abs(days));
		} else {
			int maxDays = this.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
			while (days > maxDays) {
				this.addYears(1);
				days -= maxDays;
				maxDays = this.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
			}
			if (days + this.get(java.util.Calendar.DAY_OF_YEAR) > maxDays) {
				this.addYears(1);
				days = maxDays - days;
				this.roll(days, java.util.Calendar.DAY_OF_YEAR, false);
			} else {
				this.roll(days, java.util.Calendar.DAY_OF_YEAR, true);
			}
		}
	}

	public void addHours(int hours) {
		if (hours < 0) {
			this.removeHours(Math.abs(hours));
		} else {
			int maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
			while (hours > maxHours) {
				this.addDays(1);
				hours -= maxHours;
				maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
			}
			if (hours + this.get(java.util.Calendar.HOUR_OF_DAY) > maxHours) {
				this.addDays(1);
				hours = maxHours - hours;
				this.roll(hours, java.util.Calendar.HOUR_OF_DAY, false);
			} else {
				this.roll(hours, java.util.Calendar.HOUR_OF_DAY, true);
			}
		}
	}

	public void addMinutes(int minutes) {
		if (minutes < 0) {
			this.removeMinutes(Math.abs(minutes));
		} else {
			int maxMinutes = this.getActualMaximum(java.util.Calendar.MINUTE) + 1;
			while (minutes > maxMinutes) {
				this.addHours(1);
				minutes -= maxMinutes;
			}
			if (minutes + this.get(java.util.Calendar.MINUTE) > maxMinutes) {
				this.addHours(1);
				minutes = maxMinutes - minutes;
				this.roll(minutes, java.util.Calendar.MINUTE, false);
			} else {
				this.roll(minutes, java.util.Calendar.MINUTE, true);
			}
		}
	}

	public void addMonths(int months) {
		if (months < 0) {
			this.removeMonths(Math.abs(months));
		} else {
			int maxMonths = this.getActualMaximum(java.util.Calendar.MONTH) + 1;
			while (months > maxMonths) {
				this.addYears(1);
				months -= maxMonths;
				maxMonths = this.getActualMaximum(java.util.Calendar.MONTH) + 1;
			}
			if (months + this.get(java.util.Calendar.MONTH) > maxMonths) {
				this.addYears(1);
				months = maxMonths - months;
				this.roll(months, java.util.Calendar.MONTH, false);
			} else {
				this.roll(months, java.util.Calendar.MONTH, true);
			}
		}
	}

	public void addYears(int years) {
		if (years < 0) {
			this.removeYears(Math.abs(years));
		} else {
			this.roll(years, java.util.Calendar.YEAR, true);
		}
	}

	public long diffDays(java.util.Calendar calendar) {
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

	public long diffHours(java.util.Calendar calendar) {
		long diff = 0;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(calendar.getTime());
		diff = this.diffDays(calendar) * (this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1);
		diff += this.get(java.util.Calendar.HOUR_OF_DAY) - c.get(java.util.Calendar.HOUR_OF_DAY);
		return diff;
	}

	public long diffMonths(java.util.Calendar calendar) {
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

	public long diffYears(java.util.Calendar calendar) {
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

	public String getFormated(String format) {
		for (String s : Calendar.ALL_FORMAT) {
			while (format.indexOf(s) != -1) {
				SimpleDateFormat sdf = new SimpleDateFormat(s.replace("%", ""), this.locale);
				format = format.replace(s, sdf.format(this.getTime()));
			}
		}
		return format;
	}

	public void removeDays(int days) {
		Calendar c = (Calendar) this.clone();
		c.roll(java.util.Calendar.YEAR, false);
		int maxDays = c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
		while (days > maxDays) {
			this.removeYears(1);
			days -= maxDays;
			c.setTime(this.getTime());
			c.roll(java.util.Calendar.YEAR, false);
			maxDays = c.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
		}
		if (days >= this.get(java.util.Calendar.DAY_OF_YEAR)) {
			this.removeYears(1);
			days = maxDays - days;
			this.roll(days, java.util.Calendar.DAY_OF_YEAR, true);
		} else {
			this.roll(days, java.util.Calendar.DAY_OF_YEAR, false);
		}
	}

	public void removeHours(int hours) {
		int maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
		while (hours > maxHours) {
			this.removeDays(1);
			hours -= maxHours;
			maxHours = this.getActualMaximum(java.util.Calendar.HOUR_OF_DAY) + 1;
		}
		if (hours >= this.get(java.util.Calendar.HOUR_OF_DAY)) {
			this.removeDays(1);
			hours = maxHours - hours;
			this.roll(hours, java.util.Calendar.HOUR_OF_DAY, true);
		} else {
			this.roll(hours, java.util.Calendar.HOUR_OF_DAY, false);
		}
	}

	public void removeMinutes(int minutes) {
		int maxMinutes = this.getActualMaximum(java.util.Calendar.MINUTE) + 1;
		while (minutes > maxMinutes) {
			this.removeHours(1);
			minutes -= maxMinutes;
		}
		if (minutes >= this.get(java.util.Calendar.MINUTE)) {
			this.removeHours(1);
			minutes = maxMinutes - minutes;
			this.roll(minutes, java.util.Calendar.MINUTE, true);
		} else {
			this.roll(minutes, java.util.Calendar.MINUTE, false);
		}
	}

	public void removeMonths(int months) {
		Calendar c = (Calendar) this.clone();
		c.roll(java.util.Calendar.YEAR, false);
		int maxMonths = c.getActualMaximum(java.util.Calendar.MONTH) + 1;
		while (months > maxMonths) {
			this.removeYears(1);
			months -= maxMonths;
			c.setTime(this.getTime());
			c.roll(java.util.Calendar.YEAR, false);
			maxMonths = c.getActualMaximum(java.util.Calendar.MONTH) + 1;
		}
		if (months >= this.get(java.util.Calendar.MONTH)) {
			this.removeYears(1);
			months = maxMonths - months;
			this.roll(months, java.util.Calendar.MONTH, true);
		} else {
			this.roll(months, java.util.Calendar.MONTH, false);
		}
	}

	public void removeYears(int years) {
		this.roll(years, java.util.Calendar.YEAR, false);
	}

	private void roll(int i, int field, boolean up) {
		for (; i > 0; i--) {
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
	public int compareTo(java.util.Calendar anotherCalendar) {
		return super.compareTo(anotherCalendar);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof java.util.Calendar)) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return this.getTime().toString();
	}

	public static Calendar getInstance() {
		return new Calendar(TimeZone.getDefault(), Locale.getDefault());
	}

	public static Calendar getInstance(Locale l) {
		return new Calendar(TimeZone.getDefault(), l);
	}

	public static Calendar getInstance(TimeZone tz) {
		return new Calendar(tz, Locale.getDefault());
	}

	public static Calendar getInstance(TimeZone tz, Locale l) {
		return new Calendar(tz, l);
	}

	public static Calendar toCalendar(java.util.Calendar c) {
		return new Calendar(c.getTime(), TimeZone.getDefault(), Locale.getDefault());
	}

	public static Calendar toCalendar(java.util.Calendar c, TimeZone tz, Locale l) {
		return new Calendar(c.getTime(), tz, l);
	}

	public static Calendar toCalendar(Date d) {
		return new Calendar(d, TimeZone.getDefault(), Locale.getDefault());
	}

	public static Calendar toCalendar(Date d, TimeZone tz, Locale l) {
		return new Calendar(d, tz, l);
	}

	public static Calendar toCalendar(long d) {
		return new Calendar(new Date(d), TimeZone.getDefault(), Locale.getDefault());
	}

	public static Calendar toCalendar(long d, TimeZone tz, Locale l) {
		return new Calendar(new Date(d), tz, l);
	}

}
