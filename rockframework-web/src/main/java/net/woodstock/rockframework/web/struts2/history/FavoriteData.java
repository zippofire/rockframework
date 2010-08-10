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

public class FavoriteData implements Serializable {

	private static final long	serialVersionUID	= 4133654698635313525L;

	private String				url;

	private HttpServletRequest	request;

	public FavoriteData(final String url, final HttpServletRequest request) {
		super();
		this.url = url;
		this.request = request;
	}

	public String getUrl() {
		return this.url;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

}