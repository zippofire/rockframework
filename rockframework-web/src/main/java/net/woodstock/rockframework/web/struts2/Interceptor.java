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
package net.woodstock.rockframework.web.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.utils.Struts2Utils;

import com.opensymphony.xwork2.ActionContext;

public abstract class Interceptor implements com.opensymphony.xwork2.interceptor.Interceptor {

	private static final long		serialVersionUID	= 8055330131508038464L;

	protected static final String	SKIP_INTERCEPTOR	= "net.woodstock.rockframework.web.struts2.Interceptor.SKIP_INTERCEPTOR";

	public Interceptor() {
		super();
	}

	public void destroy() {
		WebLog.getInstance().getLog().debug("Destroying " + this.getClass().getCanonicalName());
	}

	public void init() {
		WebLog.getInstance().getLog().debug("Initing " + this.getClass().getCanonicalName());
	}

	protected String getActionName() {
		return ActionContext.getContext().getName();
	}

	protected HttpServletRequest getRequest() {
		return Struts2Utils.getRequest();
	}

	protected HttpSession getSession() {
		return Struts2Utils.getSession();
	}

}
