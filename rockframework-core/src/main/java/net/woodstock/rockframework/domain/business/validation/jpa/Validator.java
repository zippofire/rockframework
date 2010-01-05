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

import org.apache.commons.logging.Log;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.logging.SysLogger;

abstract class Validator {

	public static final String	REGEX_EMAIL		= "[a-zA-Z0-9\\.-_]{2,}@[a-zA-Z0-9\\.-]{2,}\\.[a-zA-Z0-9\\.-]{2,4}";

	public static final String	REGEX_URL_WEB	= "(http|https|ftp|ftps)://[a-zA-Z0-9\\.-_]{2,}\\.[a-zA-Z0-9\\.-_]{2,4}(:[0-9]{2,5})?(/|(/.*)?)";

	public Validator() {
		super();
		this.getLog().info("Initializing validator " + this.getClass().getCanonicalName());
	}

	// Utils
	protected Log getLog() {
		return SysLogger.getLog();
	}

	protected String getMessage(final String message, final Object... args) {
		return CoreMessage.getInstance().getMessage(message, args);
	}

	// Validate
	public abstract ValidationResult validate(JPAValidationContext context);

}
