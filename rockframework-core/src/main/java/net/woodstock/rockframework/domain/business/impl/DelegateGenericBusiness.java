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
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

abstract class DelegateGenericBusiness implements GenericBusiness {

	protected static final String	MESSAGE_INVALID_CLASS	= "domain.business.invalid.class";

	protected static final String	MESSAGE_INVALID_ID		= "domain.business.invalid.id";

	protected static final String	MESSAGE_INVALID_OBJECT	= "domain.business.invalid.object";

	public DelegateGenericBusiness() {
		super();
	}

	// Commons
	public boolean validateCreate(Entity<?> entity) {
		try {
			this.validateCreateWithError(entity);
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	public <ID extends Serializable, E extends Entity<ID>> boolean validateRetrieve(Class<E> clazz, ID id) {
		try {
			this.validateRetrieveWithError(clazz, id);
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	public boolean validateUpdate(Entity<?> entity) {
		try {
			this.validateUpdateWithError(entity);
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	public boolean validateDelete(Entity<?> entity) {
		try {
			this.validateDeleteWithError(entity);
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	public boolean validateQuery(Entity<?> entity) {
		try {
			this.validateQueryWithError(entity);
			return true;
		} catch (ValidationException e) {
			return false;
		}
	}

	// Entity
	public <ID extends Serializable, E extends Entity<ID>> void validateRetrieveWithError(Class<E> clazz, ID id) throws BusinessException {
		if (clazz == null) {
			throw new ValidationException(CoreMessage.getInstance().getMessage(DelegateGenericBusiness.MESSAGE_INVALID_CLASS, clazz));
		}
		if (id == null) {
			throw new ValidationException(CoreMessage.getInstance().getMessage(DelegateGenericBusiness.MESSAGE_INVALID_ID, id));
		}
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
