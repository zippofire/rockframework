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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public abstract class ObjectUtils {

	public static final int		HASH_PRIME			= 31;

	private static final char	PROPERTY_SEPARATOR	= '.';

	public static void copyAttributes(Object from, Object to, Class<?>[] ignoredTypes) {
		BeanDescriptor beanDescriptorFrom = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(from.getClass());
		BeanDescriptor beanDescriptorTo = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(to.getClass());

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

	public static void copyAttributes(Object from, Object to, String[] ignoredAttributes) {
		BeanDescriptor beanDescriptorFrom = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(from.getClass());
		BeanDescriptor beanDescriptorTo = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(to.getClass());

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

	public static boolean equals(Object o1, Object o2) {
		if ((o1 == null) || (o2 == null)) {
			return false;
		}
		if (!o1.getClass().isAssignableFrom(o2.getClass())) {
			return false;
		}

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(o1.getClass());
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

	public static int hashCode(Object obj) {
		int result = 1;
		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(obj.getClass());
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

	private ObjectUtils() {
		//
	}

	public static Object getObjectAttribute(Object o, String name) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
		if (o == null) {
			return null;
		}

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(o.getClass());
		if (name.indexOf(ObjectUtils.PROPERTY_SEPARATOR) != -1) {
			String fieldName = name.substring(0, name.indexOf(ObjectUtils.PROPERTY_SEPARATOR));
			name = name.substring(name.indexOf(ObjectUtils.PROPERTY_SEPARATOR) + 1);

			PropertyDescriptor propertyDescriptor = beanDescriptor.getProperty(fieldName);

			Object tmp = propertyDescriptor.getValue(o);
			return ObjectUtils.getObjectAttribute(tmp, name);
		}

		PropertyDescriptor propertyDescriptor = beanDescriptor.getProperty(name);
		return propertyDescriptor.getValue(o);
	}

}