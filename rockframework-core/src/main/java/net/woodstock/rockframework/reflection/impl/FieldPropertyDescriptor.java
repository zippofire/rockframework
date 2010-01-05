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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class FieldPropertyDescriptor extends AbstractPropertyDescriptor {

	private Field	field;

	FieldPropertyDescriptor(final BeanDescriptor beanDescriptor, final Field field) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.field = field;
		this.init();
	}

	private void init() {
		this.initGet();
		this.initSet();
	}

	private void initGet() {
		Class<?> c = this.getBeanDescriptor().getType();
		String readMethodName = null;
		Method readMethod = null;
		// Is
		if (this.field.getType().equals(Boolean.TYPE)) {
			try {
				readMethodName = this.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX, this.field);
				readMethod = c.getMethod(readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				this.getLog().debug(e.getMessage(), e);
			}
			// Get
		} else {
			try {
				readMethodName = this.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX, this.field);
				readMethod = c.getMethod(readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				this.getLog().debug(e.getMessage(), e);
			}
		}
		this.setReadMethodName(readMethodName);
		this.setReadMethod(readMethod);
	}

	private void initSet() {
		Class<?> c = this.getBeanDescriptor().getType();
		String writeMethodName = null;
		Method writeMethod = null;
		// Set
		try {
			writeMethodName = this.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX, this.field);
			writeMethod = c.getMethod(writeMethodName, new Class[] { this.field.getType() });
		} catch (NoSuchMethodException e) {
			this.getLog().debug(e.getMessage(), e);
		}
		this.setWriteMethodName(writeMethodName);
		this.setWriteMethod(writeMethod);
	}

	@Override
	public int getModifiers() {
		return this.field.getModifiers();
	}

	public String getName() {
		return this.field.getName();
	}

	public Class<?> getType() {
		return this.field.getType();
	}

	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		return this.field.isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		return this.field.getAnnotation(clazz);
	}

	public Annotation[] getAnnotations() {
		return this.field.getAnnotations();
	}

	private String getMethodName(final String prefix, final Field field) {
		String property = field.getName();
		return BeanDescriptorHelper.getMethodName(prefix, property);
	}

}
