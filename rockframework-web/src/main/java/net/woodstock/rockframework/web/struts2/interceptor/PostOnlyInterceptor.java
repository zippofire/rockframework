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
package net.woodstock.rockframework.web.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionInvocation;

public class PostOnlyInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= -1501873494031969315L;

	public static final String	INVALID_METHOD		= "invalid-method";

	public static final String	GET_METHOD			= "GET";

	public static final String	POST_METHOD			= "POST";

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = this.getRequest();

		if (!request.getMethod().equalsIgnoreCase(PostOnlyInterceptor.POST_METHOD)) {
			return PostOnlyInterceptor.INVALID_METHOD;
		}

		return invocation.invoke();
	}
}
