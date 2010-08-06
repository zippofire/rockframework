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
package net.woodstock.rockframework.domain.business.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationResult;

@SuppressWarnings("rawtypes")
public abstract class AbstractJEEBusiness extends AbstractBusiness {

	private static Validator	validator;

	public AbstractJEEBusiness() {
		super();
		if (AbstractJEEBusiness.validator == null) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			AbstractJEEBusiness.validator = factory.getValidator();
		}
	}

	private ValidationResult validate(final Entity entity) {
		Set<ConstraintViolation<Entity>> constraintViolations = AbstractJEEBusiness.validator.validate(entity);
		if (constraintViolations.size() > 0) {
			ConstraintViolation<Entity> violation = constraintViolations.iterator().next();
			String message = violation.getMessage();
			new ValidationResult(true, message);
		}
		return new ValidationResult(false, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK));
	}

	// CRUD
	@Override
	public ValidationResult validateSave(final Entity entity) {
		return this.validate(entity);
	}

	@Override
	public ValidationResult validateUpdate(final Entity entity) {
		return this.validate(entity);
	}

}
