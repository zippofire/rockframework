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
import net.woodstock.rockframework.reflection.PropertyDescriptor;

public class PropertyConverterContext extends AbstractConverterContext {

	private PropertyDescriptor	propertyDescriptor;

	public PropertyConverterContext(final ConverterContext parent, final String name, final Class<?> type) {
		super(parent, name, type);
		if (!(parent instanceof BeanConverterContext)) {
			throw new IllegalArgumentException("Parent must be an instance of " + BeanConverterContext.class.getCanonicalName());
		}
		BeanConverterContext context = (BeanConverterContext) parent;
		BeanDescriptor beanDescriptor = context.getBeanDescriptor();
		this.propertyDescriptor = beanDescriptor.getProperty(name);
	}

	@Override
	public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
		return this.propertyDescriptor.getAnnotation(clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this.propertyDescriptor.getAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
		return this.propertyDescriptor.isAnnotationPresent(clazz);
	}

}
