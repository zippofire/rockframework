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

import java.text.Format;
import java.util.Locale;

public abstract class LocaleableFormatFactory<T extends Format> extends FormatFactory<T> {

	private static final String	SEPARATOR	= "@";

	public LocaleableFormatFactory() {
		super();
	}

	protected boolean containsOnCache(final String pattern, final Locale locale) {
		String key = pattern + LocaleableFormatFactory.SEPARATOR + locale;
		return super.containsOnCache(key);
	}

	protected T getFromCache(final String pattern, final Locale locale) {
		String key = pattern + LocaleableFormatFactory.SEPARATOR + locale;
		return super.getFromCache(key);
	}

	protected void addToCache(final String pattern, final Locale locale, final T format) {
		String key = pattern + LocaleableFormatFactory.SEPARATOR + locale;
		super.addToCache(key, format);
	}

	@Override
	public T getFormat(final String pattern) {
		return this.getFormat(pattern, Locale.getDefault());
	}

	public abstract T getFormat(String pattern, Locale locale);
}
