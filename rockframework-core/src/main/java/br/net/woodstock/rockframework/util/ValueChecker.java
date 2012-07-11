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
package br.net.woodstock.rockframework.util;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.config.CoreMessage;
import br.net.woodstock.rockframework.utils.ArrayUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.EnumUtils;

public class ValueChecker {

	private static final String		MESSAGE_BETWEEN			= "error.between";

	private static final String		MESSAGE_EQUALS			= "error.equals";

	private static final String		MESSAGE_GREATER_THAN	= "error.greaterThan";

	private static final String		MESSAGE_INSTANCE_OF		= "error.instanceOf";

	private static final String		MESSAGE_IS_ARRAY		= "error.isArray";

	private static final String		MESSAGE_IS_DIRECTORY	= "error.isDirectory";

	private static final String		MESSAGE_IS_FILE			= "error.isFile";

	private static final String		MESSAGE_LESS_THAN		= "error.lessThan";

	private static final String		MESSAGE_NOT_EMPTY		= "error.notEmpty";

	// private static final String MESSAGE_NOT_FOUND = "error.notFound";

	private static final String		MESSAGE_NOT_NULL		= "error.notNull";

	private static final String		MESSAGE_VALID_REGEX		= "error.validRegex";

	private static final String		MESSAGE_VALID_ENUM		= "error.validEnum";

	private ValueExceptionBuilder	exceptionBuilder;

	public ValueChecker(final ValueExceptionBuilder exceptionBuilder) {
		super();
		this.exceptionBuilder = exceptionBuilder;
	}

	// Between
	public void between(final double value, final double minimum, final double maximum, final String name) {
		if ((value < minimum) || (value > maximum)) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_BETWEEN, name, Double.valueOf(minimum), Double.valueOf(maximum)));
		}
	}

	public void between(final long value, final long minimum, final long maximum, final String name) {
		if ((value < minimum) || (value > maximum)) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_BETWEEN, name, Long.valueOf(minimum), Long.valueOf(maximum)));
		}
	}

	// Greater or Equals
	public void equals(final Object value, final Object other, final String name) {
		this.notNull(value, name);
		if (!value.equals(other)) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_EQUALS, name, other));
		}
	}

	// Greater or Equal
	public void greaterOrEqual(final double value, final double minimum, final String name) {
		if (value < minimum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_GREATER_THAN, name, Double.valueOf(minimum)));
		}
	}

	public void greaterOrEqual(final long value, final long minimum, final String name) {
		if (value < minimum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_GREATER_THAN, name, Long.valueOf(minimum)));
		}
	}

	// Greater
	public void greaterThan(final double value, final double minimum, final String name) {
		if (value <= minimum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_GREATER_THAN, name, Double.valueOf(minimum)));
		}
	}

	public void greaterThan(final long value, final long minimum, final String name) {
		if (value <= minimum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_GREATER_THAN, name, Long.valueOf(minimum)));
		}
	}

	// Instance
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void instanceOf(final Object value, final Class type, final String name) {
		this.notNull(value, name);
		this.notNull(type, "type");

		Class clazz = value.getClass();
		if (!type.isAssignableFrom(clazz)) {
			String typeName = type.getName();
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_INSTANCE_OF, name, typeName));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void instanceOf(final Object value, final Class[] types, final String name) {
		this.notNull(value, name);
		this.notEmpty(types, "types");

		boolean valid = false;
		Class clazz = value.getClass();

		for (Class type : types) {
			if (type.isAssignableFrom(clazz)) {
				valid = true;
				break;
			}
		}

		if (!valid) {
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			for (Class type : types) {
				if (!first) {
					builder.append(",");
				}
				builder.append(type.getName());
				if (first) {
					first = false;
				}
			}
			String typeNames = builder.toString();

			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_INSTANCE_OF, name, typeNames));
		}
	}

	// Array
	public void isArray(final Object obj, final String name) {
		this.notNull(obj, name);

		if (!obj.getClass().isArray()) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_IS_ARRAY, name));
		}
	}

	// IO
	public void isDirectory(final File file, final String name) {
		this.notNull(file, name);

		if ((file.exists()) && (!file.isDirectory())) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_IS_DIRECTORY, name));
		}
	}

	public void isFile(final File file, final String name) {
		this.notNull(file, name);

		if ((file.exists()) && (!file.isFile())) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_IS_FILE, name));
		}
	}

	// Less or equals
	public void lessOrEqual(final double value, final double maximum, final String name) {
		if (value > maximum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_LESS_THAN, name, Double.valueOf(maximum)));
		}
	}

	public void lessOrEqual(final long value, final long maximum, final String name) {
		if (value > maximum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_LESS_THAN, name, Long.valueOf(maximum)));
		}
	}

	// Less
	public void lessThan(final double value, final double maximum, final String name) {
		if (value >= maximum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_LESS_THAN, name, Double.valueOf(maximum)));
		}
	}

	public void lessThan(final long value, final long maximum, final String name) {
		if (value >= maximum) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_LESS_THAN, name, Long.valueOf(maximum)));
		}
	}

	// Not Null
	public void notNull(final Object obj, final String name) {
		if (obj == null) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_NULL, name));
		}
	}

	// Not Empty
	@SuppressWarnings("rawtypes")
	public void notEmpty(final Collection collection, final String name) {
		this.notNull(collection, name);

		if (collection.isEmpty()) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_EMPTY, name));
		}
	}

	@SuppressWarnings("rawtypes")
	public void notEmpty(final Map map, final String name) {
		this.notNull(map, name);

		if (map.size() == 0) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_EMPTY, name));
		}
	}

	public void notEmpty(final Object[] array, final String name) {
		this.notNull(array, name);

		if (array.length == 0) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_EMPTY, name));
		}
	}

	public void notEmpty(final Object array, final String name) {
		this.notNull(array, name);

		if (array.getClass().isArray()) {
			if (Array.getLength(array) == 0) {
				throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_EMPTY, name));
			}
		} else {
			CoreLog.getInstance().getLog().warning("Object is not an array. Call notNull() instead " + name + " : " + array);
		}
	}

	public void notEmpty(final String str, final String name) {
		if (ConditionUtils.isEmpty(str)) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_NOT_EMPTY, name));
		}
	}

	// Regex
	public void validRegex(final String value, final String pattern, final String name) {
		this.notEmpty(value, name);
		this.notEmpty(pattern, "pattern");

		if (!Pattern.matches(pattern, value)) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_VALID_REGEX, name));
		}
	}

	// Enum
	public <E extends Enum<?>> void validEnum(final String value, final Class<E> enumType, final String name) {
		this.notEmpty(value, name);
		this.notEmpty(enumType, name);

		E e = EnumUtils.getEnumByName(enumType, value);
		if (e == null) {
			throw this.exceptionBuilder.newException(CoreMessage.getInstance().getMessage(ValueChecker.MESSAGE_VALID_ENUM, name, ArrayUtils.toString(EnumUtils.getEnumValues(enumType))));
		}
	}

}
