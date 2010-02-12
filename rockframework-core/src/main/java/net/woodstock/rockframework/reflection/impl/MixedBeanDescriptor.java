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

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;

class MixedBeanDescriptor extends AbstractBeanDescriptor {

	private BeanDescriptor	byFieldBeanDescriptor;

	private BeanDescriptor	byMethodBeanDescriptor;

	public MixedBeanDescriptor(final Class<?> clazz) {
		super(clazz);
	}

	@Override
	public void init() {
		this.byFieldBeanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD).getBeanDescriptor(this.getType());
		this.byMethodBeanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.METHOD).getBeanDescriptor(this.getType());

		for (PropertyDescriptor property : this.byFieldBeanDescriptor.getProperties()) {
			this.getProperties().add(property);
		}
		for (PropertyDescriptor property : this.byMethodBeanDescriptor.getProperties()) {
			if (!this.hasProperty(property.getName())) {
				this.getProperties().add(property);
			}
		}
	}

}
