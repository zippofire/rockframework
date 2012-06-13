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
package br.net.woodstock.rockframework.web.struts2.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.struts2.AbstractInterceptor;
import br.net.woodstock.rockframework.web.struts2.Struts2Constants;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;

public class ExceptionHandlerInterceptor extends AbstractInterceptor {

	private static final long		serialVersionUID	= 1L;

	private static final String		SEPARATOR			= ",";

	private static final String		DEFAULT_LOG_NAME	= "br.net.woodstock.rockframework.web";

	private String					logName;

	private String					exceptions;

	private List<Class<Exception>>	exceptionsList;

	private Log						log;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void init() {
		if (ConditionUtils.isNotEmpty(this.logName)) {
			this.log = LogFactory.getLog(this.logName);
		} else {
			this.log = LogFactory.getLog(ExceptionHandlerInterceptor.DEFAULT_LOG_NAME);
		}

		this.exceptionsList = new ArrayList<Class<Exception>>();
		if (ConditionUtils.isNotEmpty(this.exceptions)) {
			String[] array = this.exceptions.split(ExceptionHandlerInterceptor.SEPARATOR);
			for (String s : array) {
				try {
					Class c = Class.forName(s.trim());
					if (Exception.class.isAssignableFrom(c)) {
						this.exceptionsList.add(c);
					}
				} catch (ClassNotFoundException e) {
					this.log.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		try {
			String result = invocation.invoke();
			return result;
		} catch (Exception e) {
			if (this.isHandled(e)) {
				Object action = invocation.getAction();
				if (action instanceof ValidationAware) {
					ValidationAware va = (ValidationAware) action;
					Throwable cause = e.getCause();
					if (cause != null) {
						while (cause.getCause() != null) {
							cause = cause.getCause();
						}
						va.addActionError(cause.getMessage());
					} else {
						va.addActionError(e.getMessage());
					}
				}
				this.log.warn(e.getMessage(), e);
				return Struts2Constants.ERROR;
			}
			throw e;
		}
	}

	protected boolean isHandled(final Exception e) {
		Class<? extends Exception> clazz = e.getClass();
		for (Class<Exception> c : this.exceptionsList) {
			if (c.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	public void setLogName(final String logName) {
		this.logName = logName;
	}

	public void setExceptions(final String exceptions) {
		this.exceptions = exceptions;
	}

}
