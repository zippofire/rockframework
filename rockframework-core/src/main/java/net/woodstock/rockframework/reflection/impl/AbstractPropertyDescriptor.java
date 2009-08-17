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

import java.lang.reflect.Method;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionException;

abstract class AbstractPropertyDescriptor implements PropertyDescriptor {

	private BeanDescriptor	beanDescriptor;

	private Method			readMethod;

	private String			readMethodName;

	private Method			writeMethod;

	private String			writeMethodName;

	public AbstractPropertyDescriptor() {
		super();
	}

	public BeanDescriptor getBeanDescriptor() {
		return this.beanDescriptor;
	}

	public void setBeanDescriptor(BeanDescriptor beanDescriptor) {
		this.beanDescriptor = beanDescriptor;
	}

	public Method getReadMethod() {
		return this.readMethod;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public String getReadMethodName() {
		return this.readMethodName;
	}

	public void setReadMethodName(String readMethodName) {
		this.readMethodName = readMethodName;
	}

	public Method getWriteMethod() {
		return this.writeMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}

	public String getWriteMethodName() {
		return this.writeMethodName;
	}

	public void setWriteMethodName(String writeMethodName) {
		this.writeMethodName = writeMethodName;
	}

	// Getters and Setters
	public Object getValue(Object o) {
		try {
			if (this.getReadMethod() == null) {
				throw new NoSuchMethodException(this.getReadMethodName());
			}
			return this.getReadMethod().invoke(o, new Object[] {});
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	public void setValue(Object o, Object value) {
		try {
			if (this.getWriteMethod() == null) {
				throw new NoSuchMethodException(this.getWriteMethodName());
			}
			this.getWriteMethod().invoke(o, new Object[] { value });
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	// Object
	@Override
	public String toString() {
		return this.getName();
	}

}
