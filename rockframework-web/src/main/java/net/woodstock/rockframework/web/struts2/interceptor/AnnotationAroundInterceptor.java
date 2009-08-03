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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.woodstock.rockframework.web.struts2.annotation.After;
import net.woodstock.rockframework.web.struts2.annotation.Around;
import net.woodstock.rockframework.web.struts2.annotation.Before;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

public class AnnotationAroundInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= 1L;

	private PreResultListener	listener;

	public AnnotationAroundInterceptor() {
		super();
		this.listener = new AnnotationAroundInterceptorListener();
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		Class<?> clazz = action.getClass();
		if (clazz.isAnnotationPresent(Around.class)) {
			invocation.addPreResultListener(this.listener);
			this.executeBeforeMethod(action);
			return invocation.invoke();
		}
		return invocation.invoke();
	}

	private void executeBeforeMethod(Object action) throws IllegalAccessException {
		Class<?> clazz = action.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Before.class)) {
				try {
					method.invoke(action, new Object[] {});
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getCause());
				}
			}
		}
	}

	class AnnotationAroundInterceptorListener implements PreResultListener {

		public void beforeResult(ActionInvocation invocation, String resultCode) {
			Object action = invocation.getAction();
			Class<?> clazz = action.getClass();
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(After.class)) {
					try {
						method.invoke(action, new Object[] {});
					} catch (IllegalArgumentException e) {
						new RuntimeException(e);
					} catch (IllegalAccessException e) {
						new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e.getCause());
					}
				}
			}
		}

	}
}
