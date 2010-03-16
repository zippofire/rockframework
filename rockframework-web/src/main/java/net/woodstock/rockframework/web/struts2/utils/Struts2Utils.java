package net.woodstock.rockframework.web.struts2.utils;

import javax.servlet.http.HttpServletRequest;

public abstract class Struts2Utils {

	private Struts2Utils() {
		// 
	}

	public static String getRequestPath(final HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().replace(contextPath, "");
		if (path.indexOf('!') != -1) {
			path = path.replaceAll("!\\w*\\.", ".");
		}
		return path;
	}

}
