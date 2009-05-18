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

import javax.persistence.GeneratedValue;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.Operation;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.validator.AbstractObjectValidator;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;

public class ValidatorId extends AbstractObjectValidator {

	public ValidationResult validate(ValidationContext context) throws ValidationException {
		try {
			Object value = context.getValue();
			Object parent = context.getParentContext().getValue();
			Operation operation = context.getOperation();

			if (parent == null) {
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (!(parent instanceof Pojo)) {
				return context.getErrorResult(this.getInvalidTypeErrorMessage(context));
			}

			BeanInfo beanInfo = BeanInfo.getBeanInfo(parent.getClass());
			FieldInfo fieldInfo = beanInfo.getFieldInfo(context.getName());

			if ((operation == Operation.RETRIEVE) || (operation == Operation.UPDATE)
					|| (operation == Operation.DELETE)) {
				if (value == null) {
					return context.getErrorResult(this.getEmptyErrorMessage(context));
				}
				return context.getSuccessResult();
			} else if (operation == Operation.CREATE) {
				if (value == null) {
					if (fieldInfo.isAnnotationPresent(GeneratedValue.class)) {
						return context.getSuccessResult();
					}
					return context.getErrorResult(this.getEmptyErrorMessage(context));
				}

				if (fieldInfo.isAnnotationPresent(GeneratedValue.class)) {
					return context.getErrorResult(this.getNotEmptyErrorMessage(context));
				}
				return context.getSuccessResult();
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

	private String getNotEmptyErrorMessage(ValidationContext context) {
		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_NULL, context.getCanonicalName());
	}

}
