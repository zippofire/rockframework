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
package br.net.woodstock.rockframework.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

public abstract class RequestUtils {

	public static final String	HEADER_ACCEPT						= "Accept";

	public static final String	HEADER_ACCEPT_CHARSET				= "Accept-Charset";

	public static final String	HEADER_ACCEPT_ENCODING				= "Accept-Encoding";

	public static final String	HEADER_ACCEPT_LANGUAGE				= "Accept-Language";

	public static final String	HEADER_CACHE_CONTROL				= "Cache-Control";

	public static final String	HEADER_CONNECTION					= "Connection";

	public static final String	HEADER_DATE							= "Date";

	public static final String	HEADER_HOST							= "Host";

	public static final String	HEADER_REFERER						= "Referer";

	public static final String	HEADER_USER_AGENT					= "User-Agent";

	public static final String	HEADER_X_FORWARDED_FOR				= "X-Forwarded-For";

	public static final String	JAVAX_SERVLET_FORWARD_REQUEST_URI	= "javax.servlet.forward.request_uri";

	public static final String	JAVAX_SERVLET_INCLUDE_REQUEST_URI	= "javax.servlet.include.request_uri";

	private RequestUtils() {
		//
	}

	private static String getServerUrl(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(request.getScheme());
		builder.append("://");
		builder.append(request.getServerName());
		builder.append(":");
		builder.append(request.getServerPort());
		return builder.toString();
	}

	private static String getRequestUri(final HttpServletRequest request) {
		String webInf = request.getContextPath() + "/WEB-INF";
		String uri = request.getRequestURI();
		if (uri.startsWith(webInf)) {
			uri = (String) request.getAttribute(RequestUtils.JAVAX_SERVLET_INCLUDE_REQUEST_URI);
			if (uri.startsWith(webInf)) {
				uri = (String) request.getAttribute(RequestUtils.JAVAX_SERVLET_FORWARD_REQUEST_URI);
			}
		}
		return uri;
	}

	public static String getApplicationUrl(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getServerUrl(request));
		builder.append(request.getContextPath());
		return builder.toString();
	}

	public static String getRequestUrl(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getServerUrl(request));
		builder.append(RequestUtils.getRequestUri(request));
		return builder.toString();
	}

	public static String getFullRequestUrl(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getRequestUrl(request));

		if (request.getQueryString() != null) {
			builder.append("?");
			builder.append(request.getQueryString());
		}

		return builder.toString();
	}

	public static String getRequestPath(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getRequestUri(request));
		return builder.toString();
	}

	public static String getFullRequestPath(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getRequestPath(request));

		if (request.getQueryString() != null) {
			builder.append("?");
			builder.append(request.getQueryString());
		}

		return builder.toString();
	}

	public static String getRequestAddress(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		String address = request.getHeader(RequestUtils.HEADER_X_FORWARDED_FOR);

		if (address != null) {
			if (address.indexOf(',') != -1) {
				address = address.split(",")[0];
			}
			return address;
		}

		return request.getRemoteAddr();
	}

	public static String getMethod(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		return request.getMethod();
	}

	public static String getReferer(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		return request.getHeader(RequestUtils.HEADER_REFERER);
	}

	public static String getRequestBody(final HttpServletRequest request) throws IOException {
		Assert.notNull(request, "request");
		InputStream inputStream = request.getInputStream();
		Charset charset = Charset.defaultCharset();
		if (ConditionUtils.isEmpty(request.getCharacterEncoding())) {
			charset = Charset.forName(request.getCharacterEncoding());
		}
		String body = new String(IOUtils.toByteArray(inputStream), charset);
		return body;
	}
}
