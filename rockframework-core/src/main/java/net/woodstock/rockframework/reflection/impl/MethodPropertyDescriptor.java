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

import net.woodstock.rockframework.reflection.BeanDescriptor;

class MethodPropertyDescriptor extends AbstractPropertyDescriptor {

	private String		name;

	private Class<?>	type;

	MethodPropertyDescriptor(BeanDescriptor beanDescriptor, String name, Class<?> type)
			throws NoSuchMethodException {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.name = name;
		this.type = type;
		this.init();
	}

	private void init() throws NoSuchMethodException {
		this.initGet();
		this.initSet();
	}

	private void initGet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		// Is
		if (this.type.getCanonicalName().equals(boolean.class.getCanonicalName())) {
			this.setReadMethodName(BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX,
					this.name));
			try {
				this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
				return;
			} catch (NoSuchMethodException scme) {
				// scme.printStackTrace();
			}
		}
		// Get
		this.setReadMethodName(BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX,
				this.name));
		this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
	}

	private void initSet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		this.setWriteMethodName(BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX,
				this.name));
		this.setWriteMethod(c.getMethod(this.getWriteMethodName(), new Class[] { this.type }));
	}

	public String getName() {
		return this.name;
	}

	public Class<?> getType() {
		return this.type;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		return this.getReadMethod().isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.getReadMethod().getAnnotation(clazz);
	}

	public Annotation[] getAnnotations() {
		return this.getReadMethod().getAnnotations();
	}

}
