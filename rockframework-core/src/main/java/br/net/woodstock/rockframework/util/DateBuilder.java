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
package br.net.woodstock.rockframework.util;

import java.util.Calendar;
import java.util.Date;

public class DateBuilder {

	private Calendar	calendar;

	public DateBuilder() {
		this(new Date());
	}

	public DateBuilder(final long time) {
		this(new Date(time));
	}

	public DateBuilder(final Date date) {
		super();
		Assert.notNull(date, "date");
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(date);
	}

	public Date getDate() {
		return this.calendar.getTime();
	}

	// Aux
	public DateBuilder addDays(final int days) {
		if (days < 0) {
			this.removeDays(Math.abs(days));
		} else {
			int maxDays = this.calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			int currentDays = days;
			while (currentDays > maxDays) {
				this.addYears(1);
				currentDays -= maxDays;
				maxDays = this.calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			}
			if ((currentDays + this.calendar.get(Calendar.DAY_OF_YEAR)) > maxDays) {
				this.addYears(1);
				currentDays = maxDays - currentDays;
				this.roll(currentDays, Calendar.DAY_OF_YEAR, false);
			} else {
				this.roll(currentDays, Calendar.DAY_OF_YEAR, true);
			}
		}
		return this;
	}

	public DateBuilder addHours(final int hours) {
		if (hours < 0) {
			this.removeHours(Math.abs(hours));
		} else {
			int maxHours = this.calendar.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
			int currentHours = hours;
			while (currentHours > maxHours) {
				this.addDays(1);
				currentHours -= maxHours;
				maxHours = this.calendar.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
			}
			if ((currentHours + this.calendar.get(Calendar.HOUR_OF_DAY)) > maxHours) {
				this.addDays(1);
				currentHours = maxHours - currentHours;
				this.roll(currentHours, Calendar.HOUR_OF_DAY, false);
			} else {
				this.roll(currentHours, Calendar.HOUR_OF_DAY, true);
			}
		}
		return this;
	}

	public DateBuilder addMinutes(final int minutes) {
		if (minutes < 0) {
			this.removeMinutes(Math.abs(minutes));
		} else {
			int maxMinutes = this.calendar.getActualMaximum(Calendar.MINUTE) + 1;
			int currentMinutes = minutes;
			while (currentMinutes > maxMinutes) {
				this.addHours(1);
				currentMinutes -= maxMinutes;
			}
			if ((currentMinutes + this.calendar.get(Calendar.MINUTE)) > maxMinutes) {
				this.addHours(1);
				currentMinutes = maxMinutes - currentMinutes;
				this.roll(currentMinutes, Calendar.MINUTE, false);
			} else {
				this.roll(currentMinutes, Calendar.MINUTE, true);
			}
		}
		return this;
	}

	public DateBuilder addMonths(final int months) {
		if (months < 0) {
			this.removeMonths(Math.abs(months));
		} else {
			int maxMonths = this.calendar.getActualMaximum(Calendar.MONTH) + 1;
			int currentMonths = months;
			while (currentMonths > maxMonths) {
				this.addYears(1);
				currentMonths -= maxMonths;
				maxMonths = this.calendar.getActualMaximum(Calendar.MONTH) + 1;
			}
			if ((currentMonths + this.calendar.get(Calendar.MONTH)) > maxMonths) {
				this.addYears(1);
				currentMonths = maxMonths - currentMonths;
				this.roll(currentMonths, Calendar.MONTH, false);
			} else {
				this.roll(currentMonths, Calendar.MONTH, true);
			}
		}
		return this;
	}

	public DateBuilder addYears(final int years) {
		if (years < 0) {
			this.removeYears(Math.abs(years));
		} else {
			this.roll(years, Calendar.YEAR, true);
		}
		return this;
	}

	public DateBuilder removeDays(final int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(this.calendar.getTime());
		c.roll(Calendar.YEAR, false);
		int maxDays = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		int currentDays = days;
		while (currentDays > maxDays) {
			this.removeYears(1);
			currentDays -= maxDays;
			c.setTime(this.calendar.getTime());
			c.roll(Calendar.YEAR, false);
			maxDays = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		}
		if (currentDays >= this.calendar.get(Calendar.DAY_OF_YEAR)) {
			this.removeYears(1);
			currentDays = maxDays - currentDays;
			this.roll(currentDays, Calendar.DAY_OF_YEAR, true);
		} else {
			this.roll(currentDays, Calendar.DAY_OF_YEAR, false);
		}
		return this;
	}

	public DateBuilder removeHours(final int hours) {
		int maxHours = this.calendar.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
		int currentHours = hours;
		while (currentHours > maxHours) {
			this.removeDays(1);
			currentHours -= maxHours;
			maxHours = this.calendar.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
		}
		if (currentHours >= this.calendar.get(Calendar.HOUR_OF_DAY)) {
			this.removeDays(1);
			currentHours = maxHours - currentHours;
			this.roll(currentHours, Calendar.HOUR_OF_DAY, true);
		} else {
			this.roll(currentHours, Calendar.HOUR_OF_DAY, false);
		}
		return this;
	}

	public DateBuilder removeMinutes(final int minutes) {
		int maxMinutes = this.calendar.getActualMaximum(Calendar.MINUTE) + 1;
		int currentMinutes = minutes;
		while (currentMinutes > maxMinutes) {
			this.removeHours(1);
			currentMinutes -= maxMinutes;
		}
		if (currentMinutes >= this.calendar.get(Calendar.MINUTE)) {
			this.removeHours(1);
			currentMinutes = maxMinutes - currentMinutes;
			this.roll(currentMinutes, Calendar.MINUTE, true);
		} else {
			this.roll(currentMinutes, Calendar.MINUTE, false);
		}
		return this;
	}

	public DateBuilder removeMonths(final int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(this.calendar.getTime());
		c.roll(Calendar.YEAR, false);
		int maxMonths = c.getActualMaximum(Calendar.MONTH) + 1;
		int currentMonths = months;
		while (currentMonths > maxMonths) {
			this.removeYears(1);
			currentMonths -= maxMonths;
			c.setTime(this.calendar.getTime());
			c.roll(Calendar.YEAR, false);
			maxMonths = c.getActualMaximum(Calendar.MONTH) + 1;
		}
		if (currentMonths >= this.calendar.get(Calendar.MONTH)) {
			this.removeYears(1);
			currentMonths = maxMonths - currentMonths;
			this.roll(currentMonths, Calendar.MONTH, true);
		} else {
			this.roll(currentMonths, Calendar.MONTH, false);
		}
		return this;
	}

	public DateBuilder removeYears(final int years) {
		this.roll(years, Calendar.YEAR, false);
		return this;
	}

	public boolean isWeekend() {
		if (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		if (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	// Aux
	public int getFieldValue(final DateField field) {
		Assert.notNull(field, "field");
		return this.calendar.get(field.getCalendarField());
	}

	public void setFieldValue(final DateField field, final int value) {
		Assert.notNull(field, "field");
		this.calendar.set(field.getCalendarField(), value);
	}

	// Internal
	private void roll(final int count, final int field, final boolean up) {
		for (int i = 0; i < count; i++) {
			this.calendar.roll(field, up);
		}
	}
}
