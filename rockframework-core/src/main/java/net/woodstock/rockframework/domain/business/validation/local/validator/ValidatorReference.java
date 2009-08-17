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
package net.woodstock.rockframework.domain.business.validation.local.validator;

import java.util.Collection;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateReference;

public class ValidatorReference extends AbstractValidator {

	public ValidationResult validate(LocalValidationContext context) throws ValidationException {
		try {
			ValidateReference annotation = (ValidateReference) context.getAnnotation();
			Object value = context.getValue();

			if (value == null) {
				if (annotation.notNull()) {
					return context.getErrorResult(this.getNotNullErrorMessage(context));
				}
				return context.getSuccessResult();
			}

			if (!(value instanceof Entity)) {
				return context.getErrorResult(this.getInvalidTypeErrorMessage(context));
			}

			Entity<?> e = (Entity<?>) value;

			LocalEntityValidator localEntityValidator = (LocalEntityValidator) LocalEntityValidator.getInstance();

			Collection<ValidationResult> results = localEntityValidator.validate(context, e, annotation.referenceOperaton());

			for (ValidationResult result : results) {
				if (result.isError()) {
					return context.getErrorResult(result.getMessage());
				}
			}
			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getNotNullErrorMessage(LocalValidationContext context) {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_NOT_NULL, context.getCanonicalName());
	}

	private String getInvalidTypeErrorMessage(LocalValidationContext context) {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_INVALID_TYPE, context.getCanonicalName());
	}

}
