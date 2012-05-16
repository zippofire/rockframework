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
		this.calendar.add(Calendar.DAY_OF_YEAR, days);
		return this;
	}

	public DateBuilder addHours(final int hours) {
		this.calendar.add(Calendar.HOUR_OF_DAY, hours);
		return this;
	}

	public DateBuilder addMinutes(final int minutes) {
		this.calendar.add(Calendar.MINUTE, minutes);
		return this;
	}

	public DateBuilder addMonths(final int months) {
		this.calendar.add(Calendar.MONTH, months);
		return this;
	}

	public DateBuilder addYears(final int years) {
		this.calendar.add(Calendar.YEAR, years);
		return this;
	}

	public DateBuilder removeDays(final int days) {
		this.calendar.add(Calendar.DAY_OF_YEAR, Math.abs(days));
		return this;
	}

	public DateBuilder removeHours(final int hours) {
		this.calendar.add(Calendar.HOUR_OF_DAY, Math.abs(hours));
		return this;
	}

	public DateBuilder removeMinutes(final int minutes) {
		this.calendar.add(Calendar.MINUTE, Math.abs(minutes));
		return this;
	}

	public DateBuilder removeMonths(final int months) {
		this.calendar.add(Calendar.MONTH, Math.abs(months));
		return this;
	}

	public DateBuilder removeYears(final int years) {
		this.calendar.add(Calendar.YEAR, Math.abs(years));
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
	public int getValue(final DateField field) {
		Assert.notNull(field, "field");
		return this.calendar.get(field.getCalendarField());
	}

	public void setValue(final DateField field, final int value) {
		Assert.notNull(field, "field");
		if (field.isReadOnly()) {
			throw new IllegalArgumentException("Cannot set " + field + " value");
		}
		this.calendar.set(field.getCalendarField(), value);
	}
}
