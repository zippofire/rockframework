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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.woodstock.rockframework.reflection.PropertyDescriptor;

class FieldBeanDescriptor extends AbstractBeanDescriptor {

	public FieldBeanDescriptor(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public void init() {
		Class<?> c = this.getType();
		while (c != null) {
			for (Field field : c.getDeclaredFields()) {
				if (this.hasProperty(field.getName())) {
					continue;
				}

				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				try {
					PropertyDescriptor property = new FieldPropertyDescriptor(this, field);
					this.getProperties().add(property);
				}
				catch (NoSuchMethodException e) {
					this.getLogger().info(e.getMessage(), e);
				}
			}
			c = c.getSuperclass();
		}
	}

}
