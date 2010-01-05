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

import javax.persistence.Column;

import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;

public class ValidatorColumn extends Validator {

	@Override
	public ValidationResult validate(final JPAValidationContext context) {
		try {
			Object value = context.getValue();
			Column annotation = (Column) context.getAnnotation();

			if (value == null) {
				if (annotation.nullable()) {
					return context.getSuccessResult();
				}
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (value instanceof String) {
				int length = ((String) value).length();
				if (length > annotation.length()) {
					return context.getErrorResult(this.getLengthErrorMessage(context, annotation.length()));
				}
			}

			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLog().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getEmptyErrorMessage(final JPAValidationContext context) {
		return this.getMessage(JPAEntityValidator.MESSAGE_FIELD_ERROR_NOT_EMPTY, context.getCanonicalName());
	}

	private String getLengthErrorMessage(final JPAValidationContext context, final int length) {
		return this.getMessage(JPAEntityValidator.MESSAGE_FIELD_ERROR_LENGTH, context.getCanonicalName(), new Integer(0), new Integer(length));
	}

}
