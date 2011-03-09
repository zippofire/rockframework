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

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.ReflectionException;

class MethodPropertyDescriptor extends AbstractPropertyDescriptor {

	private String		name;

	private Method		readMethod;

	private String		readMethodName;

	private Method		writeMethod;

	private String		writeMethodName;

	private Class<?>	type;

	private int			modifiers;

	MethodPropertyDescriptor(final BeanDescriptor beanDescriptor, final String name, final Class<?> type) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.name = name;
		this.type = type;
		this.modifiers = -1;
		this.init();
	}

	private void init() {
		this.initGet();
		this.initSet();
	}

	private void initGet() {
		Class<?> c = this.getBeanDescriptor().getType();
		// Is
		if (this.type.equals(Boolean.TYPE)) {
			try {
				this.readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX, this.name);
				this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				CoreLog.getInstance().getLog().debug(e.getMessage(), e);
			}
			// Get
		} else {
			try {
				this.readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX, this.name);
				this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				CoreLog.getInstance().getLog().debug(e.getMessage(), e);
			}
		}
		this.modifiers = this.readMethod.getModifiers();
	}

	private void initSet() {
		Class<?> c = this.getBeanDescriptor().getType();
		// Set
		try {
			this.writeMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX, this.name);
			this.writeMethod = c.getMethod(this.writeMethodName, new Class[] { this.type });
		} catch (NoSuchMethodException e) {
			CoreLog.getInstance().getLog().debug(e.getMessage(), e);
		}
	}

	@Override
	public int getModifiers() {
		return this.modifiers;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		if (this.readMethod != null) {
			return this.readMethod.isAnnotationPresent(clazz);
		}
		return this.writeMethod.isAnnotationPresent(clazz);
	}

	@Override
	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		if (this.readMethod != null) {
			return this.readMethod.getAnnotation(clazz);
		}
		return this.writeMethod.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		if (this.readMethod != null) {
			return this.readMethod.getAnnotations();
		}
		return this.writeMethod.getAnnotations();
	}

	// Getters and Setters
	@Override
	public Object getValue(final Object o) {
		try {
			if (this.readMethod == null) {
				throw new NoSuchMethodException(this.getBeanDescriptor().getType().getCanonicalName() + "." + this.readMethodName);
			}
			return this.readMethod.invoke(o, new Object[] {});
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	@Override
	public void setValue(final Object o, final Object value) {
		try {
			if (this.writeMethod == null) {
				throw new NoSuchMethodException(this.getBeanDescriptor().getType().getCanonicalName() + "." + this.writeMethodName);
			}
			this.writeMethod.invoke(o, new Object[] { value });
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	// Aux
	@Override
	public boolean isReadable() {
		return this.readMethod != null;
	}

	@Override
	public boolean isWriteable() {
		return this.writeMethod != null;
	}

}
