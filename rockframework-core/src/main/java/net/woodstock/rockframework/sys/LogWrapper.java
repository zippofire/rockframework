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

import org.apache.commons.logging.Log;

public class LogWrapper implements Log {

	private Log	log;

	public LogWrapper(Log log) {
		super();
		this.log = log;
	}

	public void debug(Object message, Throwable t) {
		if (this.log.isDebugEnabled()) {
			this.log.debug(message, t);
		}
	}

	public void debug(Object message) {
		if (this.log.isDebugEnabled()) {
			this.log.debug(message);
		}
	}

	public void error(Object message, Throwable t) {
		if (this.log.isErrorEnabled()) {
			this.log.error(message, t);
		}
	}

	public void error(Object message) {
		if (this.log.isErrorEnabled()) {
			this.log.error(message);
		}
	}

	public void fatal(Object message, Throwable t) {
		if (this.log.isFatalEnabled()) {
			this.log.fatal(message, t);
		}
	}

	public void fatal(Object message) {
		if (this.log.isFatalEnabled()) {
			this.log.fatal(message);
		}
	}

	public void info(Object message, Throwable t) {
		if (this.log.isInfoEnabled()) {
			this.log.info(message, t);
		}
	}

	public void info(Object message) {
		if (this.log.isInfoEnabled()) {
			this.log.info(message);
		}
	}

	public boolean isDebugEnabled() {
		return this.log.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return this.log.isErrorEnabled();
	}

	public boolean isFatalEnabled() {
		return this.log.isFatalEnabled();
	}

	public boolean isInfoEnabled() {
		return this.log.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return this.log.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return this.log.isWarnEnabled();
	}

	public void trace(Object message, Throwable t) {
		if (this.log.isTraceEnabled()) {
			this.log.trace(message, t);
		}
	}

	public void trace(Object message) {
		if (this.log.isTraceEnabled()) {
			this.log.trace(message);
		}
	}

	public void warn(Object message, Throwable t) {
		if (this.log.isWarnEnabled()) {
			this.log.warn(message, t);
		}
	}

	public void warn(Object message) {
		if (this.log.isWarnEnabled()) {
			this.log.warn(message);
		}
	}

}
