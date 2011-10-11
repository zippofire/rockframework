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
package br.net.woodstock.rockframework.web.jsp.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.net.http.HttpClient;
import br.net.woodstock.rockframework.net.http.HttpException;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.servlet.AbstractHttpServlet;

public class ProxyServlet extends AbstractHttpServlet {

	private static final long	serialVersionUID	= -3878060433162392807L;

	public static final String	URL_PARAMETER		= "br.net.woodstock.rockframework.web.jsp.util.ProxyServlet.URL_PARAMETER";

	public static final String	CHARSET_PARAMETER	= "br.net.woodstock.rockframework.web.jsp.util.ProxyServlet.CHARSET_PARAMETER";

	private String				url;

	private Charset				charset;

	@Override
	public void init() throws ServletException {
		super.init();
		this.url = this.getInitParameter(ProxyServlet.URL_PARAMETER);
		if (ConditionUtils.isEmpty(this.url)) {
			throw new IllegalArgumentException(ProxyServlet.URL_PARAMETER + "  must be setted");
		}

		String charset = this.getInitParameter(ProxyServlet.CHARSET_PARAMETER);
		if (!ConditionUtils.isEmpty(charset)) {
			this.charset = Charset.forName(charset);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		try {
			Enumeration<String> parameterNames = req.getParameterNames();
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			builder.append(this.url);
			builder.append("?");
			while (parameterNames.hasMoreElements()) {
				if (!first) {
					builder.append("&");
				}
				String name = parameterNames.nextElement();
				String value = req.getParameter(name);
				builder.append(name);
				builder.append("=");
				if (ConditionUtils.isEmpty(value)) {
					builder.append(value);
				}
				if (first) {
					first = false;
				}
			}
			HttpClient client = new HttpClient();
			byte[] bytes = client.doGet(builder.toString());
			resp.getOutputStream().write(bytes);
		} catch (HttpException e) {
			throw new ServletException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		try {
			Enumeration<String> parameterNames = req.getParameterNames();
			Map<String, Object> params = new HashMap<String, Object>();
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				String value = req.getParameter(name);
				params.put(name, value);
			}
			HttpClient client = new HttpClient();
			byte[] bytes = null;
			if (this.charset != null) {
				bytes = client.doPost(this.url, params, this.charset);
			} else {
				bytes = client.doPost(this.url, params);
			}
			resp.getOutputStream().write(bytes);
		} catch (HttpException e) {
			throw new ServletException(e);
		}
	}

}
