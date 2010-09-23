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
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.ValidationResult;

@SuppressWarnings("rawtypes")
abstract class AbstractBusiness implements GenericBusiness {

	private static final String		NULL_OBJECT_MESSAGE								= "null";

	protected static final String	MESSAGE_INVALID_CLASS					= "domain.business.invalid.class";

	protected static final String	MESSAGE_INVALID_ID						= "domain.business.invalid.id";

	protected static final String	MESSAGE_INVALID_OBJECT					= "domain.business.invalid.object";

	protected static final String	MESSAGE_VALIDATION_OK					= "domain.business.validation.ok";

	protected static final String	MESSAGE_VALIDATION_ERROR_INVALID		= "domain.business.validation.error.invalid";

	protected static final String	MESSAGE_VALIDATION_ERROR_INVALID_TYPE	= "domain.business.validation.error.invalidType";

	protected static final String	MESSAGE_VALIDATION_ERROR_LENGTH			= "domain.business.validation.error.length";

	protected static final String	MESSAGE_VALIDATION_ERROR_NOT_EMPTY		= "domain.business.validation.error.notEmpty";

	protected static final String	MESSAGE_VALIDATION_ERROR_NOT_NULL		= "domain.business.validation.error.notNull";

	protected static final String	MESSAGE_VALIDATION_ERROR_NULL			= "domain.business.validation.error.null";

	public AbstractBusiness() {
		super();
	}

	@Override
	public ValidationResult validateGet(final Entity entity) {
		boolean error = false;
		String message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK);
		if (entity == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_OBJECT, AbstractBusiness.NULL_OBJECT_MESSAGE);
		} else if (entity.getId() == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_ID, AbstractBusiness.NULL_OBJECT_MESSAGE);
		}
		return new ValidationResult(error, message);
	}

	@Override
	public ValidationResult validateDelete(final Entity entity) {
		return this.validateGet(entity);
	}

	@Override
	public ValidationResult validateList(final Entity entity) {
		boolean error = false;
		String message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK);
		if (entity == null) {
			error = true;
			message = CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_INVALID_OBJECT, AbstractBusiness.NULL_OBJECT_MESSAGE);
		}
		return new ValidationResult(error, message);
	}

}
