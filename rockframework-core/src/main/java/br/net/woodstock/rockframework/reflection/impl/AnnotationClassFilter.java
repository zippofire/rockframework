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

import br.net.woodstock.rockframework.reflection.ClassFilter;
import br.net.woodstock.rockframework.util.Assert;

public class AnnotationClassFilter implements ClassFilter {

	private Class<? extends Annotation>	annotation;

	public AnnotationClassFilter(final Class<? extends Annotation> annotation) {
		super();
		Assert.notNull(annotation, "annotation");
		this.annotation = annotation;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean accept(final Class clazz) {
		if (clazz.isAnnotationPresent(this.annotation)) {
			return true;
		}
		return false;
	}

}
