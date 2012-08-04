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
package br.net.woodstock.rockframework.domain.business.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.util.Assert;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.business.BusinessResult;

@SuppressWarnings("rawtypes")
public abstract class AbstractJEEBusiness extends AbstractBusiness {

	private static final long	serialVersionUID	= 731186211424712999L;

	private static Validator	validator;

	public AbstractJEEBusiness() {
		super();
		if (AbstractJEEBusiness.validator == null) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			AbstractJEEBusiness.validator = factory.getValidator();
		}
	}

	protected BusinessResult validate(final Entity entity, final Class... groups) {
		Assert.notNull(entity, "entity");
		Set<ConstraintViolation<Entity>> constraintViolations = AbstractJEEBusiness.validator.validate(entity, groups);
		if (constraintViolations.size() > 0) {
			ConstraintViolation<Entity> violation = constraintViolations.iterator().next();
			String field = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			return new BusinessResult(true, field + " " + message);
		}
		return new BusinessResult(false, AbstractBusiness.OK_MESSAGE);
	}

}
