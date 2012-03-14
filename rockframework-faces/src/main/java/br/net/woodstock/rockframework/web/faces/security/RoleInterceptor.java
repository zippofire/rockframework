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
package br.net.woodstock.rockframework.web.faces.security;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;

@Interceptor
@Role
public class RoleInterceptor implements SecurityInterceptor {

	private static final long	serialVersionUID	= -982725715956751626L;

	@Inject
	private RoleValidator		validator;

	@Override
	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		WebLog.getInstance().getLog().info("Checking is user is in role");
		Role annotation = null;
		Method method = context.getMethod();
		if (method.isAnnotationPresent(Role.class)) {
			annotation = method.getAnnotation(Role.class);
		} else if (method.getDeclaringClass().isAnnotationPresent(Role.class)) {
			annotation = method.getDeclaringClass().getAnnotation(Role.class);
		} else if (method.getDeclaringClass().getPackage().isAnnotationPresent(Role.class)) {
			annotation = method.getDeclaringClass().getPackage().getAnnotation(Role.class);
		}

		if (annotation != null) {
			String[] roles = annotation.value();
			if (ConditionUtils.isNotEmpty(roles)) {
				if (this.validator.isValid(context, roles)) {
					return context.proceed();
				}
				return this.validator.onInvalid(context);
			}
		}

		return context.proceed();
	}

}
