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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.net.woodstock.rockframework.domain.util.Range;
import br.net.woodstock.rockframework.util.Assert;

public class RangeCalendarDataModel extends BaseCalendarDataModel {

	private static final long		serialVersionUID	= -4873563652400201186L;

	private static final String		DATE_FORMAT			= "yyyyMMdd";

	private List<Range<Date>>		dates;

	private CalendarDataModelConfig	config;

	private DateFormat				dateFormat			= new SimpleDateFormat(RangeCalendarDataModel.DATE_FORMAT);

	public RangeCalendarDataModel(final List<Range<Date>> dates) {
		this(dates, new CalendarDataModelConfig());
	}

	public RangeCalendarDataModel(final List<Range<Date>> dates, final CalendarDataModelConfig config) {
		super();
		Assert.notNull(dates, "dates");
		Assert.notNull(config, "config");
		this.dates = dates;
		this.config = config;
	}

	@Override
	protected boolean isEnabled(final Calendar calendar) {
		long time = Long.parseLong(this.dateFormat.format(calendar.getTime()));
		for (Range<Date> range : this.dates) {
			long start = Long.parseLong(this.dateFormat.format(range.getStart()));
			long end = Long.parseLong(this.dateFormat.format(range.getEnd()));
			if ((time >= start) && (time <= end)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected CalendarDataModelConfig getConfig() {
		return this.config;
	}

}