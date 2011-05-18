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

public abstract class ComparatorUtils {

	public static final int	COMPARE_TO_BEFORE	= -1;

	public static final int	COMPARE_TO_EQUALS	= 0;

	public static final int	COMPARE_TO_AFTER	= 1;

	private ComparatorUtils() {
		//
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Comparable> T max(final T... array) {
		return ComparatorUtils.compare(ComparatorUtils.COMPARE_TO_AFTER, array);
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Comparable> T min(final T... array) {
		return ComparatorUtils.compare(ComparatorUtils.COMPARE_TO_BEFORE, array);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends Comparable> T compare(final int mode, final T... array) {
		T obj = null;
		for (T t : array) {
			if (t == null) {
				continue;
			}
			if (obj == null) {
				obj = t;
			} else if (t.compareTo(obj) == mode) {
				obj = t;
			}
		}
		return obj;
	}

}
