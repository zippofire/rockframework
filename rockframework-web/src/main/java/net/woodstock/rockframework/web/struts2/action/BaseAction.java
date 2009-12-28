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
package net.woodstock.rockframework.web.struts2.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.struts2.interceptor.PostOnlyInterceptor;

import org.apache.commons.logging.Log;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, Preparable {

	private static final long	serialVersionUID	= 655502050649662609L;

	public static final String	ERROR				= Action.ERROR;

	public static final String	INDEX				= "index";

	public static final String	INPUT				= Action.INPUT;

	public static final String	INVALID_METHOD		= PostOnlyInterceptor.INVALID_METHOD;

	public static final String	LOGIN				= Action.LOGIN;

	public static final String	LOGOUT				= "logout";

	public static final String	NONE				= Action.NONE;

	public static final String	NO_ACCESS			= "no-access";

	public static final String	NO_LOGIN			= "no-login";

	public static final String	REDIRECT			= "redirect";

	public static final String	SUCCESS				= Action.SUCCESS;

	private HttpServletRequest	request;

	private HttpServletResponse	response;

	public void prepare() throws Exception {
		this.prepare(this.getServletRequest());
	}

	@SuppressWarnings("unused")
	public void prepare(final HttpServletRequest request) throws Exception {
		//
	}

	protected HttpSession getSession() {
		return this.request.getSession();
	}

	protected HttpServletRequest getServletRequest() {
		return this.request;
	}

	protected HttpServletRequest getRequest() {
		return this.request;
	}

	public void setServletRequest(final HttpServletRequest request) {
		this.request = request;
	}

	protected HttpServletResponse getResponse() {
		return this.response;
	}

	protected HttpServletResponse getServletResponse() {
		return this.response;
	}

	public void setServletResponse(final HttpServletResponse response) {
		this.response = response;
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	protected String getRequestParameter(final String name) {
		return this.request.getParameter(name);
	}

	protected Object getRequestAttribute(final String name) {
		return this.request.getAttribute(name);
	}

	protected Object getSessionAttribute(final String name) {
		return this.request.getSession().getAttribute(name);
	}

	protected void setRequestAttribute(final String name, final Object value) {
		this.request.setAttribute(name, value);
	}

	protected void setSessionAttribute(final String name, final Object value) {
		this.request.getSession().setAttribute(name, value);
	}

	protected String getContextPath() {
		return this.getRequest().getContextPath();
	}

	protected String getRequestPath() {
		String path = this.getRequest().getRequestURI().replace(this.getContextPath(), StringUtils.BLANK);
		if (path.indexOf('!') != -1) {
			path = path.replaceAll("!\\w*\\.", ".");
		}
		return path;
	}
}
