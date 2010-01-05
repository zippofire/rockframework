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
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public class ValidatorId extends Validator {

	@Override
	public ValidationResult validate(final JPAValidationContext context) {
		try {
			Object value = context.getValue();
			Object parent = context.getParentContext().getValue();

			if (parent == null) {
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (!(parent instanceof Pojo)) {
				return context.getErrorResult(this.getInvalidTypeErrorMessage(context));
			}

			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(parent.getClass());
			PropertyDescriptor propertyDescriptor = beanDescriptor.getProperty(context.getName());

			if (value == null) {
				if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
					return context.getSuccessResult();
				}
				return context.getErrorResult(this.getEmptyErrorMessage(context));
			}

			if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
				return context.getErrorResult(this.getNotEmptyErrorMessage(context));
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

	private String getInvalidTypeErrorMessage(final JPAValidationContext context) {
		return this.getMessage(JPAEntityValidator.MESSAGE_FIELD_ERROR_INVALID_TYPE, context.getCanonicalName());
	}

	private String getNotEmptyErrorMessage(final JPAValidationContext context) {
		return this.getMessage(JPAEntityValidator.MESSAGE_FIELD_ERROR_NULL, context.getCanonicalName());
	}

}
