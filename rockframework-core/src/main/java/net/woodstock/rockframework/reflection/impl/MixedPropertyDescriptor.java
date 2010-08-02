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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;

class MixedPropertyDescriptor implements PropertyDescriptor {

	private BeanDescriptor		beanDescriptor;

	private PropertyDescriptor	fieldPropertyDescriptor;

	private PropertyDescriptor	methodPropertyDescriptor;

	public MixedPropertyDescriptor(final BeanDescriptor beanDescriptor, final PropertyDescriptor fieldPropertyDescriptor, final PropertyDescriptor methodPropertyDescriptor) {
		super();
		this.beanDescriptor = beanDescriptor;
		this.fieldPropertyDescriptor = fieldPropertyDescriptor;
		this.methodPropertyDescriptor = methodPropertyDescriptor;
	}

	@Override
	public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
		if (this.fieldPropertyDescriptor != null) {
			A a = this.fieldPropertyDescriptor.getAnnotation(clazz);
			if (a != null) {
				return a;
			}
		}

		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.getAnnotation(clazz);
		}

		return null;
	}

	@Override
	public Annotation[] getAnnotations() {
		List<Annotation> list = new ArrayList<Annotation>();
		if (this.fieldPropertyDescriptor != null) {
			list.addAll(Arrays.asList(this.fieldPropertyDescriptor.getAnnotations()));
		}
		if (this.methodPropertyDescriptor != null) {
			list.addAll(Arrays.asList(this.methodPropertyDescriptor.getAnnotations()));
		}
		return list.toArray(new Annotation[list.size()]);
	}

	@Override
	public BeanDescriptor getBeanDescriptor() {
		return this.beanDescriptor;
	}

	@Override
	public int getModifiers() {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.getModifiers();
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.getModifiers();
		}
		return -1;
	}

	@Override
	public String getName() {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.getName();
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.getName();
		}
		return null;
	}

	@Override
	public Class<?> getType() {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.getType();
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.getType();
		}
		return null;
	}

	@Override
	public Object getValue(final Object o) {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.getValue(o);
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.getValue(o);
		}
		return null;
	}

	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		if (this.fieldPropertyDescriptor != null) {
			boolean b = this.fieldPropertyDescriptor.isAnnotationPresent(clazz);
			if (b) {
				return b;
			}
		}

		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.isAnnotationPresent(clazz);
		}

		return false;
	}

	@Override
	public boolean isReadable() {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.isReadable();
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.isReadable();
		}
		return false;
	}

	@Override
	public boolean isWriteable() {
		if (this.fieldPropertyDescriptor != null) {
			return this.fieldPropertyDescriptor.isWriteable();
		}
		if (this.methodPropertyDescriptor != null) {
			return this.methodPropertyDescriptor.isWriteable();
		}
		return false;
	}

	@Override
	public void setValue(final Object o, final Object value) {
		if (this.fieldPropertyDescriptor != null) {
			this.fieldPropertyDescriptor.setValue(o, value);
		}
		if (this.methodPropertyDescriptor != null) {
			this.methodPropertyDescriptor.setValue(o, value);
		}
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
