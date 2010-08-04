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
package net.woodstock.rockframework.domain.utils;

import java.util.Collection;
import java.util.Date;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilderImpl;
import net.woodstock.rockframework.utils.ObjectUtils;

@SuppressWarnings("unchecked")
public abstract class EntityUtils {

	private static final String	UNDEFINED_ID	= "undefined";

	private static final String	BEGIN_ID		= "[";

	private static final String	END_ID			= "]";

	private EntityUtils() {
		//
	}

	// Object
	public static boolean equals(final Entity entity1, final Entity entity2) {
		if ((entity1 == null) || (entity2 == null)) {
			return false;
		}
		if (!entity1.getClass().isAssignableFrom(entity2.getClass())) {
			return false;
		}

		Object id1 = entity1.getId();
		Object id2 = entity2.getId();

		if (id1 != null) {
			if (id2 == null) {
				return false;
			}
			if (!id1.equals(id2)) {
				return false;
			}
		} else if (id2 != null) {
			return false;
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(entity1.getClass()).getBeanDescriptor();
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
			if (!property.isReadable()) {
				continue;
			}
			if (EntityUtils.isValidType(property.getType())) {
				Object v1 = property.getValue(entity1);
				Object v2 = property.getValue(entity2);

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
		}
		return true;
	}

	public static int hashCode(final Entity entity) {
		Object id = entity.getId();

		if (id != null) {
			return id.hashCode();
		}

		int result = 1;

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(entity.getClass()).getBeanDescriptor();
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
			if (!property.isReadable()) {
				continue;
			}

			if (EntityUtils.isValidType(property.getType())) {
				Object value = property.getValue(entity);

				if (value != null) {
					result = ObjectUtils.HASH_PRIME * result + value.hashCode();
				} else {
					result = ObjectUtils.HASH_PRIME * result;
				}
			}
		}
		return result;
	}

	public static String toString(final Entity e) {
		if (e == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		Object id = e.getId();
		builder.append(e.getClass().getSimpleName());
		builder.append(EntityUtils.BEGIN_ID);
		if (id != null) {
			builder.append(id);
		} else {
			builder.append(EntityUtils.UNDEFINED_ID);
		}
		builder.append(EntityUtils.END_ID);
		return builder.toString();
	}

	private static boolean isValidType(final Class<?> clazz) {
		// Wrappers
		if (clazz == Boolean.class) {
			return true;
		}
		if (clazz == Byte.class) {
			return true;
		}
		if (clazz == Character.class) {
			return true;
		}
		if (clazz == Double.class) {
			return true;
		}
		if (clazz == Float.class) {
			return true;
		}
		if (clazz == Integer.class) {
			return true;
		}
		if (clazz == Long.class) {
			return true;
		}
		if (clazz == Short.class) {
			return true;
		}
		// String
		if (clazz == String.class) {
			return true;
		}
		// Number
		if (Number.class.isAssignableFrom(clazz)) {
			return true;
		}
		// Date
		if (Date.class.isAssignableFrom(clazz)) {
			return true;
		}
		// Primitives
		if (clazz.isEnum()) {
			return true;
		}
		if (clazz.isPrimitive()) {
			return true;
		}

		return false;
	}

}
