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

import java.util.Collection;

import javax.persistence.OneToOne;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.Operation;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.validator.AbstractObjectValidator;

public class ValidatorOneToOne extends AbstractObjectValidator {

	public ValidationResult validate(ValidationContext context) throws ValidationException {
		try {
			Object value = context.getValue();
			OneToOne annotation = (OneToOne) context.getAnnotation();
			Operation operation = context.getOperation();

			if (value == null) {
				if (annotation.optional()) {
					return context.getSuccessResult();
				}
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (!(value instanceof Pojo)) {
				return context.getErrorResult(this.getInvalidTypeErrorMessage(context));
			}

			if ((operation == Operation.CREATE) || (operation == Operation.UPDATE)) {
				Pojo p = (Pojo) value;
				Collection<ValidationResult> results = JPAObjectValidator.validate(context, p,
						Operation.RETRIEVE, true);
				for (ValidationResult result : results) {
					if (result.isError()) {
						return context.getErrorResult(result.getMessage());
					}
				}
			}

			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getEmptyErrorMessage(ValidationContext context) throws ValidationException {
		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_NOT_EMPTY, context.getCanonicalName());
	}

	private String getInvalidTypeErrorMessage(ValidationContext context) throws ValidationException {
		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_INVALID_TYPE, context.getCanonicalName());
	}

}
