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
import java.util.LinkedList;
import java.util.Map;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.BeanNavigator;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.PropertyNavigator;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

class BeanNavigatorImpl implements BeanNavigator {

	public static final String				PROPERTY_SEPARATOR	= ".";

	private Object							bean;

	private String							name;

	private BeanNavigator					parent;

	private BeanDescriptorFactory			factory;

	private BeanDescriptor					beanDescriptor;

	private Collection<PropertyNavigator>	properties;

	private Map<String, BeanNavigator>		beanProperties;

	public BeanNavigatorImpl(BeanDescriptorFactory factory, Object bean, String name, BeanNavigator parent) {
		super();
		if (bean == null) {
			throw new IllegalArgumentException("Bean is null");
		}
		this.factory = factory;
		this.bean = bean;
		this.name = name;
		this.parent = parent;
		this.beanDescriptor = factory.getBeanDescriptor(bean.getClass());
		this.properties = new LinkedList<PropertyNavigator>();
		this.beanProperties = new HashMap<String, BeanNavigator>();

		for (PropertyDescriptor propertyDescriptor : this.beanDescriptor.getProperties()) {
			this.properties.add(new PropertyNavigatorImpl(this.bean, this, propertyDescriptor));
		}
	}

	@Override
	public Object getBean() {
		return this.bean;
	}

	@Override
	public String getCanonicalName() {
		StringBuilder builder = new StringBuilder();
		if ((this.parent != null) && (this.parent.getName() != null)) {
			builder.append(this.parent.getCanonicalName());
			builder.append(BeanNavigatorImpl.PROPERTY_SEPARATOR);
		}
		if (this.name != null) {
			builder.append(this.name);
		}
		return builder.toString();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BeanNavigator getParent() {
		return this.parent;
	}

	@Override
	public Collection<PropertyNavigator> getProperties() {
		return this.properties;
	}

	@Override
	public PropertyNavigator getProperty(String name) {
		for (PropertyNavigator property : this.properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	@Override
	public BeanNavigator getPropertyAsBean(String name) {
		if (this.beanProperties.containsKey(name)) {
			return this.beanProperties.get(name);
		}

		PropertyNavigator property = this.getProperty(name);
		if (property != null) {
			Object bean = property.getValue();
			if (bean != null) {
				BeanNavigator navigator = new BeanNavigatorImpl(this.factory, property.getValue(), property.getName(), this);
				this.beanProperties.put(name, navigator);
				return navigator;
			}
		}
		return null;
	}

	@Override
	public Class<?> getType() {
		return this.beanDescriptor.getType();
	}

	@Override
	public boolean hasProperty(String name) {
		for (PropertyNavigator property : this.properties) {
			if (property.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// Cache/Reference

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
