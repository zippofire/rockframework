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

import java.util.Collection;
import java.util.Map;

public abstract class ConditionUtils {

	private ConditionUtils() {
		//
	}

	// Boolean
	public static boolean isFalseAndNotNull(final Boolean b) {
		if (b == null) {
			return false;
		}
		return !b.booleanValue();
	}

	public static boolean isTrueAndNotNull(final Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	public static boolean isFalseOrNull(final Boolean b) {
		if (b == null) {
			return true;
		}
		return !b.booleanValue();
	}

	public static boolean isTrueOrNull(final Boolean b) {
		if (b == null) {
			return true;
		}
		return b.booleanValue();
	}

	// Character
	public static boolean isDigitAndNotNull(final Character c) {
		if (c == null) {
			return false;
		}
		return Character.isDigit(c.charValue());
	}

	public static boolean isLetterAndNotNull(final Character c) {
		if (c == null) {
			return false;
		}
		return Character.isLetter(c.charValue());
	}

	public static boolean isLetterOrDigitAndNotNull(final Character c) {
		if (c == null) {
			return false;
		}
		return Character.isLetterOrDigit(c.charValue());
	}

	public static boolean isDigitOrNull(final Character c) {
		if (c == null) {
			return true;
		}
		return Character.isDigit(c.charValue());
	}

	public static boolean isLetterOrNull(final Character c) {
		if (c == null) {
			return true;
		}
		return Character.isLetter(c.charValue());
	}

	public static boolean isLetterOrDigitOrNull(final Character c) {
		if (c == null) {
			return true;
		}
		return Character.isLetterOrDigit(c.charValue());
	}

	// Number
	public static boolean isGreaterThanZeroAndNotNull(final Number n) {
		if (n == null) {
			return false;
		}
		return n.doubleValue() > 0;
	}

	public static boolean isLessThanZeroAndNotNull(final Number n) {
		if (n == null) {
			return false;
		}
		return n.doubleValue() < 0;
	}

	public static boolean isGreaterOrEqualsZeroAndNotNull(final Number n) {
		if (n == null) {
			return false;
		}
		return n.doubleValue() >= 0;
	}

	public static boolean isLessOrEqualsZeroAndNotNull(final Number n) {
		if (n == null) {
			return false;
		}
		return n.doubleValue() <= 0;
	}

	public static boolean isGreaterThanZeroOrNull(final Number n) {
		if (n == null) {
			return true;
		}
		return n.doubleValue() > 0;
	}

	public static boolean isLessThanZeroOrNull(final Number n) {
		if (n == null) {
			return true;
		}
		return n.doubleValue() < 0;
	}

	public static boolean isGreaterOrEqualsZeroOrNull(final Number n) {
		if (n == null) {
			return true;
		}
		return n.doubleValue() >= 0;
	}

	public static boolean isLessOrEqualsZeroOrNull(final Number n) {
		if (n == null) {
			return true;
		}
		return n.doubleValue() <= 0;
	}

	// Collections
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyOrNull(final Collection collection) {
		if ((collection == null) || (collection.isEmpty())) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmptyOrNull(final Map map) {
		if ((map == null) || (map.isEmpty())) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyOrNull(final Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(final Collection collection) {
		if ((collection == null) || (collection.isEmpty())) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(final Map map) {
		if ((map == null) || (map.isEmpty())) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(final Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}
		return true;
	}

	// String
	public static boolean isEmpty(final String s) {
		if (s == null) {
			return true;
		}
		if (s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(final String s) {
		if (s == null) {
			return false;
		}
		if (s.trim().length() == 0) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmptyAsString(final Object o) {
		if (o == null) {
			return false;
		}
		String str = o.toString();
		return ConditionUtils.isEmpty(str);
	}

	// Object
	public static boolean isNull(final Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(final Object o) {
		if (o == null) {
			return false;
		}
		return true;
	}

}
