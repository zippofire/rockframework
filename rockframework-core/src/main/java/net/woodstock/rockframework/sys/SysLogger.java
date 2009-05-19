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
package net.woodstock.rockframework.sys;

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SysLogger {

	private static final String	KEY_LOG_WRAPPER	= "log.wrapper";

	private static Log			log;

	private static boolean		wrapper;

	static {
		String s = CoreConfig.getInstance().getValue(SysLogger.KEY_LOG_WRAPPER, "false");
		if (!StringUtils.isEmpty(s)) {
			SysLogger.wrapper = Boolean.parseBoolean(s);
		} else {
			SysLogger.wrapper = false;
		}
	}

	private SysLogger() {
		super();
	}

	public static Log getLogger() {
		try {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			Class<?> clazz = stack[3].getClass();
			if (SysLogger.wrapper) {
				return new LogWrapper(LogFactory.getLog(clazz));
			}
			return LogFactory.getLog(clazz);
		} catch (Exception e) {
			return SysLogger.getCommonLogger();
		}
	}

	public static Log getCommonLogger() {
		if (SysLogger.log == null) {
			if (SysLogger.wrapper) {
				SysLogger.log = new LogWrapper(LogFactory.getLog(SysLogger.class));
			} else {
				SysLogger.log = LogFactory.getLog(SysLogger.class);
			}
		}
		return SysLogger.log;
	}
}
