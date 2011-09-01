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

import br.net.woodstock.rockframework.utils.ConditionUtils;

public abstract class PrimitiveUtils {

	private PrimitiveUtils() {
		//
	}

	public static Boolean toBoolean(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Boolean.valueOf(value);
		}
		return null;
	}

	public static boolean toBoolean(final String value, final boolean defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Boolean.parseBoolean(value);
		}
		return defaultValue;
	}

	public static Byte toByte(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Byte.valueOf(value);
		}
		return null;
	}

	public static byte toByte(final String value, final byte defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Byte.parseByte(value);
		}
		return defaultValue;
	}

	public static Character toCharacter(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Character.valueOf(value.charAt(0));
		}
		return null;
	}

	public static char toCharacter(final String value, final char defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return value.charAt(0);
		}
		return defaultValue;
	}

	public static Float toFloat(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Float.valueOf(value);
		}
		return null;
	}

	public static float toFloat(final String value, final float defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Float.parseFloat(value);
		}
		return defaultValue;
	}

	public static Double toDouble(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Double.valueOf(value);
		}
		return null;
	}

	public static double toDouble(final String value, final double defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Double.parseDouble(value);
		}
		return defaultValue;
	}

	public static Integer toInteger(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Integer.valueOf(value);
		}
		return null;
	}

	public static int toInteger(final String value, final int defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Integer.parseInt(value);
		}
		return defaultValue;
	}

	public static Short toShort(final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Short.valueOf(value);
		}
		return null;
	}

	public static short toShort(final String value, final short defaultValue) {
		if (ConditionUtils.isNotEmpty(value)) {
			return Short.parseShort(value);
		}
		return defaultValue;
	}

}
