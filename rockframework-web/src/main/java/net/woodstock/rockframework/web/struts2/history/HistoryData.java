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
package net.woodstock.rockframework.web.struts2.history;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class HistoryData implements Serializable {

	private static final long	serialVersionUID	= 1500323178751402507L;

	private String				url;

	private String				className;

	private String				methodName;

	private HttpServletRequest	request;

	public HistoryData(final String url, final String className, final String methodName, final HttpServletRequest request) {
		super();
		this.url = url;
		this.className = className;
		this.methodName = methodName;
		this.request = request;
	}

	public String getUrl() {
		return this.url;
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

}
