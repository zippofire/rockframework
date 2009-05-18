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

import java.text.NumberFormat;
import java.util.Locale;

public abstract class FormatUtils {

	public static Locale	BRAZIL	= new Locale("pt", "BR");

	private static Locale	locale	= Locale.getDefault();

	private FormatUtils() {
		//
	}

	public static String formatCurrency(double d) {
		NumberFormat f = NumberFormat.getCurrencyInstance(FormatUtils.locale);
		return f.format(d);
	}

	public static String formatCurrency(long l) {
		NumberFormat f = NumberFormat.getCurrencyInstance(FormatUtils.locale);
		return f.format(l);
	}

	public static String formatInteger(double d) {
		NumberFormat f = NumberFormat.getIntegerInstance(FormatUtils.locale);
		return f.format(d);
	}

	public static String formatInteger(long l) {
		NumberFormat f = NumberFormat.getIntegerInstance(FormatUtils.locale);
		return f.format(l);
	}

	public static String formatPercent(double d) {
		NumberFormat f = NumberFormat.getPercentInstance(FormatUtils.locale);
		return f.format(d);
	}

	public static String formatPercent(long l) {
		NumberFormat f = NumberFormat.getPercentInstance(FormatUtils.locale);
		return f.format(l);
	}

	public static Locale getLocale() {
		return FormatUtils.locale;
	}

	public static Locale getLocale(String country) {
		for (Locale l : Locale.getAvailableLocales()) {
			if (l.getISO3Country().equals(country)) {
				return l;
			}
		}
		return FormatUtils.locale;
	}

	public static Locale getLocale(String country, String language) {
		for (Locale l : Locale.getAvailableLocales()) {
			if ((l.getISO3Country().equals(country)) && (l.getISO3Language().equals(language))) {
				return l;
			}
		}
		return FormatUtils.locale;
	}

	public static void setLocale(Locale locale) {
		FormatUtils.locale = locale;
	}

	public static void setLocale(String country) {
		FormatUtils.locale = FormatUtils.getLocale(country);
	}

	public static void setLocale(String country, String language) {
		FormatUtils.locale = FormatUtils.getLocale(country, language);
	}

}
