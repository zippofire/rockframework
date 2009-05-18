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

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.InvalidValueException;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

abstract class DelegateGenericBusiness implements GenericBusiness {

	public static final String	MESSAGE_INVALID_CLASS	= "domain.business.invalid.class";

	public static final String	MESSAGE_INVALID_ID		= "domain.business.invalid.id";

	public static final String	MESSAGE_INVALID_OBJECT	= "domain.business.invalid.object";

	public DelegateGenericBusiness() {
		super();
	}

	// Commons
	public boolean validateCreate(Entity<?> entity) {
		try {
			this.validateCreateWithError(entity);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	public boolean validateRetrieve(Entity<?> entity) {
		try {
			this.validateRetrieveWithError(entity);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	public <ID extends Serializable, E extends Entity<ID>> boolean validateRetrieve(Class<E> clazz, ID id) {
		try {
			this.validateRetrieveWithError(clazz, id);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	public boolean validateUpdate(Entity<?> entity) {
		try {
			this.validateUpdateWithError(entity);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	public boolean validateDelete(Entity<?> entity) {
		try {
			this.validateDeleteWithError(entity);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	public boolean validateQuery(Entity<?> entity) {
		try {
			this.validateQueryWithError(entity);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
