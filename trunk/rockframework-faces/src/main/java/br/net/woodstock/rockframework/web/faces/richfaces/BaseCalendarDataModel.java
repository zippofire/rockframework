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
import java.util.Calendar;
import java.util.Date;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

public abstract class BaseCalendarDataModel implements CalendarDataModel, Serializable {

	private static final long	serialVersionUID	= -2459611191563842274L;

	@Override
	public CalendarDataModelItem[] getData(final Date[] dateArray) {
		CalendarDataModelItemImpl[] modelItems = new CalendarDataModelItemImpl[dateArray.length];
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		CalendarDataModelConfig config = this.getConfig();
		boolean beforeEnabled = true;
		boolean weekendEnabled = true;

		if ((config.getDateMode() == DateMode.FUTURE_WITH_WEEKEND) || (config.getDateMode() == DateMode.FUTURE_WITHOUT_WEEKEND)) {
			beforeEnabled = false;
		}

		if ((config.getDateMode() == DateMode.FUTURE_WITHOUT_WEEKEND) || (config.getDateMode() == DateMode.FUTURE_WITHOUT_WEEKEND) || (config.getDateMode() == DateMode.PAST_WITHOUT_WEEKEND)) {
			weekendEnabled = false;
		}

		for (int i = 0; i < dateArray.length; i++) {
			current.setTime(dateArray[i]);
			CalendarDataModelItemImpl modelItem = new CalendarDataModelItemImpl();

			if ((!beforeEnabled) && (today.after(current))) {
				modelItem.setEnabled(false);
				modelItem.setStyleClass(config.getDisabledStyleClass());
			} else if (!this.isEnabled(current)) {
				modelItem.setEnabled(false);
				modelItem.setStyleClass(config.getDisabledStyleClass());
			} else if ((!weekendEnabled) && (this.isWeekend(current))) {
				modelItem.setEnabled(false);
				modelItem.setStyleClass(config.getWeekendStyleClass());
			} else {
				modelItem.setEnabled(true);
				modelItem.setStyleClass(config.getEnabledStyleClass());
			}
			modelItems[i] = modelItem;
		}

		return modelItems;
	}

	@Override
	public Object getToolTip(final Date date) {
		return null;
	}

	protected boolean isWeekend(final Calendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY) {
			return true;
		}
		if (day == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	protected abstract boolean isEnabled(final Calendar calendar);

	protected abstract CalendarDataModelConfig getConfig();

}