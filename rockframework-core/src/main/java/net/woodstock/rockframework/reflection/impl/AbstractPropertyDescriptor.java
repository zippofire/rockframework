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

abstract class AbstractPropertyDescriptor implements PropertyDescriptor {

	protected static final String	GET_METHOD_PREFIX	= "get";

	protected static final String	IS_METHOD_PREFIX	= "is";

	protected static final String	SET_METHOD_PREFIX	= "set";

	private BeanDescriptor			beanDescriptor;

	private Method					readMethod;

	private String					readMethodName;

	private Method					writeMethod;

	private String					writeMethodName;

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

	// Common
	protected String getMethodName(String prefix, String property) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(Character.toUpperCase(property.charAt(0)));
		builder.append(property.substring(1));
		return builder.toString();
	}

}
