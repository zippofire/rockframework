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
package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.utils.StringUtils;

abstract class BeanDescriptorHelper {

	public static String	GET_CLASS_METHOD_NAME	= "getClass";

	public static String	GET_METHOD_PREFIX		= "get";

	public static String	IS_METHOD_PREFIX		= "is";

	public static String	SET_METHOD_PREFIX		= "set";

	private static String	METHOD_PREFIX_REGEX		= "^(get|is|set)";

	public static boolean isGetter(String methodName) {
		if (methodName.startsWith(BeanDescriptorHelper.GET_METHOD_PREFIX)) {
			return true;
		}
		if (methodName.startsWith(BeanDescriptorHelper.IS_METHOD_PREFIX)) {
			return true;
		}
		return false;
	}

	public static boolean isSetter(String methodName) {
		if (methodName.startsWith(BeanDescriptorHelper.GET_METHOD_PREFIX)) {
			return true;
		}
		if (methodName.startsWith(BeanDescriptorHelper.IS_METHOD_PREFIX)) {
			return true;
		}
		return false;
	}

	public static String getMethodName(String prefix, String property) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(Character.toUpperCase(property.charAt(0)));
		builder.append(property.substring(1));
		return builder.toString();
	}

	public static String getPropertyName(String methodName) {
		StringBuilder builder = new StringBuilder();
		String name = methodName.replaceAll(BeanDescriptorHelper.METHOD_PREFIX_REGEX, StringUtils.BLANK);
		builder.append(Character.toLowerCase(name.charAt(0)));
		builder.append(name.substring(1));
		return builder.toString();
	}

}
