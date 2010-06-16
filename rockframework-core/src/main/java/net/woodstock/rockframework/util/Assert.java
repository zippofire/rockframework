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
package net.woodstock.rockframework.util;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class Assert {

	private static final String	MESSAGE_BETWEEN			= "error.between";

	private static final String	MESSAGE_GREATER_THAN	= "error.greaterThan";

	private static final String	MESSAGE_INSTANCE_OF		= "error.instanceOf";

	private static final String	MESSAGE_IS_ARRAY		= "error.isArray";

	private static final String	MESSAGE_IS_DIRECTORY	= "error.isDirectory";

	private static final String	MESSAGE_IS_FILE			= "error.isFile";

	private static final String	MESSAGE_LESS_THAN		= "error.lessThan";

	private static final String	MESSAGE_NOT_EMPTY		= "error.notEmpty";

	// private static final String MESSAGE_NOT_FOUND = "error.notFound";

	private static final String	MESSAGE_NOT_NULL		= "error.notNull";

	private static final String	MESSAGE_VALID_REGEX		= "error.validRegex";

	private Assert() {
		super();
	}

	// Between
	public static void between(final double value, final double minimum, final double maximum, final String name) {
		if ((value < minimum) || (value > maximum)) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_BETWEEN, name, new Double(minimum), new Double(maximum)));
		}
	}

	public static void between(final long value, final long minimum, final long maximum, final String name) {
		if ((value < minimum) || (value > maximum)) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_BETWEEN, name, new Long(minimum), new Long(maximum)));
		}
	}

	// Greater or Equal
	public static void greaterOrEqual(final double value, final double minimum, final String name) {
		if (value < minimum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Double(minimum)));
		}
	}

	public static void greaterOrEqual(final long value, final long minimum, final String name) {
		if (value < minimum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Long(minimum)));
		}
	}

	// Greater
	public static void greaterThan(final double value, final double minimum, final String name) {
		if (value <= minimum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Double(minimum)));
		}
	}

	public static void greaterThan(final long value, final long minimum, final String name) {
		if (value <= minimum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Long(minimum)));
		}
	}

	// Instance
	@SuppressWarnings("unchecked")
	public static void instanceOf(final Object value, final Class type, final String name) {
		Assert.notNull(value, name);
		Assert.notNull(type, "type");

		Class clazz = value.getClass();
		if (!type.isAssignableFrom(clazz)) {
			String typeName = type.getName();
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_INSTANCE_OF, name, typeName));
		}
	}

	@SuppressWarnings("unchecked")
	public static void instanceOf(final Object value, final Class[] types, final String name) {
		Assert.notNull(value, name);
		Assert.notEmpty(types, "types");

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

			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_INSTANCE_OF, name, typeNames));
		}
	}

	// Array
	public static void isArray(final Object obj, final String name) {
		Assert.notNull(obj, name);

		if (!obj.getClass().isArray()) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_ARRAY, name));
		}
	}

	// IO
	public static void isDirectory(final File file, final String name) {
		Assert.notNull(file, name);

		if ((file.exists()) && (!file.isDirectory())) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_DIRECTORY, name));
		}
	}

	public static void isFile(final File file, final String name) {
		Assert.notNull(file, name);

		if ((file.exists()) && (!file.isFile())) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_FILE, name));
		}
	}

	// Less or equals
	public static void lessOrEqual(final double value, final double maximum, final String name) {
		if (value > maximum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Double(maximum)));
		}
	}

	public static void lessOrEqual(final long value, final long maximum, final String name) {
		if (value > maximum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Long(maximum)));
		}
	}

	// Less
	public static void lessThan(final double value, final double maximum, final String name) {
		if (value >= maximum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Double(maximum)));
		}
	}

	public static void lessThan(final long value, final long maximum, final String name) {
		if (value >= maximum) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Long(maximum)));
		}
	}

	// Not Null
	public static void notNull(final Object obj, final String name) {
		if (obj == null) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_NULL, name));
		}
	}

	// Not Empty
	@SuppressWarnings("unchecked")
	public static void notEmpty(final Collection collection, final String name) {
		Assert.notNull(collection, name);

		if (collection.isEmpty()) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	public static void notEmpty(final Object[] array, final String name) {
		Assert.notNull(array, name);

		if (array.length == 0) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	public static void notEmpty(final String str, final String name) {
		if (StringUtils.isEmpty(str)) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	// Regex
	public static void validRegex(final String value, final String pattern, final String name) {
		Assert.notEmpty(value, name);
		Assert.notEmpty(pattern, "pattern");

		if (!Pattern.matches(pattern, value)) {
			throw new AssertionFailedException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_VALID_REGEX, name));
		}
	}

}
