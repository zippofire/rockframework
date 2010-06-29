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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.Constants;
import net.woodstock.rockframework.web.struts2.Interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class RoleInterceptor extends Interceptor {

	private static final long	serialVersionUID	= -1142678626424407060L;

	private static final String	HISTORY_PARAMETER	= "net.woodstock.rockframework.web.struts2.security.HISTORY_PARAMETER";

	private static final String	NO_ACCESS_PARAMETER	= "net.woodstock.rockframework.web.struts2.security.NO_ACCESS_PARAMETER";

	private RoleValidator		validator;

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(final ActionInvocation invocation) throws Exception {
		Assert.notNull(this.validator, "validator");
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
			boolean skipTest = false;
			HttpSession session = this.getSession();

			String fullName = clazz.getCanonicalName() + "." + method.getName() + "()";

			if (session.getAttribute(RoleInterceptor.HISTORY_PARAMETER) == null) {
				session.setAttribute(RoleInterceptor.HISTORY_PARAMETER, new ArrayList<String>());
			}

			if (session.getAttribute(RoleInterceptor.NO_ACCESS_PARAMETER) == null) {
				session.setAttribute(RoleInterceptor.NO_ACCESS_PARAMETER, new ArrayList<String>());
			}

			List<String> history = (List<String>) session.getAttribute(RoleInterceptor.HISTORY_PARAMETER);
			List<String> noAccess = (List<String>) session.getAttribute(RoleInterceptor.NO_ACCESS_PARAMETER);

			if (history.contains(fullName)) {
				hasAccess = true;
				skipTest = true;
			} else if (noAccess.contains(fullName)) {
				hasAccess = false;
				skipTest = true;
			}

			if (!skipTest) {
				for (String role : roles) {
					if (this.validator.isUserInRole(this.getRequest(), role)) {
						hasAccess = true;
						break;
					}
				}
			}

			if (hasAccess) {
				history.add(fullName);
			} else {
				noAccess.add(fullName);
				WebLog.getInstance().getLog().info("Invalid privileges to call " + clazz.getName() + "." + method.getName() + "()");
				return Constants.NO_ACCESS;
			}
		}

		return invocation.invoke();
	}

	// Setters
	public void setValidatorClass(final String validatorClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.validator = (RoleValidator) Class.forName(validatorClass).newInstance();
	}

	protected void setValidator(final RoleValidator validator) {
		this.validator = validator;
	}

}
