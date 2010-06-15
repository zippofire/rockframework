package net.woodstock.rockframework.web.struts2.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;

public abstract class Struts2Utils {

	private static final String	METHOD_REGEX	= "!\\w*\\.";

	private static final String	METHOD_REPLACE	= ".";

	private Struts2Utils() {
		// 
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
		String path = request.getRequestURI().replace(contextPath, "");
		if (path.indexOf('!') != -1) {
			path = path.replaceAll(Struts2Utils.METHOD_REGEX, Struts2Utils.METHOD_REPLACE);
		}
		return path;
	}

}
