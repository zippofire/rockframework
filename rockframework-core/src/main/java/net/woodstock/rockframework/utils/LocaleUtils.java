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
package net.woodstock.rockframework.utils;

import java.nio.charset.Charset;
import java.util.Locale;

public abstract class LocaleUtils {

	private static final String		LOCALE_CHARSET	= SystemUtils.getProperty(SystemUtils.FILE_ENCODING_PROPERTY);

	private static final String		LOCALE_COUNTRY	= SystemUtils.getProperty(SystemUtils.USER_COUNTRY_PROPERTY);

	private static final String		LOCALE_LANGUAGE	= SystemUtils.getProperty(SystemUtils.USER_LANGUAGE_PROPERTY);

	private static final Charset	CHARSET			= Charset.forName(LocaleUtils.LOCALE_CHARSET);

	private static final Locale		LOCALE			= new Locale(LocaleUtils.LOCALE_LANGUAGE, LocaleUtils.LOCALE_COUNTRY);

	private LocaleUtils() {
		//
	}

	public static Charset getCharset() {
		return LocaleUtils.CHARSET;
	}

	public static Locale getLocale() {
		return LocaleUtils.LOCALE;
	}

}
