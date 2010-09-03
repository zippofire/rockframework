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

public abstract class HexUtils {

	private static final char	PADDING_CHAR	= '0';

	private static final int	BIT_COMPARATOR	= 0xFF;

	private HexUtils() {
		//
	}

	public static byte[] toHex(final byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			String s = Integer.toHexString(HexUtils.BIT_COMPARATOR & b);
			if (s.length() == 1) {
				builder.append(HexUtils.PADDING_CHAR);
			}
			builder.append(s);
		}
		return builder.toString().getBytes();
	}

	public static String toHexString(final byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			String s = Integer.toHexString(HexUtils.BIT_COMPARATOR & b);
			if (s.length() == 1) {
				builder.append(HexUtils.PADDING_CHAR);
			}
			builder.append(s);
		}
		return builder.toString();
	}

}
