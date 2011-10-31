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
package br.net.woodstock.rockframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.net.woodstock.rockframework.config.CoreConfig;

public abstract class LogUtils {

	private static final String	LOG_NAME_PROPERTY	= "logging.name";

	private static final String	LOG_NAME			= CoreConfig.getInstance().getValue(LogUtils.LOG_NAME_PROPERTY);

	private static Logger		log					= LoggerFactory.getLogger(LogUtils.LOG_NAME);

	private LogUtils() {
		super();
	}

	public static Logger getSharedLog() {
		return LogUtils.log;
	}

	public static Logger getLog(final Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger getLog(final String name) {
		return LoggerFactory.getLogger(name);
	}
}
