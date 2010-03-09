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

import java.util.Locale;

import net.woodstock.rockframework.config.CoreConfig;

public abstract class LocaleUtils {

	private static final String	LOCALE_COUNTRY_PROPERTY		= "locale.country";

	private static final String	LOCALE_LANGUAGE_PROPERTY	= "locale.language";

	private static final String	LOCALE_COUNTRY				= CoreConfig.getInstance().getValue(LocaleUtils.LOCALE_COUNTRY_PROPERTY);

	private static final String	LOCALE_LANGUAGE				= CoreConfig.getInstance().getValue(LocaleUtils.LOCALE_LANGUAGE_PROPERTY);

	private static final Locale	LOCALE						= new Locale(LocaleUtils.LOCALE_LANGUAGE, LocaleUtils.LOCALE_COUNTRY);

	private LocaleUtils() {
		//
	}

	public static Locale getLocale() {
		return LocaleUtils.LOCALE;
	}

}
