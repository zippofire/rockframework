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
package br.net.woodstock.rockframework.util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import br.net.woodstock.rockframework.utils.ConditionUtils;


public class StringFormater extends Format {

	private static final long	serialVersionUID	= 8381540260159406826L;

	public static final char	DEFAULT_CHARACTER	= '#';

	private final String		pattern;

	private final char			character;

	private final char[]		patternChars;

	public StringFormater(final String pattern) {
		this(pattern, StringFormater.DEFAULT_CHARACTER);
	}

	public StringFormater(final String pattern, final char character) {
		super();
		Assert.notEmpty(pattern, "pattern");
		this.pattern = pattern;
		this.character = character;
		this.patternChars = this.pattern.toCharArray();
	}

	public String format(final String value) {
		StringBuffer buffer = new StringBuffer();
		FieldPosition fieldPosition = DontCareFieldPosition.getInstance();
		return this.format(value, buffer, fieldPosition).toString();
	}

	@Override
	public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
		if (obj != null) {
			int index = 0;
			String str = obj.toString();
			char[] strChars = str.toCharArray();

			for (char c : this.patternChars) {
				if (c != this.character) {
					toAppendTo.append(c);
				} else {
					toAppendTo.append(strChars[index++]);
				}
			}
		}
		return toAppendTo;
	}

	public String parse(final String source) {
		return (String) this.parseObject(source, DontCareParsePosition.getInstance());
	}

	@Override
	public Object parseObject(final String source, final ParsePosition pos) {
		if (ConditionUtils.isNotEmpty(source)) {
			StringBuilder s = new StringBuilder();

			char[] sourceChars = source.toCharArray();

			for (int i = 0; i < this.patternChars.length; i++) {
				if (this.patternChars[i] == this.character) {
					s.append(sourceChars[i]);
				}
			}

			return s.toString();
		}
		return null;
	}

}
