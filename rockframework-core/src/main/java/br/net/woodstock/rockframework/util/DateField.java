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

public enum DateField {

	DAY_OF_MONTH(Calendar.DAY_OF_MONTH, false), DAY_OF_WEEK(Calendar.DAY_OF_WEEK, true), HOUR(Calendar.HOUR_OF_DAY, false), MINUTE(Calendar.MINUTE, false), MONTH(Calendar.MONTH, false), SECOND(Calendar.SECOND, false), YEAR(Calendar.YEAR, false);

	private int		calendarField;

	private boolean	readOnly;

	private DateField(final int calendarField, final boolean readOnly) {
		this.calendarField = calendarField;
		this.readOnly = readOnly;
	}

	public int getCalendarField() {
		return this.calendarField;
	}

	public boolean isReadOnly() {
		return this.readOnly;
	}

}
