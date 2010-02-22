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
package net.woodstock.rockframework.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.woodstock.rockframework.web.config.WebLog;

import org.apache.commons.logging.Log;

public abstract class BaseServlet extends HttpServlet {

	private static final long	serialVersionUID	= -5677220024908433472L;

	@Override
	public final void init() throws ServletException {
		super.init();
	}

	@Override
	public final void init(final ServletConfig config) throws ServletException {
		super.init(config);
		this.doInit();
	}

	@SuppressWarnings("unused")
	public void doInit() throws ServletException {
		//
	}

	// Logger
	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}

}
