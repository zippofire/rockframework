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
package br.net.woodstock.rockframework.reflection;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface BeanDescriptor {

	String getName();

	Class<?> getType();

	// Property
	PropertyDescriptor getProperty(String name);

	boolean hasProperty(String name);

	Collection<PropertyDescriptor> getProperties();

	// Annotation
	boolean isAnnotationPresent(Class<? extends Annotation> clazz);

	<A extends Annotation> A getAnnotation(Class<A> clazz);

	Annotation[] getAnnotations();

}
