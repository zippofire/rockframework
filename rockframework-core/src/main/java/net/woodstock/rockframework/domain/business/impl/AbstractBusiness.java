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

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

abstract class AbstractBusiness implements GenericBusiness {

	protected static final String	MESSAGE_INVALID_CLASS	= "domain.business.invalid.class";

	protected static final String	MESSAGE_INVALID_ID		= "domain.business.invalid.id";

	protected static final String	MESSAGE_INVALID_OBJECT	= "domain.business.invalid.object";

	protected static final String	MESSAGE_VALIDATION_OK	= "domain.business.validation.ok";

	public AbstractBusiness() {
		super();
	}

	@Override
	public ValidationResult validateGet(Entity<?> entity) throws BusinessException {
		boolean error = false;
		String message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK);
		if (entity == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_OBJECT, entity);
		}
		Object id = entity.getId();
		if (id == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_ID, id);
		}
		return new ValidationResult(error, message);
	}

	@Override
	public ValidationResult validateDelete(Entity<?> entity) throws BusinessException {
		return this.validateGet(entity);
	}

	@Override
	public ValidationResult validateList(Entity<?> entity) throws BusinessException {
		boolean error = false;
		String message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK);
		if (entity == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_OBJECT, entity);
		}
		return new ValidationResult(error, message);
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
