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

import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;

public class ValidatorNotNull extends AbstractObjectValidator {

	public ValidationResult validate(ValidationContext context) throws ValidationException {
		try {
			Object value = context.getValue();
			if (value == null) {
				return context.getErrorResult(this.getErrorMessage(context));
			}
			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getErrorMessage(ValidationContext context) {
		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_NOT_NULL, context.getCanonicalName());
	}

}
