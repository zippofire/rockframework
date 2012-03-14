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
package br.net.woodstock.rockframework.web.struts2.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;

import br.net.woodstock.rockframework.web.utils.RequestUtils;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.inject.Container;

public abstract class Struts2Utils {

	private static final String	METHOD_SEPARATOR_CHAR	= "!";

	private static final String	METHOD_SEPARATOR_CODE	= "%21";

	private static final String	METHOD_REGEX_CHAR		= Struts2Utils.METHOD_SEPARATOR_CHAR + "\\w*\\.";

	private static final String	METHOD_REGEX_CODE		= Struts2Utils.METHOD_SEPARATOR_CODE + "\\w*\\.";

	private static final String	METHOD_REPLACE			= ".";

	private Struts2Utils() {
		//
	}

	public static String getConstant(final String name) {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ConfigurationManager manager = dispatcher.getConfigurationManager();
		Configuration configuration = manager.getConfiguration();
		Container container = configuration.getContainer();
		String value = container.getInstance(String.class, name);
		return value;
	}

	public static PageContext getPageContext() {
		return ServletActionContext.getPageContext();
	}

	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public static ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public static String getRequestPath(final HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = RequestUtils.getRequestPath(request);
		String path = uri.replace(contextPath, "");
		if (path.indexOf(Struts2Utils.METHOD_SEPARATOR_CHAR) != -1) {
			path = path.replaceAll(Struts2Utils.METHOD_REGEX_CHAR, Struts2Utils.METHOD_REPLACE);
		}
		if (path.indexOf(Struts2Utils.METHOD_SEPARATOR_CODE) != -1) {
			path = path.replaceAll(Struts2Utils.METHOD_REGEX_CODE, Struts2Utils.METHOD_REPLACE);
		}
		return path;
	}

}
