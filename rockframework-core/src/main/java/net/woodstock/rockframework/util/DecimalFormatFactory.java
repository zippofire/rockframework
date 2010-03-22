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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.utils.StringUtils;

public final class DecimalFormatFactory extends FormatFactory<DecimalFormat> {

	private static DecimalFormatFactory	instance	= new DecimalFormatFactory();

	private DecimalFormatFactory() {
		super();
	}

	@Override
	public DecimalFormat getFormat(final String pattern, final Locale locale) {
		if (StringUtils.isEmpty(pattern)) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_EMPTY, "Pattern"));
		}
		if (locale == null) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_NULL, "Locale"));
		}

		if (this.containsOnCache(pattern, locale)) {
			return this.getFromCache(pattern, locale);
		}

		DecimalFormat format = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
		this.addToCache(pattern, locale, format);
		return format;
	}

	// Instance
	public static DecimalFormatFactory getInstance() {
		return instance;
	}
}
