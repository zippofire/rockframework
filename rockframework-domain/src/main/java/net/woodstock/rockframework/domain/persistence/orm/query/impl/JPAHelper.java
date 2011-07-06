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
package net.woodstock.rockframework.domain.persistence.orm.query.impl;

import javax.persistence.Entity;
import javax.persistence.Transient;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.ConditionUtils;

final class JPAHelper {

	private JPAHelper() {
		super();
	}

	public static boolean isTransient(final PropertyDescriptor descriptor) {
		if (descriptor.isAnnotationPresent(Transient.class)) {
			return true;
		}
		return false;
	}

	public static String getEntityName(final BeanDescriptor beanDescriptor) {
		if (beanDescriptor.isAnnotationPresent(Entity.class)) {
			Entity annotation = beanDescriptor.getAnnotation(Entity.class);
			String name = annotation.name();
			if (ConditionUtils.isEmpty(name)) {
				name = beanDescriptor.getType().getSimpleName();
			}
			return name;
		}
		return beanDescriptor.getType().getCanonicalName();
	}

}
