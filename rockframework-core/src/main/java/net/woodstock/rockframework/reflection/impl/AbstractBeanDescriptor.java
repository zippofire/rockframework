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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;

import net.woodstock.rockframework.logging.SysLogger;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;

import org.apache.commons.logging.Log;

abstract class AbstractBeanDescriptor implements BeanDescriptor {

	private Class<?>						type;

	private Collection<PropertyDescriptor>	properties;

	public AbstractBeanDescriptor(final Class<?> clazz) {
		super();
		this.type = clazz;
		this.properties = new LinkedList<PropertyDescriptor>();
		this.init();
	}

	public abstract void init();

	public String getName() {
		return this.type.getSimpleName();
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	public boolean hasProperty(final String name) {
		for (PropertyDescriptor property : this.properties) {
			if (property.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public PropertyDescriptor getProperty(final String name) {
		for (PropertyDescriptor property : this.properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	@Override
	public Collection<PropertyDescriptor> getProperties() {
		return this.properties;
	}

	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		return this.type.isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		return this.type.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this.type.getAnnotations();
	}

	// Log
	protected Log getLog() {
		return SysLogger.getLog();
	}

	// Object
	@Override
	public String toString() {
		return this.getName();
	}

}
