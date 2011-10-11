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
package br.net.woodstock.rockframework.web.struts2.security;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.struts2.ConditionalInterceptor;
import br.net.woodstock.rockframework.web.struts2.Constants;
import br.net.woodstock.rockframework.web.utils.RequestUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class HttpMethodInterceptor extends ConditionalInterceptor<String> {

	private static final long	serialVersionUID	= -7686764937974794750L;

	private static final String	METHOD_SEPARATOR	= ",";

	private Set<HttpMethodType>	methods;

	public HttpMethodInterceptor() {
		super();
		this.methods = new HashSet<HttpMethodType>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(final ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		Class<?> clazz = action.getClass();
		Method method = clazz.getMethod(proxy.getMethod(), new Class[] {});
		String rule = clazz.getCanonicalName() + "." + method.getName() + "()";

		Set<HttpMethodType> methods = null;
		boolean validate = false;

		if (this.containsRule(rule)) {
			validate = this.getRule(rule);
			methods = (Set<HttpMethodType>) this.getRuleValue(rule);
		} else {
			if (method.isAnnotationPresent(AllowAnyHttpMethod.class)) {
				validate = false;
			} else if (method.isAnnotationPresent(HttpMethod.class)) {
				HttpMethod annotation = method.getAnnotation(HttpMethod.class);
				methods = this.getMethods(annotation);
				validate = true;
			} else if (clazz.isAnnotationPresent(HttpMethod.class)) {
				HttpMethod annotation = clazz.getAnnotation(HttpMethod.class);
				methods = this.getMethods(annotation);
				validate = true;
			} else if (clazz.getPackage().isAnnotationPresent(HttpMethod.class)) {
				HttpMethod annotation = clazz.getPackage().getAnnotation(HttpMethod.class);
				methods = this.getMethods(annotation);
				validate = true;
			}
			this.addRule(rule, validate);
			this.addRuleValue(rule, methods);
		}

		if (validate) {
			String url = RequestUtils.getRequestUrl(this.getRequest());
			String httpMethod = RequestUtils.getMethod(this.getRequest());

			if ((methods != null) && (methods.size() > 0)) {
				HttpMethodType hm = HttpMethodType.valueOf(httpMethod);
				if (!methods.contains(hm)) {
					WebLog.getInstance().getLog().info("Invalid method for " + url + " found " + httpMethod + " required " + methods);
					return Constants.INVALID_METHOD;
				}
			} else {
				WebLog.getInstance().getLog().info("Methods not found for " + url);
				return Constants.INVALID_METHOD;
			}
		}

		return invocation.invoke();
	}

	private Set<HttpMethodType> getMethods(final HttpMethod annotation) {
		HttpMethodType[] array = annotation.value();
		if ((array != null) && (array.length > 0)) {
			Set<HttpMethodType> set = new HashSet<HttpMethodType>();
			for (HttpMethodType hm : array) {
				set.add(hm);
			}
			return set;
		}
		return this.methods;
	}

	// Setters
	public void setMethods(final String methods) {
		if (ConditionUtils.isNotEmpty(methods)) {
			if (methods.indexOf(HttpMethodInterceptor.METHOD_SEPARATOR) != -1) {
				String[] array = methods.split(HttpMethodInterceptor.METHOD_SEPARATOR);
				for (String s : array) {
					HttpMethodType hm = HttpMethodType.valueOf(s.trim());
					this.methods.add(hm);
				}
			} else {
				this.methods.add(HttpMethodType.valueOf(methods.trim()));
			}
		}
	}

}
