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
package net.woodstock.rockframework.logging;

import net.woodstock.rockframework.config.CoreConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SysLogger {

	private static final String		LOG_NAME_PROPERTY	= "logging.name";

	private static final String		LOG_SHARED_PROPERTY	= "logging.shared";

	private static final String		LOG_NAME			= CoreConfig.getInstance().getValue(SysLogger.LOG_NAME_PROPERTY);

	private static final boolean	LOG_SHARED			= Boolean.parseBoolean(CoreConfig.getInstance().getValue(SysLogger.LOG_SHARED_PROPERTY));

	private static Log				log					= LogFactory.getLog(SysLogger.LOG_NAME);

	private SysLogger() {
		super();
	}

	public static Log getLog() {
		if (SysLogger.LOG_SHARED) {
			return SysLogger.log;
		}
		try {
			StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
			Class<?> clazz = Class.forName(stacks[2].getClassName());
			return LogFactory.getLog(clazz);
		} catch (Exception e) {
			SysLogger.log.warn(e.getMessage(), e);
			return SysLogger.log;
		}
	}
}
