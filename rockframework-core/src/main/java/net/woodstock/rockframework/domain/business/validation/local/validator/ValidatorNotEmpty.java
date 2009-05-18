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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;
import net.woodstock.rockframework.utils.StringUtils;

public class ValidatorNotEmpty extends AbstractObjectValidator {

	public ValidationResult validate(ValidationContext context) throws ValidationException {
		try {
			Object value = context.getValue();
			if (value == null) {
				return context.getErrorResult(this.getErrorMessage(context));
			}
			if (value.getClass().isArray()) {
				if (Array.getLength(value) == 0) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			if (value instanceof Character) {
				Character c = (Character) value;
				if ((c.charValue() == '\0') || (c.charValue() == '\n') || (c.charValue() == '\r')
						|| (c.charValue() == '\t') || (c.charValue() == ' ')) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			if (value instanceof Collection) {
				Collection<?> c = (Collection<?>) value;
				if (c.size() == 0) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			if (value instanceof Map) {
				Map<?, ?> m = (Map<?, ?>) value;
				if (m.size() == 0) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			if (value instanceof Number) {
				Number n = (Number) value;
				if (n.doubleValue() == 0) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			if (value instanceof String) {
				String s = (String) value;
				if (StringUtils.isEmpty(s)) {
					return context.getErrorResult(this.getErrorMessage(context));
				}
			}
			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getErrorMessage(ValidationContext context) {
		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_NOT_EMPTY, context.getCanonicalName());
	}

}
