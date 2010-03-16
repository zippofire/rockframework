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
package net.woodstock.rockframework.web.struts2.util;


import net.woodstock.rockframework.web.struts2.Interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

public class AroundInterceptor extends Interceptor {

	private static final long	serialVersionUID	= 1L;

	private PreResultListener	listener;

	public AroundInterceptor() {
		super();
		this.listener = new AroundInterceptorListener();
	}

	public String intercept(final ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof AroundAction) {
			AroundAction a = (AroundAction) action;
			invocation.addPreResultListener(this.listener);
			a.before();
			return invocation.invoke();
		}
		return invocation.invoke();
	}

	class AroundInterceptorListener implements PreResultListener {

		public void beforeResult(final ActionInvocation invocation, final String resultCode) {
			Object action = invocation.getAction();
			AroundAction a = (AroundAction) action;
			try {
				a.after();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

}
