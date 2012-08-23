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

import java.lang.reflect.Array;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.net.woodstock.rockframework.util.Assert;

public final class ArrayUtils {

	private ArrayUtils() {
		//
	}

	public static Class<?> getArrayType(final Object array) {
		if (array == null) {
			return null;
		}

		if (!ArrayUtils.isArray(array)) {
			return null;
		}

		Class<?> clazz = array.getClass().getComponentType();

		return clazz;
	}

	public static boolean isArray(final Object array) {
		if (array == null) {
			return false;
		}

		if (array.getClass().isArray()) {
			return true;
		}
		return false;
	}

	public static List<Object> toList(final Object array) {
		if (array == null) {
			return null;
		}

		Assert.isArray(array, "array");

		List<Object> list = new LinkedList<Object>();
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			list.add(o);
		}
		return list;
	}

	public static <T> List<T> toList(final T[] array) {
		if (array == null) {
			return null;
		}
		List<T> list = new LinkedList<T>();
		for (T t : array) {
			list.add(t);
		}
		return list;
	}

	public static Set<Object> toSet(final Object array) {
		if (array == null) {
			return null;
		}

		Assert.isArray(array, "array");

		Set<Object> set = new LinkedHashSet<Object>();
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			set.add(o);
		}
		return set;
	}

	public static <T> Set<T> toSet(final T[] array) {
		if (array == null) {
			return null;
		}
		Set<T> list = new LinkedHashSet<T>();
		for (T t : array) {
			list.add(t);
		}
		return list;
	}

	public static String toString(final Object array) {
		if (array == null) {
			return null;
		}

		Assert.isArray(array, "array");

		boolean first = true;
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			if (!first) {
				builder.append(",");
			}
			builder.append(o);
			if (first) {
				first = false;
			}
		}
		builder.append("]");

		return builder.toString();
	}

	public static String toString(final Object[] array) {
		if (array == null) {
			return null;
		}

		boolean first = true;
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (Object o : array) {
			if (!first) {
				builder.append(",");
			}
			builder.append(o);
			if (first) {
				first = false;
			}
		}
		builder.append("]");

		return builder.toString();
	}

	public static String[] toStringArray(final Object array) {
		if (array == null) {
			return null;
		}

		Assert.isArray(array, "array");

		int len = Array.getLength(array);
		String[] s = new String[len];
		for (int i = 0; i < len; i++) {
			Object o = Array.get(array, i);
			if (o != null) {
				s[i] = o.toString();
			} else {
				s[i] = null;
			}
		}
		return s;
	}

	public static String[] toStringArray(final Object[] array) {
		int len = array.length;
		String[] s = new String[len];
		for (int i = 0; i < len; i++) {
			Object o = s[i];
			if (o != null) {
				s[i] = o.toString();
			} else {
				s[i] = null;
			}
		}
		return s;
	}

}
