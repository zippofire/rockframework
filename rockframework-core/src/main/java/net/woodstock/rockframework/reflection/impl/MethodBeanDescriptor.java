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

import net.woodstock.rockframework.reflection.PropertyDescriptor;

class MethodBeanDescriptor extends AbstractBeanDescriptor {

	public MethodBeanDescriptor(final Class<?> clazz) {
		super(clazz);
	}

	@Override
	public void init() {
		Class<?> c = this.getType();
		for (Method method : c.getMethods()) {
			if (BeanDescriptorHelper.isValidGetterOrSetter(method)) {
				String propertyName = BeanDescriptorHelper.getPropertyName(method);
				if (!this.hasProperty(propertyName)) {
					Class<?> returnType = BeanDescriptorHelper.getPropertyType(method);
					PropertyDescriptor property = new MethodPropertyDescriptor(this, propertyName, returnType);
					this.getProperties().add(property);
				}
			}
		}
		c = c.getSuperclass();
	}

}
