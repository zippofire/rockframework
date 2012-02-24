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
package br.net.woodstock.rockframework.web.jsf;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.jsf.utils.FacesUtils;

@Interceptor
@ExceptionHandler
public class ExceptionHandlerInterceptor implements Serializable {

	private static final long	serialVersionUID	= -2287074502520388653L;

	public static final String	ERROR_PAGE			= "/error.xhtml";

	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		try {
			Object obj = context.proceed();
			return obj;
		} catch (Exception e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			FacesUtils.addError(e);
			return ExceptionHandlerInterceptor.ERROR_PAGE;
		}
	}
}
