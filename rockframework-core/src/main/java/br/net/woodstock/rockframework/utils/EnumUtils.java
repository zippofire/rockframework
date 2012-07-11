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

import br.net.woodstock.rockframework.util.Assert;

public abstract class EnumUtils {

	private EnumUtils() {
		//
	}

	@SuppressWarnings("rawtypes")
	public static String getName(final Enum e) {
		return EnumUtils.getName(e, null);
	}

	@SuppressWarnings("rawtypes")
	public static String getName(final Enum e, final String defaultName) {
		if (e != null) {
			return e.name();
		}
		return defaultName;
	}

	@SuppressWarnings("rawtypes")
	public static int getOrdinal(final Enum e) {
		return EnumUtils.getOrdinal(e, -1);
	}

	@SuppressWarnings("rawtypes")
	public static int getOrdinal(final Enum e, final int defaultOrdinal) {
		if (e != null) {
			return e.ordinal();
		}
		return defaultOrdinal;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T extends Enum> T getEnumByName(final Class<T> clazz, final String name) {
		Assert.notNull(clazz, "clazz");
		if (clazz.isEnum()) {
			if (ConditionUtils.isNotEmpty(name)) {
				T[] array = clazz.getEnumConstants();
				for (T t : array) {
					if (t.name().equals(name)) {
						return t;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T extends Enum> T getEnumByOrdinal(final Class<T> clazz, final int ordinal) {
		Assert.notNull(clazz, "clazz");
		if (clazz.isEnum()) {
			T[] array = clazz.getEnumConstants();
			if (array.length > ordinal) {
				return array[ordinal];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T extends Enum> T[] getEnumValues(final Class<T> clazz) {
		Assert.notNull(clazz, "clazz");
		if (clazz.isEnum()) {
			T[] array = clazz.getEnumConstants();
			return array;
		}
		return null;
	}

}
