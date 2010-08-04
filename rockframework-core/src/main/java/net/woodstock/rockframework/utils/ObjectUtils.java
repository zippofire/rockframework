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

import java.util.Collection;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilderImpl;

public abstract class ObjectUtils {

	public static final int		COMPARE_TO_BEFORE	= -1;

	public static final int		COMPARE_TO_EQUALS	= 0;

	public static final int		COMPARE_TO_AFTER	= 1;

	public static final int		HASH_PRIME			= 31;

	private static final String	HASH_SEPARATOR		= "@";

	private ObjectUtils() {
		//
	}

	public static void copyAttributes(final Object from, final Object to, final Class<?>[] ignoredTypes) {
		BeanDescriptor beanDescriptorFrom = new BeanDescriptorBuilderImpl().setType(from.getClass()).getBeanDescriptor();
		BeanDescriptor beanDescriptorTo = new BeanDescriptorBuilderImpl().setType(to.getClass()).getBeanDescriptor();

		outer: for (PropertyDescriptor propertyDescriptor : beanDescriptorFrom.getProperties()) {
			if (beanDescriptorTo.hasProperty(propertyDescriptor.getName())) {
				PropertyDescriptor propertyDescriptorTo = beanDescriptorTo.getProperty(propertyDescriptor.getName());
				if (ignoredTypes != null) {
					for (Class<?> c : ignoredTypes) {
						if ((c.isAssignableFrom(propertyDescriptor.getType())) || (c.isAssignableFrom(propertyDescriptorTo.getType()))) {
							continue outer;
						}
					}
				}
				if (!propertyDescriptorTo.getType().isAssignableFrom(propertyDescriptor.getType())) {
					continue;
				}
				Object tmp = propertyDescriptor.getValue(from);
				propertyDescriptorTo.setValue(to, tmp);
			}
		}
	}

	public static void copyAttributes(final Object from, final Object to, final String[] ignoredAttributes) {
		BeanDescriptor beanDescriptorFrom = new BeanDescriptorBuilderImpl().setType(from.getClass()).getBeanDescriptor();
		BeanDescriptor beanDescriptorTo = new BeanDescriptorBuilderImpl().setType(to.getClass()).getBeanDescriptor();

		outer: for (PropertyDescriptor propertyDescriptor : beanDescriptorFrom.getProperties()) {
			if (beanDescriptorTo.hasProperty(propertyDescriptor.getName())) {
				PropertyDescriptor propertyDescriptorTo = beanDescriptorTo.getProperty(propertyDescriptor.getName());
				if (ignoredAttributes != null) {
					for (String s : ignoredAttributes) {
						if (s.equals(propertyDescriptor.getName())) {
							continue outer;
						}
					}
				}
				if (!propertyDescriptorTo.getType().isAssignableFrom(propertyDescriptor.getType())) {
					continue;
				}
				Object tmp = propertyDescriptor.getValue(from);
				propertyDescriptorTo.setValue(to, tmp);
			}
		}
	}

	public static boolean equals(final Object o1, final Object o2) {
		if ((o1 == null) || (o2 == null)) {
			return false;
		}
		if (!o1.getClass().isAssignableFrom(o2.getClass())) {
			return false;
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(o1.getClass()).getBeanDescriptor();
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
			Object v1 = property.getValue(o1);
			Object v2 = property.getValue(o2);

			if (v1 != null) {
				if (v2 == null) {
					return false;
				}
				if (!v1.equals(v2)) {
					return false;
				}
			} else if (v2 != null) {
				return false;
			}
		}
		return true;
	}

	public static int hashCode(final Object obj) {
		int result = 1;
		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(obj.getClass()).getBeanDescriptor();
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
			Object value = property.getValue(obj);

			if (value != null) {
				result = ObjectUtils.HASH_PRIME * result + value.hashCode();
			} else {
				result = ObjectUtils.HASH_PRIME * result;
			}
		}
		return result;
	}

	public static String toString(final Object obj) {
		if (obj == null) {
			return "null";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(obj.getClass().getName());
		builder.append(ObjectUtils.HASH_SEPARATOR);
		builder.append(obj.hashCode());
		return builder.toString();
	}

}
