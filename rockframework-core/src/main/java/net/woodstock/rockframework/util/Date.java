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

public class Date extends java.util.Date {

	private static final long	serialVersionUID	= 8877640332648754076L;

	public Date() {
		super();
	}

	public Date(final long date) {
		super(date);
	}

	public Date(final java.util.Date date) {
		super(date.getTime());
	}

	public void addDays(final int days) {
		Calendar c = Calendar.toCalendar(this);
		c.addDays(days);
		this.setTime(c.getTime().getTime());
	}

	public void addHours(final int hours) {
		Calendar c = Calendar.toCalendar(this);
		c.addHours(hours);
		this.setTime(c.getTime().getTime());
	}

	public void addMonths(final int months) {
		Calendar c = Calendar.toCalendar(this);
		c.addMonths(months);
		this.setTime(c.getTime().getTime());
	}

	public void addYears(final int years) {
		Calendar c = Calendar.toCalendar(this);
		c.addYears(years);
		this.setTime(c.getTime().getTime());
	}

	public long diffDays(final java.util.Date date) {
		Calendar c = Calendar.toCalendar(this);
		return c.diffDays(Calendar.toCalendar(date));
	}

	public long diffHours(final java.util.Date date) {
		Calendar c = Calendar.toCalendar(this);
		return c.diffHours(Calendar.toCalendar(date));
	}

	public long diffMonths(final java.util.Date date) {
		Calendar c = Calendar.toCalendar(this);
		return c.diffMonths(Calendar.toCalendar(date));
	}

	public long diffYears(final java.util.Date date) {
		Calendar c = Calendar.toCalendar(this);
		return c.diffYears(Calendar.toCalendar(date));
	}

	public void removeDays(final int days) {
		Calendar c = Calendar.toCalendar(this);
		c.removeDays(days);
		this.setTime(c.getTime().getTime());
	}

	public void removeHours(final int hours) {
		Calendar c = Calendar.toCalendar(this);
		c.removeHours(hours);
		this.setTime(c.getTime().getTime());
	}

	public void removeMonths(final int months) {
		Calendar c = Calendar.toCalendar(this);
		c.removeMonths(months);
		this.setTime(c.getTime().getTime());
	}

	public void removeYears(final int years) {
		Calendar c = Calendar.toCalendar(this);
		c.removeYears(years);
		this.setTime(c.getTime().getTime());
	}

}
