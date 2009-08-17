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

import java.io.Serializable;
import java.util.Collection;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.utils.ObjectUtils;

public abstract class EntityUtils {

	public static final int	HASH_PRIME	= 31;

	private EntityUtils() {
		//
	}

	public static boolean equals(Entity<?> entity1, Entity<?> entity2) {
		if ((entity1 == null) || (entity2 == null)) {
			return false;
		}
		if (!entity1.getClass().isAssignableFrom(entity2.getClass())) {
			return false;
		}

		Object id1 = ((Entity<?>) entity1).getId();
		Object id2 = ((Entity<?>) entity2).getId();

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

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(entity1.getClass());
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
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

	public static int hashCode(Entity<?> entity) {
		Serializable id = entity.getId();

		if (id != null) {
			return id.hashCode();
		}

		int result = 1;

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(entity.getClass());
		Collection<PropertyDescriptor> properties = beanDescriptor.getProperties();

		for (PropertyDescriptor property : properties) {
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

	private static boolean isValidType(Class<?> clazz) {
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
		// Primitives
		if (clazz.isEnum()) {
			return true;
		}
		if (clazz.isPrimitive()) {
			return true;
		}
		// Entity
		if (Entity.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

}
