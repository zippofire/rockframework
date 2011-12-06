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
package br.net.woodstock.rockframework.web.jsf.security;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpSession;

@Interceptor
@Logon
public class LogonInterceptor implements Serializable {

	private static final long	serialVersionUID	= 3471332654974977295L;

	public static final String	USER_ATTRIBUTE		= "";					// FIXME

	public static final String	LOGIN_PAGE			= "";					// FIXME

	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session.getAttribute(LogonInterceptor.USER_ATTRIBUTE) != null) {
			return context.proceed();
		}
		return LogonInterceptor.LOGIN_PAGE;
	}

}
