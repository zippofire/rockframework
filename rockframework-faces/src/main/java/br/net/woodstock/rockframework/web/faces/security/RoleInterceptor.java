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

import br.net.woodstock.rockframework.utils.ArrayUtils;
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
		WebLog.getInstance().getLogger().debug("Checking is user is in role");
		Role annotation = null;
		Method method = context.getMethod();
		Class<?> methodClass = method.getDeclaringClass();

		if (method.isAnnotationPresent(Role.class)) {
			annotation = method.getAnnotation(Role.class);
		} else {
			annotation = this.getAnnotation(methodClass, methodClass);
		}

		if (annotation == null) {
			Class<?> targetClass = context.getTarget().getClass();
			if (methodClass.isAssignableFrom(targetClass)) {
				annotation = this.getAnnotation(targetClass, methodClass);
			}
		}

		if (annotation != null) {
			String[] roles = annotation.value();
			WebLog.getInstance().getLogger().debug("Checking is user is in roles " + ArrayUtils.toString(roles));
			if (ConditionUtils.isNotEmpty(roles)) {
				if (this.validator.isValid(context, roles)) {
					return context.proceed();
				}
				WebLog.getInstance().getLogger().debug("User inst in roles " + ArrayUtils.toString(roles));
				return this.validator.onInvalid(context);
			}
		}

		return context.proceed();
	}

	private Role getAnnotation(final Class<?> clazz, final Class<?> topClazz) {
		WebLog.getInstance().getLogger().debug("Searching for @Role in class " + clazz.getCanonicalName());
		if (clazz.isAnnotationPresent(Role.class)) {
			return clazz.getAnnotation(Role.class);
		} else if (clazz.getPackage().isAnnotationPresent(Role.class)) {
			return clazz.getPackage().getAnnotation(Role.class);
		}
		Class<?> parent = clazz.getSuperclass();
		if ((parent != null) && (topClazz.isAssignableFrom(parent))) {
			return this.getAnnotation(parent, topClazz);
		}
		return null;
	}
}
