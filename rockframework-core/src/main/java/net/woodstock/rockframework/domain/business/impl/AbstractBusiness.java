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

import java.util.Collection;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;

public abstract class AbstractBusiness extends DelegateGenericBusiness {

	public static final String	MESSAGE_INVALID_CLASS	= "domain.business.invalid.class";

	public static final String	MESSAGE_INVALID_ID		= "domain.business.invalid.id";

	public static final String	MESSAGE_INVALID_OBJECT	= "domain.business.invalid.object";

	public AbstractBusiness() {
		super();
	}

	private void validate(Pojo pojo, Operation operation) throws BusinessException {
		Collection<ValidationResult> results = LocalEntityValidator.getInstance().validate(pojo, operation);
		for (ValidationResult result : results) {
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
	}

	// CRUD
	public void validateCreateWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity, Operation.CREATE);
	}

	public void validateUpdateWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity, Operation.UPDATE);
	}

	public void validateDeleteWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity, Operation.DELETE);
	}

	public void validateQueryWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity, Operation.QUERY);
	}

}
