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

import java.io.Serializable;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.InvalidFieldValueException;
import net.woodstock.rockframework.domain.business.InvalidValueException;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

public class AbstractHibernateBusiness extends DelegateGenericBusiness {

	public AbstractHibernateBusiness() {
		super();
	}

	@SuppressWarnings("unchecked")
	private void validate(Entity<?> entity) throws BusinessException {
		ClassValidator validator = new ClassValidator(entity.getClass());
		if (validator.hasValidationRules()) {
			InvalidValue values[] = validator.getInvalidValues(entity);
			if ((values != null) && (values.length > 0)) {
				throw new InvalidFieldValueException(values[0].getPropertyName(), values[0].getValue(),
						values[0].toString());
			}
		}
	}

	// CRUD
	public void validateCreateWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity);
	}

	public void validateRetrieveWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity);
	}

	public <ID extends Serializable, E extends Entity<ID>> void validateRetrieveWithError(Class<E> clazz,
			ID id) throws BusinessException {
		if (clazz == null) {
			throw new InvalidValueException(CoreMessage.getInstance()
					.getMessage(MESSAGE_INVALID_CLASS, clazz));
		}
		if (id == null) {
			throw new InvalidValueException(CoreMessage.getInstance().getMessage(MESSAGE_INVALID_ID, id));
		}
	}

	public void validateUpdateWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity);
	}

	public void validateDeleteWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity);
	}

	public void validateQueryWithError(Entity<?> entity) throws BusinessException {
		this.validate(entity);
	}

}
