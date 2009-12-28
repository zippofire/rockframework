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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.Normalizer;
import java.util.Map;
import java.util.Map.Entry;

public abstract class StringUtils {

	public static final String	BLANK				= "";

	public static final char	DEFAULT_FORMAT_CHAR	= '#';

	private static final String	ACCENT_PATTERN		= "[^\\p{ASCII}]";

	private static final String	LOWERCASE_LETTERS	= "abcdefghijklmnopkrstuvwxyz";

	private static final String	DIGITS				= "0123456789";

	private static final String	UPERCASE_LETTERS	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final String	LETTERS_DIGITS		= StringUtils.UPERCASE_LETTERS + StringUtils.LOWERCASE_LETTERS + StringUtils.DIGITS;

	private StringUtils() {
		//
	}

	public static String camelCase(final String s) {
		if (s == null) {
			return null;
		}
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(Character.toLowerCase(s.charAt(0)));
		builder.append(s.substring(1));
		return builder.toString();
	}

	public static String camelCase(final String s, final char separator) {
		if (s == null) {
			return null;
		}
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		StringBuilder builder = new StringBuilder();
		String[] array = s.split(Character.toString(separator));
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				builder.append(Character.toLowerCase(array[i].charAt(0)));
			} else {
				builder.append(Character.toUpperCase(array[i].charAt(0)));
			}
			builder.append(array[i].substring(1));
		}
		return builder.toString();
	}

	public static String camelCase(final String[] array) {
		if (array == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				builder.append(Character.toLowerCase(array[i].charAt(0)));
			} else {
				builder.append(Character.toUpperCase(array[i].charAt(0)));
			}
			builder.append(array[i].substring(1));
		}
		return builder.toString();
	}

	public static String capitalize(final String s) {
		if (s == null) {
			return null;
		}
		StringBuilder b = new StringBuilder();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			boolean capitalize = false;
			if (i == 0) {
				capitalize = true;
			} else if ((i > 0) && (chars[i - 1] == ' ')) {
				capitalize = true;
			}
			if ((capitalize) && (Character.isLetter(chars[i]))) {
				b.append(Character.toUpperCase(chars[i]));
			} else if (Character.isLetter(chars[i])) {
				b.append(Character.toLowerCase(chars[i]));
			} else {
				b.append(chars[i]);
			}
		}
		return b.toString();
	}

	public static String convertCharset(final Charset from, final String text) throws CharacterCodingException {
		return StringUtils.convertCharset(from, Charset.defaultCharset(), text);
	}

	public static String convertCharset(final Charset from, final Charset to, final String text) throws CharacterCodingException {
		CharsetDecoder decoderFrom = from.newDecoder();
		CharsetEncoder encoderTo = to.newEncoder();

		CharBuffer charBufferFrom = decoderFrom.decode(ByteBuffer.wrap(text.getBytes()));
		String tmp = charBufferFrom.toString();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tmp.length(); i++) {
			char c = tmp.charAt(i);
			if (encoderTo.canEncode(c)) {
				builder.append(c);
			}
		}

		return builder.toString();
	}

	public static String format(final String format, final String value) {
		return StringUtils.format(format, value, StringUtils.DEFAULT_FORMAT_CHAR);
	}

	public static String format(final String format, final String value, final char c) {
		if (StringUtils.isEmpty(format)) {
			return null;
		}

		if (StringUtils.isEmpty(value)) {
			return null;
		}

		int index = 0;

		StringBuilder s = new StringBuilder();

		char[] charsFormat = format.toCharArray();
		char[] charsValue = value.toCharArray();

		for (char element : charsFormat) {
			if (element != c) {
				s.append(element);
			} else {
				s.append(charsValue[index++]);
			}
		}

		return s.toString();
	}

	public static String getString(final String s) {
		return StringUtils.getString(s, StringUtils.BLANK);
	}

	public static String getString(final String s, final String r) {
		if (StringUtils.isEmpty(s)) {
			return r;
		}
		return s;
	}

	public static boolean hasOnlyDigit(final String s) {
		if (StringUtils.isEmpty(s)) {
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
		if (StringUtils.isEmpty(s)) {
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
		if (StringUtils.isEmpty(s)) {
			return false;
		}
		for (char c : s.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(final String s) {
		return ((s == null) || s.trim().equals(StringUtils.BLANK));
	}

	public static String lpad(final double d, final int size, final char pad) {
		return StringUtils.lpad(Double.toString(d), size, pad);
	}

	public static String lpad(final long l, final int size, final char pad) {
		return StringUtils.lpad(Long.toString(l), size, pad);
	}

	public static String lpad(final String s, final int size, final char pad) {
		StringBuilder builder = new StringBuilder();
		while (builder.length() + s.length() < size) {
			builder.append(pad);
		}
		builder.append(s);
		return builder.toString();
	}

	public static String random(final int size) {
		StringBuilder builder = new StringBuilder();
		int max = StringUtils.LETTERS_DIGITS.length();
		for (int i = 0; i < size; i++) {
			int index = NumberUtils.random(max);
			builder.append(StringUtils.LETTERS_DIGITS.charAt(index));
		}
		return builder.toString();
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

	public static String rpad(final double d, final int size, final char pad) {
		return StringUtils.rpad(Double.toString(d), size, pad);
	}

	public static String rpad(final long l, final int size, final char pad) {
		return StringUtils.rpad(Long.toString(l), size, pad);
	}

	public static String rpad(final String s, final int size, final char pad) {
		StringBuilder builder = new StringBuilder(s);
		while (builder.length() < size) {
			builder.append(pad);
		}
		return builder.toString();
	}

	public static String toString(final Object o) {
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	public static String[] toStringArray(final Object[] o) {
		int len = o.length;
		String[] s = new String[len];
		for (int i = 0; i < len; i++) {
			s[i] = o[i].toString();
		}
		return s;
	}

	public static String unformat(final String format, final String value) {
		return StringUtils.unformat(format, value, StringUtils.DEFAULT_FORMAT_CHAR);
	}

	public static String unformat(final String format, final String value, final char c) {
		if (StringUtils.isEmpty(format)) {
			return null;
		}

		if (StringUtils.isEmpty(value)) {
			return null;
		}

		StringBuilder s = new StringBuilder();

		char[] charsFormat = format.toCharArray();
		char[] charsValue = value.toCharArray();

		for (int i = 0; i < charsFormat.length; i++) {
			if (charsFormat[i] == c) {
				s.append(charsValue[i]);
			}
		}

		return s.toString();
	}

	public static String normalize(final String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll(StringUtils.ACCENT_PATTERN, StringUtils.BLANK);
	}
}
