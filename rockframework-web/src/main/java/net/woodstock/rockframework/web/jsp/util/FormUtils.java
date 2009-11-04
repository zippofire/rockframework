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
package net.woodstock.rockframework.web.jsp.util;

import java.text.ParseException;
import java.util.Date;

import net.woodstock.rockframework.utils.DateUtils;
import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class FormUtils {

	private FormUtils() {
		//
	}

	// Byte
	public static Byte toByte(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Byte(NumberUtils.parse(value).byteValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Byte toByte(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Byte(NumberUtils.parse(value, format).byteValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Byte value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Byte value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// Date
	public static Date toDate(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return DateUtils.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date toDate(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return DateUtils.parse(value, format);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Date date) {
		if (date == null) {
			return null;
		}
		return DateUtils.format(date);
	}

	public static String toString(Date date, String format) {
		if (date == null) {
			return null;
		}
		return DateUtils.format(date, format);
	}

	// Double
	public static Double toDouble(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Double(NumberUtils.parse(value).doubleValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Double toDouble(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Double(NumberUtils.parse(value, format).doubleValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Double value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Double value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// Float
	public static Float toFloat(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Float(NumberUtils.parse(value).floatValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Float toFloat(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Float(NumberUtils.parse(value, format).floatValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Float value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Float value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// Integer
	public static Integer toInteger(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Integer(NumberUtils.parse(value).intValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Integer toInteger(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Integer(NumberUtils.parse(value, format).intValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Integer value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Integer value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// Long
	public static Long toLong(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Long(NumberUtils.parse(value).longValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Long toLong(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Long(NumberUtils.parse(value, format).longValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Long value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Long value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// Short
	public static Short toShort(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Short(NumberUtils.parse(value).shortValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Short toShort(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return new Short(NumberUtils.parse(value, format).shortValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Short value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value);
	}

	public static String toString(Short value, String format) {
		if (value == null) {
			return null;
		}
		return NumberUtils.format(value, format);
	}

	// String
	public static String fromText(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return value;
	}

	public static String fromText(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return StringUtils.unformat(format, value);
	}

	public static String toText(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return value;
	}

	public static String toText(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return StringUtils.format(format, value);
	}

}
