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
package net.woodstock.rockframework.domain.business.validation.local.validator;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.business.validation.local.InitializableValidator;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public abstract class AbstractObjectValidator implements InitializableValidator {

	public void init() {
		this.getLogger().info("Initializing validator " + this.getClass().getCanonicalName());
	}

	// Utils
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	protected String getMessage(String message, Object... args) {
		return CoreMessage.getInstance().getMessage(message, args);
	}

}
