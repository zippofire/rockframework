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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public abstract class RequestUtils {

	private RequestUtils() {
		//
	}

	public static String getParameter(HttpServletRequest request, String parameter) {
		return RequestUtils.getParameter(request, parameter, null);
	}

	public static String getParameter(HttpServletRequest request, String parameter, String nvl) {
		String value = request.getParameter(parameter);
		if (value == null) {
			value = nvl;
		}
		return value;
	}

	public static boolean hasParameter(HttpServletRequest request, String parameter) {
		if (RequestUtils.getParameter(request, parameter) != null) {
			return true;
		}
		return false;
	}

	public static void printParameters(HttpServletRequest request, OutputStream output) throws IOException {
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			output.write((name + " = " + request.getParameter(name) + "\n").getBytes());
		}
	}

	public static void printParameters(HttpServletRequest request, PrintStream output) {
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			output.print(name + " = " + request.getParameter(name) + "\n");
		}
	}

	public static void printParameters(HttpServletRequest request, Writer writer) throws IOException {
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			writer.write(name + " = " + request.getParameter(name) + "\n");
		}
	}

	public static String getApplicationUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(request.getScheme());
		builder.append("://");
		builder.append(request.getServerName());
		builder.append(":");
		builder.append(request.getServerPort());
		builder.append(request.getContextPath());
		return builder.toString();
	}

	public static String getRequestUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(RequestUtils.getApplicationUrl(request));
		builder.append(request.getServletPath());

		// if (request.getQueryString() != null) {
		// builder.append("?");
		// builder.append(request.getQueryString());
		// }

		return builder.toString();
	}

}
