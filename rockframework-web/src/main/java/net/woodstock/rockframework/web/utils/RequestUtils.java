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
package net.woodstock.rockframework.web.utils;

import javax.servlet.http.HttpServletRequest;

public abstract class RequestUtils {

	public static final String	HEADER_ACCEPT			= "accept";

	public static final String	HEADER_ACCEPT_CHARSET	= "accept-charset";

	public static final String	HEADER_ACCEPT_ENCODING	= "accept-encoding";

	public static final String	HEADER_ACCEPT_LANGUAGE	= "accept-language";

	public static final String	HEADER_REFERER			= "referer";

	public static final String	HEADER_USER_AGENT		= "user-agent";

	public static final String	HEADER_X_FORWARDED_FOR	= "X-Forwarded-For";

	private RequestUtils() {
		//
	}

	public static String getApplicationUrl(final HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(request.getScheme());
		builder.append("://");
		builder.append(request.getServerName());
		builder.append(":");
		builder.append(request.getServerPort());
		builder.append(request.getContextPath());
		return builder.toString();
	}

	public static String getRequestUrl(final HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getApplicationUrl(request));
		builder.append(request.getServletPath());

		return builder.toString();
	}

	public static String getFullRequestUrl(final HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getApplicationUrl(request));
		builder.append(request.getServletPath());

		if (request.getQueryString() != null) {
			builder.append("?");
			builder.append(request.getQueryString());
		}

		return builder.toString();
	}

	public static String getRequestAddress(final HttpServletRequest request) {
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
		return request.getMethod();
	}

	public static String getReferer(final HttpServletRequest request) {
		return request.getHeader(RequestUtils.HEADER_REFERER);
	}

}
