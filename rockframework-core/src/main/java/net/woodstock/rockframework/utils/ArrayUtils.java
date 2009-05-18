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

import java.lang.reflect.Array;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ArrayUtils {

	private ArrayUtils() {
		//
	}

	public static Class<?> getArrayType(Object array) throws ClassNotFoundException {
		if (array == null) {
			return null;
		}
		String name = ArrayUtils.getArrayTypeName(array);
		if (ClassUtils.isPrimitiveClass(name)) {
			return ClassUtils.getPrimitiveClass(name);
		}
		return Class.forName(ArrayUtils.getArrayTypeName(array));
	}

	public static String getArrayTypeName(Object array) {
		if (array == null) {
			return null;
		}
		String className = array.getClass().getCanonicalName();
		if (className.indexOf('[') != -1) {
			className = className.substring(0, className.indexOf('['));
		}
		return className;
	}

	public static List<Object> asList(Object array) {
		if (array == null) {
			return null;
		}
		List<Object> list = new LinkedList<Object>();
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			list.add(o);
		}
		return list;
	}

	public static Set<Object> asSet(Object array) {
		if (array == null) {
			return null;
		}
		Set<Object> set = new LinkedHashSet<Object>();
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			set.add(o);
		}
		return set;
	}

	public static String toString(Object array) {
		if (array == null) {
			return null;
		}

		boolean first = true;
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (int i = 0; i < Array.getLength(array); i++) {
			Object o = Array.get(array, i);
			if (!first) {
				builder.append(",");
			}
			builder.append(o.toString());
			if (first) {
				first = false;
			}
		}
		builder.append("]");

		return builder.toString();
	}

	public static String toString(Object[] array) {
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
			builder.append(o.toString());
			if (first) {
				first = false;
			}
		}
		builder.append("]");

		return builder.toString();
	}

}
