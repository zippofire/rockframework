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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;

import br.net.woodstock.rockframework.config.CoreConfig;

public abstract class NumberUtils {

	private static final String	DECIMAL_FORMAT_PROPERTY	= "format.decimal";

	private static final String	INTEGER_FORMAT_PROPERTY	= "format.integer";

	private static final String	DECIMAL_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(NumberUtils.DECIMAL_FORMAT_PROPERTY);

	private static final String	INTEGER_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(NumberUtils.INTEGER_FORMAT_PROPERTY);

	private static final Random	RANDOM					= new Random();

	public static String format(final BigDecimal value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.DECIMAL_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final BigInteger value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Byte value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final double value) {
		NumberFormat nf = new DecimalFormat(NumberUtils.DECIMAL_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Double value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.DECIMAL_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Float value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.DECIMAL_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Integer value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final long value) {
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Long value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Number value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.DECIMAL_FORMAT_PATTERN);
		return nf.format(value);
	}

	public static String format(final Short value) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(NumberUtils.INTEGER_FORMAT_PATTERN);
		return nf.format(value);
	}

	// Pattern
	public static String format(final double value, final String pattern) {
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(value);
	}

	public static String format(final long value, final String pattern) {
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(value);
	}

	public static String format(final Number value, final String pattern) {
		if (value == null) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(value);
	}

	// Parse
	public static Number parse(final String value, final String pattern) throws ParseException {
		if (ConditionUtils.isEmpty(value)) {
			return null;
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.parse(value);
	}

	// Random
	public static int random() {
		return NumberUtils.RANDOM.nextInt();
	}

	public static int random(final int max) {
		return NumberUtils.RANDOM.nextInt(max);
	}

	public static double root(final double value, final double root) {
		if (root == 2) {
			return Math.sqrt(value);
		}
		if (root == 3) {
			return Math.cbrt(value);
		}
		return Math.pow(value, 1.0 / root);
	}

	private NumberUtils() {
		//
	}

	public static int unsignedByteToInt(final byte b) {
		return b & 0xff;
	}

	public static byte[] intToByteArray(final int i) {
		byte[] array = new byte[4];
		array[0] = (byte) (i >>> 24);
		array[1] = (byte) (i >>> 16);
		array[2] = (byte) (i >>> 8);
		array[3] = (byte) (i);
		return array;
	}

	public static int byteArrayToInt(final byte[] array) {
		int i = 0;
		i += (array[0] << 24);
		i += (NumberUtils.unsignedByteToInt(array[1]) << 16);
		i += (NumberUtils.unsignedByteToInt(array[2]) << 8);
		i += (NumberUtils.unsignedByteToInt(array[3]));
		return i;
	}

}
