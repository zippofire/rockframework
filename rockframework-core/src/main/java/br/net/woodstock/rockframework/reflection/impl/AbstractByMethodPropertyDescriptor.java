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
package br.net.woodstock.rockframework.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.ReflectionException;

class AbstractByMethodPropertyDescriptor extends AbstractPropertyDescriptor {

	private String		name;

	private Field		field;

	private Method		readMethod;

	private String		readMethodName;

	private Method		writeMethod;

	private String		writeMethodName;

	private Class<?>	type;

	private int			modifiers;

	AbstractByMethodPropertyDescriptor(final BeanDescriptor beanDescriptor, final String propertyName, final Class<?> propertyType) {
		super();
		this.setBeanDescriptor(beanDescriptor);
		this.name = propertyName;
		this.type = propertyType;
		this.initGet();
		this.initSet();
		this.initField();
	}

	private void initGet() {
		Class<?> c = this.getBeanDescriptor().getType();
		// Is
		if (this.type.equals(Boolean.TYPE)) {
			try {
				this.readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.IS_METHOD_PREFIX, this.name);
				this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				CoreLog.getInstance().getLogger().debug(e.getMessage());
			}
			// Get
		} else {
			try {
				this.readMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.GET_METHOD_PREFIX, this.name);
				this.readMethod = c.getMethod(this.readMethodName, new Class[] {});
			} catch (NoSuchMethodException e) {
				CoreLog.getInstance().getLogger().debug(e.getMessage());
			}
		}
		if (this.readMethod != null) {
			this.modifiers = this.readMethod.getModifiers();
		}
	}

	private void initSet() {
		Class<?> c = this.getBeanDescriptor().getType();
		// Set
		try {
			this.writeMethodName = BeanDescriptorHelper.getMethodName(BeanDescriptorHelper.SET_METHOD_PREFIX, this.name);
			this.writeMethod = c.getMethod(this.writeMethodName, new Class[] { this.type });
		} catch (NoSuchMethodException e) {
			CoreLog.getInstance().getLogger().debug(e.getMessage());
		}
	}

	private void initField() {
		Class<?> c = this.getBeanDescriptor().getType();
		while (c != Object.class) {
			Field field = null;
			try {
				field = c.getDeclaredField(this.name);
			} catch (NoSuchFieldException e) {
				CoreLog.getInstance().getLogger().debug(e.getMessage());
			}
			if (field != null) {
				this.field = field;
				break;
			}
			c = c.getSuperclass();
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
		boolean b = false;
		if (this.readMethod != null) {
			b = this.readMethod.isAnnotationPresent(clazz);
		}
		if ((!b) && (this.field != null)) {
			b = this.field.isAnnotationPresent(clazz);
		}
		if ((!b) && (this.writeMethod != null)) {
			b = this.writeMethod.isAnnotationPresent(clazz);
		}
		return b;
	}

	@Override
	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		T t = null;
		if (this.readMethod != null) {
			t = this.readMethod.getAnnotation(clazz);
		}
		if ((t == null) && (this.field != null)) {
			t = this.field.getAnnotation(clazz);
		}
		if ((t == null) && (this.writeMethod != null)) {
			t = this.writeMethod.getAnnotation(clazz);
		}
		return t;
	}

	@Override
	public Annotation[] getAnnotations() {
		List<Annotation> annotations = new ArrayList<Annotation>();
		if (this.readMethod != null) {
			Annotation[] array = this.readMethod.getAnnotations();
			if ((array != null) && (array.length > 0)) {
				annotations.addAll(Arrays.asList(array));
			}
		}
		if (this.field != null) {
			Annotation[] array = this.field.getAnnotations();
			if ((array != null) && (array.length > 0)) {
				for (Annotation a : array) {
					if (!annotations.contains(a)) {
						annotations.add(a);
					}
				}
			}
		}
		if (this.writeMethod != null) {
			Annotation[] array = this.writeMethod.getAnnotations();
			if ((array != null) && (array.length > 0)) {
				for (Annotation a : array) {
					if (!annotations.contains(a)) {
						annotations.add(a);
					}
				}
			}
		}
		return annotations.toArray(new Annotation[annotations.size()]);
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
