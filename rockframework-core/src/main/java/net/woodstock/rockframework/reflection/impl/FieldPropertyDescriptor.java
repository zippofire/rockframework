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

import net.woodstock.rockframework.reflection.BeanDescriptor;

class FieldPropertyDescriptor extends AbstractPropertyDescriptor {

	private Field	field;

	FieldPropertyDescriptor(BeanDescriptor beanDescriptor, Field field) throws NoSuchMethodException {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.field = field;
		this.init();
	}

	private void init() throws NoSuchMethodException {
		this.initGet();
		this.initSet();
	}

	private void initGet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		// Is
		if (this.field.getType().getCanonicalName().equals(boolean.class.getCanonicalName())) {
			this.setReadMethodName(this.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX, this.field));
			try {
				this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
				return;
			}
			catch (NoSuchMethodException scme) {
				// scme.printStackTrace();
			}
		}
		// Get
		this.setReadMethodName(this.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX, this.field));
		this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
	}

	private void initSet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		this.setWriteMethodName(this.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX, this.field));
		this.setWriteMethod(c.getMethod(this.getWriteMethodName(), new Class[] { this.field.getType() }));
	}

	public String getName() {
		return this.field.getName();
	}

	public Class<?> getType() {
		return this.field.getType();
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		return this.field.isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.field.getAnnotation(clazz);
	}

	public Annotation[] getAnnotations() {
		return this.field.getAnnotations();
	}

	private String getMethodName(String prefix, Field field) {
		String property = field.getName();
		return BeanDescriptorHelper.getMethodName(prefix, property);
	}

}
