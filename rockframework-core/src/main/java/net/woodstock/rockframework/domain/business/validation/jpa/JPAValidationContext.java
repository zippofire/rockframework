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
package net.woodstock.rockframework.domain.business.validation.jpa;

import java.lang.annotation.Annotation;

import net.woodstock.rockframework.utils.StringUtils;

public class JPAValidationContext {

	private static final char		NAME_SEPARATOR	= '.';

	private Object					value;

	private String					name;

	private Annotation				annotation;

	private JPAValidationContext	parentContext;

	protected JPAValidationContext(final Object value, final String name, final Annotation annotation) {
		this(value, name, annotation, null);
	}

	protected JPAValidationContext(final Object value, final String name, final Annotation annotation, final JPAValidationContext parentContext) {
		super();
		this.value = value;
		this.name = name;
		this.annotation = annotation;
		this.parentContext = parentContext;
	}

	// Getters and Setters
	public Object getValue() {
		return this.value;
	}

	public String getName() {
		if (StringUtils.isEmpty(this.name)) {
			return StringUtils.BLANK;
		}
		return this.name;
	}

	public Annotation getAnnotation() {
		return this.annotation;
	}

	public JPAValidationContext getParentContext() {
		return this.parentContext;
	}

	// Others
	public String getCanonicalName() {
		StringBuilder builder = new StringBuilder();
		if (this.parentContext != null) {
			String parentName = this.parentContext.getCanonicalName();
			if (!StringUtils.isEmpty(parentName)) {
				builder.append(parentName);
				builder.append(JPAValidationContext.NAME_SEPARATOR);
			}
		}
		if (!StringUtils.isEmpty(this.name)) {
			builder.append(this.name);
		}
		return builder.toString();
	}

	public JPAValidationResult getSuccessResult() {
		return new JPAValidationResult(this.getCanonicalName(), this.getAnnotation(), false, null);
	}

	public JPAValidationResult getErrorResult(final String message) {
		return new JPAValidationResult(this.getCanonicalName(), this.getAnnotation(), true, message);
	}

}
