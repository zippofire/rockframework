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

public class CamelCaseTransformer implements Transformer {

	private static final char	DEFAULT_SEPARATOR	= ' ';

	private char				separator;

	public CamelCaseTransformer() {
		this(CamelCaseTransformer.DEFAULT_SEPARATOR);
	}

	public CamelCaseTransformer(final char separator) {
		super();
		this.separator = separator;
	}

	@Override
	public String transform(final String str) {
		if (str == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		String[] array = str.split(Character.toString(this.separator));
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
}
