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
package net.woodstock.rockframework.web.struts2.security;

import java.lang.reflect.Method;

import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.Constants;
import net.woodstock.rockframework.web.struts2.Interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class RoleInterceptor extends Interceptor {

	private static final long	serialVersionUID	= -1142678626424407060L;

	private RoleValidator		validator;

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		Class<?> clazz = action.getClass();
		Method method = clazz.getMethod(proxy.getMethod(), new Class[] {});

		String[] roles = null;
		boolean validate = false;

		if (method.isAnnotationPresent(Role.class)) {
			Role annotation = method.getAnnotation(Role.class);
			roles = annotation.value();
			validate = true;
		} else if (clazz.isAnnotationPresent(Role.class)) {
			Role annotation = clazz.getAnnotation(Role.class);
			roles = annotation.value();
			validate = true;
		}

		if (validate) {
			boolean hasAccess = false;
			for (String role : roles) {
				if (this.validator.isUserInRole(this.getRequest(), role)) {
					hasAccess = true;
					break;
				}
			}

			if (!hasAccess) {
				WebLog.getInstance().getLog().debug("Invalid privileges to call " + clazz.getName() + "." + method.getName() + "()");
				return Constants.NO_ACCESS;
			}
		}

		return invocation.invoke();
	}

	// Setters
	public void setValidator(final String validator) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.validator = (RoleValidator) Class.forName(validator).newInstance();
	}

}
