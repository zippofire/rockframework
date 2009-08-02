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
import java.lang.reflect.Modifier;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.ReflectionException;

class FieldPropertyDescriptor extends AbstractPropertyDescriptor {

	private Field	field;

	FieldPropertyDescriptor(BeanDescriptor beanDescriptor, Field field) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.field = field;
		this.init();
	}

	private void init() {
		try {
			this.initGet();
			this.initSet();
		}
		catch (NoSuchMethodException e) {
			throw new ReflectionException(e);
		}
	}

	private void initGet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		// Is
		if (this.field.getType().getCanonicalName().equals(boolean.class.getCanonicalName())) {
			this.setReadMethodName(this.getMethodName(AbstractPropertyDescriptor.IS_METHOD_PREFIX, this.field));
			try {
				this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
				return;
			}
			catch (NoSuchMethodException scme) {
				// scme.printStackTrace();
			}
		}
		// Get
		this.setReadMethodName(this.getMethodName(AbstractPropertyDescriptor.GET_METHOD_PREFIX, this.field));
		this.setReadMethod(c.getMethod(this.getReadMethodName(), new Class[] {}));
	}

	private void initSet() throws NoSuchMethodException {
		Class<?> c = this.getBeanDescriptor().getType();
		this.setWriteMethodName(this.getMethodName(AbstractPropertyDescriptor.SET_METHOD_PREFIX, this.field));
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

	public Object getValue(Object o) {
		try {
			if (Modifier.isPublic(this.field.getModifiers())) {
				return this.field.get(o);
			}
			if (this.getReadMethod() == null) {
				throw new NoSuchMethodException(this.getReadMethodName());
			}
			return this.getReadMethod().invoke(o, new Object[] {});
		}
		catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	public void setValue(Object o, Object value) {
		try {
			if (Modifier.isPublic(this.field.getModifiers())) {
				this.field.set(o, value);
				return;
			}
			if (this.getWriteMethod() == null) {
				throw new NoSuchMethodException(this.getWriteMethodName());
			}
			this.getWriteMethod().invoke(o, new Object[] { value });
		}
		catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	private String getMethodName(String prefix, Field field) {
		String property = field.getName();
		return this.getMethodName(prefix, property);
	}

}
