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
package br.net.woodstock.rockframework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.net.woodstock.rockframework.text.impl.RandomGenerator;

public abstract class StringUtils {

	private static final Map<Character, String>	HTML_REPLACEMENT;

	static {
		HTML_REPLACEMENT = new HashMap<Character, String>();
		StringUtils.HTML_REPLACEMENT.put(Character.valueOf('<'), "&lt;");
		StringUtils.HTML_REPLACEMENT.put(Character.valueOf('>'), "&gt;");
		StringUtils.HTML_REPLACEMENT.put(Character.valueOf('&'), "&amp;");
		StringUtils.HTML_REPLACEMENT.put(Character.valueOf('\''), "&#039;");
		StringUtils.HTML_REPLACEMENT.put(Character.valueOf('"'), "&quot;");
	}

	private StringUtils() {
		//
	}

	public static String escapeHTML(final String s) {
		if (ConditionUtils.isEmpty(s)) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (char c : s.toCharArray()) {
			Character wrappedChar = Character.valueOf(c);
			if (StringUtils.HTML_REPLACEMENT.containsKey(wrappedChar)) {
				builder.append(StringUtils.HTML_REPLACEMENT.get(wrappedChar));
			} else {
				builder.append(c);
			}
		}

		return builder.toString();
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
