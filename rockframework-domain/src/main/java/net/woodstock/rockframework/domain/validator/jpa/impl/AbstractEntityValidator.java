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
package net.woodstock.rockframework.domain.validator.jpa.impl;

import net.woodstock.rockframework.domain.config.DomainMessage;
import net.woodstock.rockframework.domain.validator.jpa.EntityValidator;

public abstract class AbstractEntityValidator implements EntityValidator {

	protected static final String	MESSAGE_VALIDATION_OK					= "domain.validation.ok";

	protected static final String	MESSAGE_INVALID_CLASS					= "domain.validation.invalid.class";

	protected static final String	MESSAGE_INVALID_ID						= "domain.validation.invalid.id";

	protected static final String	MESSAGE_INVALID_OBJECT					= "domain.validation.invalid.object";

	protected static final String	MESSAGE_VALIDATION_ERROR_INVALID		= "domain.validation.error.invalid";

	protected static final String	MESSAGE_VALIDATION_ERROR_INVALID_TYPE	= "domain.validation.error.invalidType";

	protected static final String	MESSAGE_VALIDATION_ERROR_LENGTH			= "domain.validation.error.length";

	protected static final String	MESSAGE_VALIDATION_ERROR_NOT_EMPTY		= "domain.validation.error.notEmpty";

	protected static final String	MESSAGE_VALIDATION_ERROR_NOT_NULL		= "domain.validation.error.notNull";

	protected static final String	MESSAGE_VALIDATION_ERROR_NULL			= "domain.validation.error.null";

	public AbstractEntityValidator() {
		super();
	}

	protected String getMessage(final String key, final Object... args) {
		return DomainMessage.getInstance().getMessage(key, args);
	}

}
