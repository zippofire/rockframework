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

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.util.DecimalFormatFactory;

public abstract class NumberUtils {

	private static final String			DECIMAL_FORMAT_PROPERTY	= "format.decimal";

	private static final String			INTEGER_FORMAT_PROPERTY	= "format.integer";

	private static final String			DECIMAL_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(NumberUtils.DECIMAL_FORMAT_PROPERTY);

	private static final String			INTEGER_FORMAT_PATTERN	= CoreConfig.getInstance().getValue(NumberUtils.INTEGER_FORMAT_PROPERTY);

	private static final DecimalFormat	DECIMAL_FORMAT			= DecimalFormatFactory.getInstance().getFormat(NumberUtils.DECIMAL_FORMAT_PATTERN, LocaleUtils.getLocale());

	private static final DecimalFormat	INTEGER_FORMAT			= DecimalFormatFactory.getInstance().getFormat(NumberUtils.INTEGER_FORMAT_PATTERN, LocaleUtils.getLocale());

	private static final Random			RANDOM					= new Random();

	public static String format(final BigDecimal value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(final BigInteger value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(final Byte value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(final double value) {
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(final Double value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(final Float value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(final Integer value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(final long value) {
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(final Long value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(final Number value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(final Short value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	// Pattern
	public static String format(final double value, final String pattern) {
		DecimalFormat df = DecimalFormatFactory.getInstance().getFormat(pattern);
		return df.format(value);
	}

	public static String format(final long value, final String pattern) {
		DecimalFormat df = DecimalFormatFactory.getInstance().getFormat(pattern);
		return df.format(value);
	}

	public static String format(final Number value, final String pattern) {
		if (value == null) {
			return null;
		}
		DecimalFormat df = DecimalFormatFactory.getInstance().getFormat(pattern);
		return df.format(value);
	}

	// Parse
	public static Number parse(final String value, final String pattern) throws ParseException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		DecimalFormat df = DecimalFormatFactory.getInstance().getFormat(pattern);
		return df.parse(value);
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

}
