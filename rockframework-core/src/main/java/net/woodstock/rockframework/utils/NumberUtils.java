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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Random;

public abstract class NumberUtils {

	private static DecimalFormat	decimalFormat;

	public static String format(BigDecimal value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value);
	}

	public static String format(BigDecimal value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(BigInteger value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value);
	}

	public static String format(BigInteger value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(Byte value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.byteValue());
	}

	public static String format(Byte value, String format) {
		return new DecimalFormat(format).format(value.byteValue());
	}

	public static String format(double value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value);
	}

	public static String format(double value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(Double value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.doubleValue());
	}

	public static String format(Double value, String format) {
		return new DecimalFormat(format).format(value.doubleValue());
	}

	public static String format(Float value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.floatValue());
	}

	public static String format(Float value, String format) {
		return new DecimalFormat(format).format(value.floatValue());
	}

	public static String format(Integer value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.intValue());
	}

	public static String format(Integer value, String format) {
		return new DecimalFormat(format).format(value.intValue());
	}

	public static String format(long value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value);
	}

	public static String format(long value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(Long value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.longValue());
	}

	public static String format(Long value, String format) {
		return new DecimalFormat(format).format(value.longValue());
	}

	public static String format(Number value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.doubleValue());
	}

	public static String format(Number value, String format) {
		return new DecimalFormat(format).format(value.doubleValue());
	}

	public static String format(Short value) {
		if (NumberUtils.decimalFormat == null) {
			throw new IllegalStateException("Format must be defined");
		}
		return NumberUtils.decimalFormat.format(value.shortValue());
	}

	public static String format(Short value, String format) {
		return new DecimalFormat(format).format(value.shortValue());
	}

	public static Number parse(String value) throws ParseException {
		if (value == null) {
			return null;
		}
		return NumberUtils.decimalFormat.parse(value);
	}

	public static Number parse(String value, String format) throws ParseException {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).parse(value);
	}

	public static int random() {
		return new Random().nextInt();
	}

	public static int random(int max) {
		return new Random().nextInt(max);
	}

	public static void setDecimalFormat(String format) {
		NumberUtils.decimalFormat = new DecimalFormat(format);
	}

	private NumberUtils() {
		//
	}

}
