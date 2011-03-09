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
import net.woodstock.rockframework.reflection.ReflectionException;

class FieldPropertyDescriptor extends AbstractPropertyDescriptor {

	private Field	field;

	FieldPropertyDescriptor(final BeanDescriptor beanDescriptor, final Field field) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.field = field;
		this.field.setAccessible(true);
	}

	@Override
	public int getModifiers() {
		return this.field.getModifiers();
	}

	@Override
	public String getName() {
		return this.field.getName();
	}

	@Override
	public Class<?> getType() {
		return this.field.getType();
	}

	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		return this.field.isAnnotationPresent(clazz);
	}

	@Override
	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		return this.field.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this.field.getAnnotations();
	}

	@Override
	public Object getValue(final Object o) {
		try {
			return this.field.get(o);
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	@Override
	public void setValue(final Object o, final Object value) {
		try {
			this.field.set(o, value);
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	@Override
	public boolean isReadable() {
		return true;
	}

	@Override
	public boolean isWriteable() {
		return true;
	}

}
