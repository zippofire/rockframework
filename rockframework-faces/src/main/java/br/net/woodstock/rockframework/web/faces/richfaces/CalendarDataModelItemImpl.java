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

import org.richfaces.model.CalendarDataModelItem;

public class CalendarDataModelItemImpl implements CalendarDataModelItem, Serializable {

	private static final long	serialVersionUID	= 6587875449514099385L;

	private boolean				enabled;

	private String				styleClass;

	private Object				data;

	private Object				toolTip;

	private int					day;

	public CalendarDataModelItemImpl() {
		super();
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(final String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public Object getData() {
		return this.data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	@Override
	public boolean hasToolTip() {
		return this.getToolTip() != null;
	}

	@Override
	public Object getToolTip() {
		return this.toolTip;
	}

	public void setToolTip(final Object toolTip) {
		this.toolTip = toolTip;
	}

	@Override
	public int getDay() {
		return this.day;
	}

	public void setDay(final int day) {
		this.day = day;
	}

}
