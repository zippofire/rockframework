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

import java.util.Random;

public class RandomGenerator implements StringGenerator {

	private int				length;

	private RandomPattern	pattern;

	public RandomGenerator(final int length) {
		this(length, RandomPattern.LETTER_OR_DIGIT);
	}

	public RandomGenerator(final int length, final RandomPattern pattern) {
		super();
		this.length = length;
		this.pattern = pattern;
	}

	@Override
	public String generate() {
		StringBuilder builder = new StringBuilder();
		String pattern = this.pattern.getPattern();
		Random random = new Random();
		int max = pattern.length();
		for (int i = 0; i < this.length; i++) {
			int index = random.nextInt(max);
			builder.append(pattern.charAt(index));
		}
		return builder.toString();
	}

	public static enum RandomPattern {
		DIGITS("0123456789"),
		LETTER_UPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		LETTER_LOWERCASE("abcdefghijklmnopkrstuvwxyz"),
		LETTER(LETTER_UPERCASE.pattern + LETTER_LOWERCASE.pattern),
		LETTER_OR_DIGIT(LETTER.pattern + DIGITS.pattern),
		LETTER_OR_DIGIT_LOWERCASE(LETTER_LOWERCASE.pattern + DIGITS.pattern),
		LETTER_OR_DIGIT_UPERCASE(LETTER_UPERCASE.pattern + DIGITS.pattern);

		private String	pattern;

		private RandomPattern(final String pattern) {
			this.pattern = pattern;
		}

		public String getPattern() {
			return this.pattern;
		}

	}
}
