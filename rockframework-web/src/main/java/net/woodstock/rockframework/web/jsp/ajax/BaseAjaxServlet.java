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
package net.woodstock.rockframework.web.jsp.ajax;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseAjaxServlet extends HttpServlet {

	private static final long	serialVersionUID	= -6552625261282484182L;

	public static final String	PROPERTIES_FILE		= "PROPERTIES_FILE";

	private static final String	PATH_SEPARATOR		= "/";

	private static final String	JS_SUFFIX			= ".js";

	private static final String	JSP_SUFFIX			= ".jsp";

	private Map<String, Object>	classes;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			String propertiesFile = config.getInitParameter(BaseAjaxServlet.PROPERTIES_FILE);
			Properties properties = this.loadProperties(propertiesFile);
			this.loadProperties(propertiesFile);
			this.loadClasses(properties);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		if (url.endsWith(JS_SUFFIX)) {
			String name = url;
			if (name.indexOf(PATH_SEPARATOR) != -1) {
				name = name.substring(name.indexOf(PATH_SEPARATOR));
			}
			name = name.substring(0, name.indexOf(JS_SUFFIX));
		} else if (url.endsWith(JSP_SUFFIX)) {
			String name = url;
			if (name.indexOf(PATH_SEPARATOR) != -1) {
				name = name.substring(name.indexOf(PATH_SEPARATOR));
			}
			name = name.substring(0, name.indexOf(JSP_SUFFIX));
		}
	}

	protected Properties loadProperties(String propertiesFile) throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

	protected void loadClasses(Properties properties) throws Exception {
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			Object obj = this.loadClass(value);
			this.addClass(key, obj);
		}
	}

	protected void addClass(String name, Object obj) {
		if (this.classes == null) {
			this.classes = new HashMap<String, Object>();
		}
		this.classes.put(name, obj);
	}

	protected abstract Object loadClass(String name) throws Exception;

}
