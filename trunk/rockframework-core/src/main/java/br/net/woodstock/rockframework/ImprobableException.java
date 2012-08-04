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
package br.net.woodstock.rockframework;

import java.util.logging.Level;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.util.Assert;

public class ImprobableException extends DelegateException {

	private static final long	serialVersionUID	= -9094311532446382264L;

	public ImprobableException(final Throwable cause) {
		super(cause);
		Assert.notNull(cause, "cause");
		CoreLog.getInstance().getLog().log(Level.SEVERE, cause.getMessage(), cause);
	}

	public ImprobableException(final String message) {
		super(message);
		Assert.notEmpty(message, "message");
		CoreLog.getInstance().getLog().severe(message);
	}

	public ImprobableException(final String message, final Throwable cause) {
		super(message, cause);
		Assert.notEmpty(message, "message");
		Assert.notNull(cause, "cause");
		CoreLog.getInstance().getLog().log(Level.SEVERE, message, cause);
	}

}
