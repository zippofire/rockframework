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

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log
public class RoleInterceptor implements SecurityInterceptor {

	private static final long	serialVersionUID	= -982725715956751626L;

	@Inject
	private LogonValidator		validator;

	@Override
	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		if (this.validator.isValid(context)) {
			return context.proceed();
		}
		return this.validator.onInvalid(context);
	}

}
