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

import java.lang.reflect.Method;

import net.woodstock.rockframework.utils.StringUtils;

final class BeanDescriptorHelper {

	public static final String	GET_CLASS_METHOD_NAME	= "getClass";

	public static final String	GET_METHOD_PREFIX		= "get";

	public static final String	IS_METHOD_PREFIX		= "is";

	public static final String	SET_METHOD_PREFIX		= "set";

	private static final String	METHOD_PREFIX_REGEX		= "^(get|is|set)";

	private BeanDescriptorHelper() {
		super();
	}

	public static String getMethodName(final String prefix, final String property) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(Character.toUpperCase(property.charAt(0)));
		builder.append(property.substring(1));
		return builder.toString();
	}

	public static String getPropertyName(final Method method) {
		String methodName = method.getName();
		StringBuilder builder = new StringBuilder();
		String name = methodName.replaceAll(BeanDescriptorHelper.METHOD_PREFIX_REGEX, StringUtils.BLANK);
		builder.append(Character.toLowerCase(name.charAt(0)));
		builder.append(name.substring(1));
		return builder.toString();
	}

	public static Class<?> getPropertyType(final Method method) {
		if (BeanDescriptorHelper.isGetter(method)) {
			return method.getReturnType();
		} else if (BeanDescriptorHelper.isSetter(method)) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			if ((parameterTypes != null) && (parameterTypes.length == 1)) {
				return parameterTypes[0];
			}
		}
		return null;
	}

	public static boolean isGetter(final Method method) {
		String methodName = method.getName();
		if (methodName.startsWith(BeanDescriptorHelper.GET_METHOD_PREFIX)) {
			if (methodName.equals(BeanDescriptorHelper.GET_CLASS_METHOD_NAME)) {
				return false;
			}
			if ((method.getParameterTypes() != null) && (method.getParameterTypes().length > 0)) {
				return false;
			}
			if (method.getReturnType().equals(Void.TYPE)) {
				return false;
			}
			return true;
		}
		if (methodName.startsWith(BeanDescriptorHelper.IS_METHOD_PREFIX)) {
			return true;
		}
		return false;
	}

	public static boolean isSetter(final Method method) {
		String methodName = method.getName();
		if (methodName.startsWith(BeanDescriptorHelper.SET_METHOD_PREFIX)) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			if ((parameterTypes != null) && (parameterTypes.length == 1)) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static boolean isValidGetterOrSetter(final Method method) {
		if (BeanDescriptorHelper.isGetter(method)) {
			return true;
		}
		if (BeanDescriptorHelper.isSetter(method)) {
			return true;
		}
		return false;
	}

}
