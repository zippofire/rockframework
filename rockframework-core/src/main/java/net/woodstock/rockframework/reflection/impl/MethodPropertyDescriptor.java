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
import java.lang.reflect.Method;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class MethodPropertyDescriptor extends AbstractPropertyDescriptor {

	private String		name;

	private Class<?>	type;

	MethodPropertyDescriptor(BeanDescriptor beanDescriptor, String name, Class<?> type) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.name = name;
		this.type = type;
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
		if (this.type.equals(Boolean.TYPE)) {
			try {
				readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX, this.name);
				readMethod = c.getMethod(readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				// this.getLogger().info(e.getMessage(), e);
			}
		}
		// Get
		else {
			try {
				readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX, this.name);
				readMethod = c.getMethod(readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				// this.getLogger().info(e.getMessage(), e);
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
			writeMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX, this.name);
			writeMethod = c.getMethod(writeMethodName, new Class[] { this.type });
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
		}
		this.setWriteMethodName(writeMethodName);
		this.setWriteMethod(writeMethod);
	}

	public String getName() {
		return this.name;
	}

	public Class<?> getType() {
		return this.type;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		if (this.getReadMethod() != null) {
			return this.getReadMethod().isAnnotationPresent(clazz);
		}
		return this.getWriteMethod().isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		if (this.getReadMethod() != null) {
			return this.getReadMethod().getAnnotation(clazz);
		}
		return this.getWriteMethod().getAnnotation(clazz);
	}

	public Annotation[] getAnnotations() {
		if (this.getReadMethod() != null) {
			return this.getReadMethod().getAnnotations();
		}
		return this.getWriteMethod().getAnnotations();
	}

}
