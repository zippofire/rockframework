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
package net.woodstock.rockframework.util;

public class LPadTransform implements StringTransform {

	private int		size;

	private char	pad;

	public LPadTransform(final int size, final char pad) {
		super();
		this.size = size;
		this.pad = pad;
	}

	@Override
	public String transform(final String str) {
		if (str == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		while (builder.length() + str.length() < this.size) {
			builder.append(this.pad);
		}
		builder.append(str);
		return builder.toString();
	}
}
