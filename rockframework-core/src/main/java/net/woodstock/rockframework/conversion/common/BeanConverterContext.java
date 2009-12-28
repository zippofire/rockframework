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

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public class BeanConverterContext extends AbstractConverterContext {

	private BeanDescriptor	beanDescriptor;

	public BeanConverterContext(final ConverterContext parent, final String name, final Class<?> type) {
		super(parent, name, type);
		this.beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(type);
	}

	public BeanDescriptor getBeanDescriptor() {
		return this.beanDescriptor;
	}

	@Override
	public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
		return this.beanDescriptor.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this.beanDescriptor.getAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		return this.beanDescriptor.isAnnotationPresent(clazz);
	}

}
