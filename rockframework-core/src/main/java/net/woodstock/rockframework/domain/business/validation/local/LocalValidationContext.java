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
package net.woodstock.rockframework.domain.business.validation.local;

import java.lang.annotation.Annotation;

import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationContext;

public class LocalValidationContext extends ValidationContext {

	private Annotation	annotation;

	protected LocalValidationContext(final Object value, final String name, final Annotation annotation, final Operation operation) {
		this(value, name, annotation, operation, null);
	}

	protected LocalValidationContext(final Object value, final String name, final Annotation annotation, final Operation operation, final LocalValidationContext parentContext) {
		super(value, name, operation, parentContext);
		this.annotation = annotation;
	}

	// Getters and Setters
	public Annotation getAnnotation() {
		return this.annotation;
	}

	// Util
	public ValidationResult getSuccessResult() {
		return LocalValidationResult.getSuccessResult(this);
	}

	public ValidationResult getErrorResult(final String message) {
		return LocalValidationResult.getErrorResult(this, message);
	}

}
