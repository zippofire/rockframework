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
package net.woodstock.rockframework.web.struts2.interceptor;

import net.woodstock.rockframework.web.struts2.action.InitializableAction;

import com.opensymphony.xwork2.ActionInvocation;

public class InitializableInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof InitializableAction) {
			InitializableAction a = (InitializableAction) action;
			a.init();
		}
		return invocation.invoke();
	}
}
