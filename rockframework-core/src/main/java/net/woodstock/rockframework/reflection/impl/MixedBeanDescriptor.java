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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;

public class MixedBeanDescriptor extends FieldBeanDescriptor {

	private BeanDescriptor					byMethodBeanDescriptor;

	private Map<String, PropertyDescriptor>	properties;

	public MixedBeanDescriptor(final Class<?> clazz) {
		super(clazz);
		this.byMethodBeanDescriptor = BeanDescriptorFactory.getInstance(ReflectionType.METHOD).getBeanDescriptor(clazz);
	}

	@Override
	public Collection<PropertyDescriptor> getProperties() {
		if (this.properties == null) {
			this.properties = new HashMap<String, PropertyDescriptor>();
			for (PropertyDescriptor property : super.getProperties()) {
				this.properties.put(property.getName(), property);
			}
			for (PropertyDescriptor property : this.byMethodBeanDescriptor.getProperties()) {
				if (!this.properties.containsKey(property.getName())) {
					this.properties.put(property.getName(), property);
				}
			}
		}
		return this.properties.values();
	}

	@Override
	public PropertyDescriptor getProperty(final String name) {
		if (this.hasProperty(name)) {
			return this.getProperty(name);
		}
		return this.byMethodBeanDescriptor.getProperty(name);
	}

	@Override
	public boolean hasProperty(final String name) {
		if (this.hasProperty(name)) {
			return true;
		}
		return this.byMethodBeanDescriptor.hasProperty(name);
	}

}
