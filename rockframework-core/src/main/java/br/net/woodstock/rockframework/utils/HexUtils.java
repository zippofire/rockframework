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

public abstract class HexUtils {

	private static final char	PADDING_CHAR	= '0';

	private static final int	BIT_COMPARATOR	= 0xFF;

	private HexUtils() {
		//
	}

	public static byte[] toHex(final byte[] bytes) {
		String str = HexUtils.toHexString(bytes);
		return str.getBytes();
	}

	public static byte[] fromHex(final byte[] bytes) {
		byte[] b = HexUtils.fromHexString(new String(bytes));
		return b;
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

	public static byte[] fromHexString(final String str) {
		byte[] bytes = new byte[str.length() / 2];
		char[] chars = str.toCharArray();

		int byteIndex = 0;
		int charIndex = 0;

		while (charIndex < chars.length) {
			String hex = new String(new char[] { chars[charIndex], chars[charIndex + 1] });
			Integer value = Integer.valueOf(hex, 16);

			bytes[byteIndex] = value.byteValue();

			byteIndex += 1;
			charIndex += 2;
		}

		return bytes;
	}

}
