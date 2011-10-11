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
package br.net.woodstock.rockframework.web.struts2.security;

import javax.servlet.http.HttpServletRequest;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.struts2.AbstractInterceptor;
import br.net.woodstock.rockframework.web.struts2.Constants;

import com.opensymphony.xwork2.ActionInvocation;

public class DenyGetParamInterceptor extends AbstractInterceptor {

	private static final long	serialVersionUID	= -7686764937974794750L;

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		HttpServletRequest request = this.getRequest();
		String queryString = request.getQueryString();

		if (ConditionUtils.isNotEmpty(queryString)) {
			return Constants.INVALID_METHOD;
		}

		return invocation.invoke();
	}

}
