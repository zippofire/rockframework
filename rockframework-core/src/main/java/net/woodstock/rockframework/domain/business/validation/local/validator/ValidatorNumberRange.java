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

import java.math.BigDecimal;

import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNumberRange;

public class ValidatorNumberRange extends AbstractValidator {

	public ValidationResult validate(LocalValidationContext context) throws ValidationException {
		try {
			ValidateNumberRange annotation = (ValidateNumberRange) context.getAnnotation();
			Number value = (Number) context.getValue();

			if (value == null) {
				return context.getSuccessResult();
			}

			BigDecimal bigDecimal = new BigDecimal(value.toString());
			BigDecimal min = new BigDecimal(annotation.min());
			BigDecimal max = new BigDecimal(annotation.max());

			if ((bigDecimal.compareTo(min) < 0) || (bigDecimal.compareTo(max) > 0)) {
				return context.getErrorResult(this.getErrorMessage(annotation, context.getCanonicalName()));
			}

			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getErrorMessage(ValidateNumberRange annotation, String name) {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_RANGE, name, new BigDecimal(
				annotation.min()), new BigDecimal(annotation.max()));
	}

}
