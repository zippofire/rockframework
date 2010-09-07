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

public abstract class CharacterUtils {

	private static final int	MAX_ASCII_CHAR		= 127;

	private static final int	MAX_ISO88591_CHAR	= 255;

	private CharacterUtils() {
		//
	}

	public static boolean isASCII(char c) {
		int i = c;
		if (i <= CharacterUtils.MAX_ASCII_CHAR) {
			return true;
		}
		return false;
	}

	public static boolean isISO8859_1(char c) {
		int i = c;
		if (i <= CharacterUtils.MAX_ISO88591_CHAR) {
			return true;
		}
		return false;
	}

}
