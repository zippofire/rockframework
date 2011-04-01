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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.util.RandomGenerator;

public abstract class StringUtils {

	private StringUtils() {
		//
	}

	public static boolean hasOnlyDigit(final String s) {
		if (ConditionUtils.isEmpty(s)) {
			return false;
		}
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasOnlyLetter(final String s) {
		if (ConditionUtils.isEmpty(s)) {
			return false;
		}
		for (char c : s.toCharArray()) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasOnlyLetterOrDigit(final String s) {
		if (ConditionUtils.isEmpty(s)) {
			return false;
		}
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public static String random(final int size) {
		RandomGenerator randomString = new RandomGenerator(size);
		return randomString.generate();
	}

	public static String replace(final InputStream input, final Map<String, String> replaces) throws IOException {
		return StringUtils.replace(new InputStreamReader(input), replaces);
	}

	public static String replace(final Reader reader, final Map<String, String> replaces) throws IOException {
		BufferedReader r = new BufferedReader(reader);
		StringBuilder b = new StringBuilder();
		String tmp = null;
		while ((tmp = r.readLine()) != null) {
			b.append(tmp);
			b.append(SystemUtils.getProperty(SystemUtils.LINE_SEPARATOR_PROPERTY));
		}
		return StringUtils.replace(b.toString(), replaces);
	}

	public static String replace(final String s, final Map<String, String> replaces) {
		String ss = s;
		if (replaces.size() > 0) {
			for (Entry<String, String> entry : replaces.entrySet()) {
				ss = ss.replaceAll(entry.getKey(), entry.getValue());
			}
		}
		return ss;
	}

	public static String reverse(final String s) {
		if (s == null) {
			return null;
		}
		return new StringBuilder(s).reverse().toString();
	}
}
