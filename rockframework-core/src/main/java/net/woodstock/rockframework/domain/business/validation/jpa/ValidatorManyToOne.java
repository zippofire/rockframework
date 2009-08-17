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

import javax.persistence.ManyToOne;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.validator.AbstractValidator;

public class ValidatorManyToOne extends AbstractValidator {

	public ValidationResult validate(LocalValidationContext context) throws ValidationException {
		try {
			Object value = context.getValue();
			ManyToOne annotation = (ManyToOne) context.getAnnotation();
			Operation operation = context.getOperation();

			if (value == null) {
				if (annotation.optional()) {
					return context.getSuccessResult();
				}
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (!(value instanceof Entity)) {
				return context.getErrorResult(this.getInvalidTypeErrorMessage(context));
			}

			if ((operation == Operation.CREATE) || (operation == Operation.UPDATE)) {
				Entity<?> e = (Entity<?>) value;

				JPAEntityValidator entityValidator = (JPAEntityValidator) JPAEntityValidator.getInstance();

				Collection<ValidationResult> results = entityValidator.validate(context, e, Operation.RETRIEVE);
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

	private String getEmptyErrorMessage(LocalValidationContext context) throws ValidationException {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_NOT_EMPTY, context.getCanonicalName());
	}

	private String getInvalidTypeErrorMessage(LocalValidationContext context) throws ValidationException {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_INVALID_TYPE, context.getCanonicalName());
	}

}
