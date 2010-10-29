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
import java.util.regex.Pattern;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.ConditionalInterceptor;
import net.woodstock.rockframework.web.struts2.Constants;
import net.woodstock.rockframework.web.utils.RequestUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class RefererInterceptor extends ConditionalInterceptor<String> {

	private static final long	serialVersionUID	= -5950458600867386751L;

	private String				regex;

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		Class<?> clazz = action.getClass();
		Method method = clazz.getMethod(proxy.getMethod(), new Class[] {});
		String rule = clazz.getCanonicalName() + "." + method.getName() + "()";

		String regex = null;
		boolean validate = false;

		if (this.containsRule(rule)) {
			validate = this.getRule(rule);
			regex = (String) this.getRuleValue(rule);
		} else {
			if (method.isAnnotationPresent(Referer.class)) {
				Referer annotation = method.getAnnotation(Referer.class);
				regex = this.getRegex(annotation);
				validate = true;
			} else if (clazz.isAnnotationPresent(Referer.class)) {
				Referer annotation = clazz.getAnnotation(Referer.class);
				regex = this.getRegex(annotation);
				validate = true;
			}
			this.addRule(rule, validate);
			this.addRuleValue(rule, regex);
		}

		if (validate) {
			String url = RequestUtils.getRequestUrl(this.getRequest());
			String referer = RequestUtils.getReferer(this.getRequest());

			if (StringUtils.isNotEmpty(referer)) {
				if (StringUtils.isNotEmpty(regex)) {
					if ((StringUtils.isEmpty(referer)) || (!Pattern.matches(regex, referer))) {
						WebLog.getInstance().getLog().debug("Invalid referer for " + url + " found " + referer + " required " + regex);
						return Constants.INVALID_REFERER;
					}
				}
			} else {
				WebLog.getInstance().getLog().info("Referer not found for " + url);
				return Constants.NO_REFERER;
			}

		}

		return invocation.invoke();
	}

	private String getRegex(final Referer annotation) {
		String s = annotation.value();
		if (StringUtils.isNotEmpty(s)) {
			return s;
		}
		return this.regex;
	}

	// Setters
	public void setRegex(final String regex) {
		this.regex = regex;
	}

}
