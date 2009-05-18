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
package net.woodstock.rockframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FieldInfo {

	private BeanInfo	beanInfo;

	private Field		field;

	private String		readMethodName;

	private String		writeMethodName;

	private Method		readMethod;

	private Method		writeMethod;

	FieldInfo(BeanInfo beanInfo, Field field) throws NoSuchMethodException {
		super();
		this.beanInfo = beanInfo;
		this.field = field;
		this.init();
	}

	private void init() throws NoSuchMethodException {
		this.initGet();
		this.initSet();
	}

	private void initGet() throws NoSuchMethodException {
		Class<?> c = this.field.getDeclaringClass();
		// Is
		if (this.field.getType().getCanonicalName().equals(boolean.class.getCanonicalName())) {
			this.readMethodName = this.getMethodName("is", this.field);
			try {
				this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
				return;
			} catch (NoSuchMethodException scme) {
				// scme.printStackTrace();
			}
		}
		// Get
		this.readMethodName = this.getMethodName("get", this.field);
		this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
	}

	private void initSet() throws NoSuchMethodException {
		Class<?> c = this.field.getDeclaringClass();
		this.writeMethodName = this.getMethodName("set", this.field);
		this.writeMethod = c.getMethod(this.writeMethodName, new Class[] { this.field.getType() });
	}

	public BeanInfo getBeanInfo() {
		return this.beanInfo;
	}

	public Field getField() {
		return this.field;
	}

	public String getFieldName() {
		return this.field.getName();
	}

	public Class<?> getFieldType() {
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

	public Method getReadMethod() {
		return this.readMethod;
	}

	public String getReadMethodName() {
		return this.readMethodName;
	}

	public Method getWriteMethod() {
		return this.writeMethod;
	}

	public String getWriteMethodName() {
		return this.writeMethodName;
	}

	public Object getFieldValue(Object o) throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (Modifier.isPublic(this.field.getModifiers())) {
			return this.field.get(o);
		}
		if (this.readMethod == null) {
			throw new NoSuchMethodException(this.readMethodName);
		}
		return this.readMethod.invoke(o, new Object[] {});
	}

	public void setFieldValue(Object o, Object value) throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (Modifier.isPublic(this.field.getModifiers())) {
			this.field.set(o, value);
			return;
		}
		if (this.writeMethod == null) {
			throw new NoSuchMethodException(this.writeMethodName);
		}
		this.writeMethod.invoke(o, new Object[] { value });
	}

	private String getMethodName(String prefix, Field field) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(Character.toUpperCase(field.getName().charAt(0)));
		builder.append(field.getName().substring(1));
		return builder.toString();
	}

	@Override
	public String toString() {
		return this.field.toString();
	}

}
