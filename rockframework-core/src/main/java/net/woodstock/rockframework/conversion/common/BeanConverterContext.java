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
package net.woodstock.rockframework.conversion.common;

import java.lang.annotation.Annotation;

import net.woodstock.rockframework.reflection.BeanDescriptor;

public class BeanConverterContext extends AbstractConverterContext {

	private BeanDescriptor	beanDescriptor;

	public BeanConverterContext(BeanDescriptor beanDescriptor, Object value) {
		super(null, beanDescriptor.getName(), beanDescriptor.getType(), value);
		this.beanDescriptor = beanDescriptor;
	}

	@Override
	public <A extends Annotation> A getAnnotation(Class<A> clazz) {
		return this.beanDescriptor.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this.beanDescriptor.getAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		return this.beanDescriptor.isAnnotationPresent(clazz);
	}

}
