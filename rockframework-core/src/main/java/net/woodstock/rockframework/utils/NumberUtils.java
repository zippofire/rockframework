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

public abstract class NumberUtils {

	private static final String			DECIMAL_FORMAT_PROPERTY	= "decimal.format";

	private static final String			INTEGER_FORMAT_PROPERTY	= "integer.format";

	private static final DecimalFormat	DECIMAL_FORMAT			= new DecimalFormat(CoreConfig.getInstance().getValue(NumberUtils.DECIMAL_FORMAT_PROPERTY));

	private static final DecimalFormat	INTEGER_FORMAT			= new DecimalFormat(CoreConfig.getInstance().getValue(NumberUtils.INTEGER_FORMAT_PROPERTY));

	private static final Random			random					= new Random();

	public static String format(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(BigDecimal value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value);
	}

	public static String format(BigInteger value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(BigInteger value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value);
	}

	public static String format(Byte value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value.byteValue());
	}

	public static String format(Byte value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.byteValue());
	}

	public static String format(double value) {
		return NumberUtils.DECIMAL_FORMAT.format(value);
	}

	public static String format(double value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(Double value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value.doubleValue());
	}

	public static String format(Double value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.doubleValue());
	}

	public static String format(Float value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value.floatValue());
	}

	public static String format(Float value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.floatValue());
	}

	public static String format(Integer value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value.intValue());
	}

	public static String format(Integer value, String format) {
		return new DecimalFormat(format).format(value.intValue());
	}

	public static String format(long value) {
		return NumberUtils.INTEGER_FORMAT.format(value);
	}

	public static String format(long value, String format) {
		return new DecimalFormat(format).format(value);
	}

	public static String format(Long value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value.longValue());
	}

	public static String format(Long value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.longValue());
	}

	public static String format(Number value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.format(value.doubleValue());
	}

	public static String format(Number value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.doubleValue());
	}

	public static String format(Short value) {
		if (value == null) {
			return null;
		}
		return NumberUtils.INTEGER_FORMAT.format(value.shortValue());
	}

	public static String format(Short value, String format) {
		if (value == null) {
			return null;
		}
		return new DecimalFormat(format).format(value.shortValue());
	}

	public static Number parse(String value) throws ParseException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return NumberUtils.DECIMAL_FORMAT.parse(value);
	}

	public static Number parse(String value, String format) throws ParseException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new DecimalFormat(format).parse(value);
	}

	public static int random() {
		return NumberUtils.random.nextInt();
	}

	public static int random(int max) {
		return NumberUtils.random.nextInt(max);
	}

	private NumberUtils() {
		//
	}

}
