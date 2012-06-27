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
package br.net.woodstock.rockframework.web.faces.richfaces;

import java.io.Serializable;

public class CalendarDataModelConfig implements Serializable {

	private static final long	serialVersionUID	= -7821486314381574669L;

	private DateMode			dateMode;

	private String				enabledStyleClass;

	private String				disabledStyleClass;

	private String				weekendStyleClass;

	public DateMode getDateMode() {
		return this.dateMode;
	}

	public void setDateMode(final DateMode dateMode) {
		this.dateMode = dateMode;
	}

	public String getEnabledStyleClass() {
		return this.enabledStyleClass;
	}

	public void setEnabledStyleClass(final String enabledStyleClass) {
		this.enabledStyleClass = enabledStyleClass;
	}

	public String getDisabledStyleClass() {
		return this.disabledStyleClass;
	}

	public void setDisabledStyleClass(final String disabledStyleClass) {
		this.disabledStyleClass = disabledStyleClass;
	}

	public String getWeekendStyleClass() {
		return this.weekendStyleClass;
	}

	public void setWeekendStyleClass(final String weekendStyleClass) {
		this.weekendStyleClass = weekendStyleClass;
	}

}
