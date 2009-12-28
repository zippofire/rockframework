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

import net.woodstock.rockframework.reflection.BeanNavigator;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.PropertyNavigator;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

class PropertyNavigatorImpl implements PropertyNavigator {

	private Object				bean;

	private BeanNavigator		parent;

	private PropertyDescriptor	propertyDescriptor;

	public PropertyNavigatorImpl(final Object bean, final BeanNavigator parent, final PropertyDescriptor propertyDescriptor) {
		super();
		this.bean = bean;
		this.parent = parent;
		this.propertyDescriptor = propertyDescriptor;
	}

	@Override
	public String getCanonicalName() {
		StringBuilder builder = new StringBuilder();
		if ((this.parent != null) && (this.parent.getName() != null)) {
			builder.append(this.parent.getCanonicalName());
			builder.append(BeanNavigatorImpl.PROPERTY_SEPARATOR);
		}
		builder.append(this.getName());
		return builder.toString();
	}

	@Override
	public String getName() {
		return this.propertyDescriptor.getName();
	}

	@Override
	public BeanNavigator getParent() {
		return this.parent;
	}

	@Override
	public Class<?> getType() {
		return this.propertyDescriptor.getType();
	}

	@Override
	public Object getValue() {
		if (this.bean == null) {
			return null;
		}
		return this.propertyDescriptor.getValue(this.bean);
	}

	@Override
	public void setValue(final Object value) {
		if (this.bean == null) {
			return;
		}
		this.propertyDescriptor.setValue(this.bean, value);
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	// Object
	@Override
	public String toString() {
		return this.getName();
	}

}
