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
package br.net.woodstock.rockframework.web.struts2.converter;

import java.util.Date;

import br.net.woodstock.rockframework.config.CoreConfig;
import br.net.woodstock.rockframework.web.types.TimeType;


public class TimeConverter extends DateTimeConverter<TimeType> {

	private static final String	TIME_FORMAT_PROPERTY	= "format.time";

	private static final String	TIME_FORMAT_PATTERN		= CoreConfig.getInstance().getValue(TimeConverter.TIME_FORMAT_PROPERTY);

	public TimeConverter() {
		super(TimeConverter.TIME_FORMAT_PATTERN);
	}

	public TimeConverter(final String pattern) {
		super(pattern);
	}

	@Override
	protected TimeType wrap(final Date d) {
		return new TimeType(d);
	}

}
