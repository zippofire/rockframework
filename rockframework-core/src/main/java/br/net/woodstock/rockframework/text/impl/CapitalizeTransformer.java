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
package br.net.woodstock.rockframework.text.impl;

import br.net.woodstock.rockframework.text.Transformer;

public final class CapitalizeTransformer implements Transformer {

	private static final char	SPACE		= ' ';

	private static Transformer	instance	= new CapitalizeTransformer();

	private CapitalizeTransformer() {
		super();
	}

	@Override
	public String transform(final String str) {
		if (str == null) {
			return null;
		}
		StringBuilder b = new StringBuilder();
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			boolean capitalize = false;
			if (i == 0) {
				capitalize = true;
			} else if ((i > 0) && (chars[i - 1] == CapitalizeTransformer.SPACE)) {
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

	public static Transformer getInstance() {
		return CapitalizeTransformer.instance;
	}
}
