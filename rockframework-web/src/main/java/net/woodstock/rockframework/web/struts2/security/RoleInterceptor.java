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

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.Constants;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class RoleInterceptor extends AccessInterceptor<String> {

	private static final long		serialVersionUID	= -1142678626424407060L;

	private static final String		HISTORY_PARAMETER	= "net.woodstock.rockframework.web.struts2.security.RoleInterceptor.HISTORY_PARAMETER";

	private static final String		NO_ACCESS_PARAMETER	= "net.woodstock.rockframework.web.struts2.security.RoleInterceptor.NO_ACCESS_PARAMETER";

	private transient RoleValidator	validator;

	public RoleInterceptor() {
		super(RoleInterceptor.HISTORY_PARAMETER, RoleInterceptor.NO_ACCESS_PARAMETER);
	}

	public RoleInterceptor(final RoleValidator validator) {
		super(RoleInterceptor.HISTORY_PARAMETER, RoleInterceptor.NO_ACCESS_PARAMETER);
		this.validator = validator;
	}

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		Assert.notNull(this.validator, "validator");
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		Class<?> clazz = action.getClass();
		Method method = clazz.getMethod(proxy.getMethod(), new Class[] {});

		String rule = clazz.getCanonicalName() + "." + method.getName() + "()";

		String[] roles = null;
		boolean validate = false;

		if (this.containsRule(rule)) {
			validate = this.getRule(rule);
			roles = (String[]) this.getRuleValue(rule);
		} else {
			if (method.isAnnotationPresent(AllowAnyRole.class)) {
				validate = false;
			} else if (method.isAnnotationPresent(Role.class)) {
				Role annotation = method.getAnnotation(Role.class);
				roles = annotation.value();
				validate = true;
			} else if (clazz.isAnnotationPresent(Role.class)) {
				Role annotation = clazz.getAnnotation(Role.class);
				roles = annotation.value();
				validate = true;
			} else if (clazz.getPackage().isAnnotationPresent(Role.class)) {
				Role annotation = clazz.getAnnotation(Role.class);
				roles = annotation.value();
				validate = true;
			}
			this.addRule(rule, validate);
			this.addRuleValue(rule, roles);
		}

		if (validate) {
			boolean hasAccess = false;
			boolean skipTest = false;
			String fullName = rule;

			if (this.existOnHistory(fullName)) {
				hasAccess = true;
				skipTest = true;
			} else if (this.existOnNoAccess(fullName)) {
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
				this.addToHistory(fullName);
			} else {
				this.addToNoAccess(fullName);
				WebLog.getInstance().getLog().info("Invalid privileges to call " + fullName);
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
