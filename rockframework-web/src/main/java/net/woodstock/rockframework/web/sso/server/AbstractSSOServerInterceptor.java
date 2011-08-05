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
package net.woodstock.rockframework.web.sso.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.web.sso.SSOConstants;
import net.woodstock.rockframework.web.struts2.Constants;
import net.woodstock.rockframework.web.struts2.Interceptor;

import com.opensymphony.xwork2.ActionInvocation;

public abstract class AbstractSSOServerInterceptor extends Interceptor {

	private static final long	serialVersionUID	= -73503214795111201L;

	public static final String	DOMAIN_PARAMETER	= "domain";

	public static final String	URL_PARAMETER		= "url";

	private String				domain;

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		if (request.getParameter(SSOConstants.HASH_PARAMETER) != null) {
			SSOFilterServerHelper.configureHash(request);
		}

		if (request.getParameter(SSOConstants.REDIRECT_PARAMETER) != null) {
			String url = SSOFilterServerHelper.getRedirectURL(request, response, this.domain);
			request.setAttribute(AbstractSSOServerInterceptor.URL_PARAMETER, url);
			return Constants.REDIRECT;
		}

		return invocation.invoke();
	}

	public void setDomain(final String domain) {
		this.domain = domain;
	}

}
