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
package net.woodstock.rockframework.web.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.Interceptor;

public abstract class BaseInterceptor implements Interceptor {

	private static final long	serialVersionUID	= 8055330131508038464L;

	public BaseInterceptor() {
		super();
	}

	public void destroy() {
		this.getLogger().debug("Destroying " + this.getClass().getCanonicalName());
	}

	public void init() {
		this.getLogger().debug("Initing " + this.getClass().getCanonicalName());
	}

	public Log getLogger() {
		return SysLogger.getLogger();
	}

	protected String getActionName() {
		return ActionContext.getContext().getName();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession getSession() {
		return this.getRequest().getSession();
	}

	protected Object getRequestAttribute(String name) {
		return this.getRequest().getAttribute(name);
	}

	protected String getRequestParameter(String name) {
		return this.getRequest().getParameter(name);
	}

	protected Object getSessionAttribute(String name) {
		return this.getSession().getAttribute(name);
	}

	protected String getContextPath() {
		return this.getRequest().getContextPath();
	}

	protected String getRequestPath() {
		String path = this.getRequest().getRequestURI().replace(this.getContextPath(), "");
		if (path.indexOf('!') != -1) {
			path = path.replaceAll("!\\w*\\.", ".");
		}
		return path;
	}
}
