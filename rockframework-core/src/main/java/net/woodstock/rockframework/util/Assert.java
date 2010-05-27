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

	// Greater
	public static void greaterThan(final double value, final double minimum, final String name) {
		if (value <= minimum) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Double(minimum)));
		}
	}

	public static void greaterThan(final long value, final long minimum, final String name) {
		if (value <= minimum) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_GREATER_THAN, name, new Long(minimum)));
		}
	}

	// Instance
	@SuppressWarnings("unchecked")
	public static void instanceOf(final Object value, final Class type, final String name) {
		Assert.notNull(value, name);

		if (!type.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_INSTANCE_OF, name, type.getCanonicalName()));
		}
	}

	// Array
	public static void isArray(final Object obj, final String name) {
		Assert.notNull(obj, name);

		if (!obj.getClass().isArray()) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_ARRAY, name));
		}
	}

	// IO
	public static void isDirectory(final File file, final String name) {
		Assert.notNull(file, name);

		if ((file.exists()) && (!file.isDirectory())) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_DIRECTORY, name));
		}
	}

	public static void isFile(final File file, final String name) {
		Assert.notNull(file, name);

		if ((file.exists()) && (!file.isFile())) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_IS_FILE, name));
		}
	}

	// Less
	public static void lessThan(final double value, final double maximum, final String name) {
		if (value >= maximum) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Double(maximum)));
		}
	}

	public static void lessThan(final long value, final long maximum, final String name) {
		if (value >= maximum) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_LESS_THAN, name, new Long(maximum)));
		}
	}

	// Not Null
	public static void notNull(final Object obj, final String name) {
		if (obj == null) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_NULL, name));
		}
	}

	// Not Empty
	@SuppressWarnings("unchecked")
	public static void notEmpty(final Collection collection, final String name) {
		Assert.notNull(collection, name);

		if (collection.isEmpty()) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	public static void notEmpty(final Object[] array, final String name) {
		Assert.notNull(array, name);

		if (array.length == 0) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	public static void notEmpty(final String str, final String name) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_NOT_EMPTY, name));
		}
	}

	// Regex
	public static void validRegex(final String value, final String pattern, final String name) {
		Assert.notEmpty(value, name);
		Assert.notEmpty(pattern, name);

		if (!Pattern.matches(pattern, value)) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(Assert.MESSAGE_VALID_REGEX, name));
		}
	}

}
