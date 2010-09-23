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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateFormatFactory extends FormatFactory<DateFormat> {

	private static DateFormatFactory	instance	= new DateFormatFactory();

	private DateFormatFactory() {
		super();
	}

	@Override
	public DateFormat getFormat(final String pattern, final Locale locale) {
		Assert.notEmpty(pattern, "pattern");
		Assert.notNull(locale, "locale");

		if (this.containsOnCache(pattern, locale)) {
			return this.getFromCache(pattern, locale);
		}
		ImmutableDateFormat format = new ImmutableDateFormat(new SimpleDateFormat(pattern, locale));
		this.addToCache(pattern, locale, format);
		return format;
	}

	// Instance
	public static DateFormatFactory getInstance() {
		return instance;
	}
}
