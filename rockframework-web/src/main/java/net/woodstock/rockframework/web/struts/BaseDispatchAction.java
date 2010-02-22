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
package net.woodstock.rockframework.web.struts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.ClassUtils;
import net.woodstock.rockframework.web.config.WebLog;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public abstract class BaseDispatchAction extends DispatchAction {

	private static ThreadLocal<ActionMapping>	currentMapping	= new ThreadLocal<ActionMapping>();

	@Override
	public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final ServletRequest request, final ServletResponse response) throws Exception {
		if ((request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Invalid request type");
		}
		if ((response instanceof HttpServletResponse)) {
			throw new IllegalArgumentException("Invalid response type");
		}
		return this.execute(mapping, form, (HttpServletRequest) request, (HttpServletResponse) response);
	}

	@Override
	public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return super.execute(mapping, form, request, response);
	}

	@Override
	protected final ActionForward unspecified(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return mapping.findForward(BaseAction.INPUT);
	}

	@Override
	protected ActionForward dispatchMethod(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response, final String name) throws Exception {
		try {
			BaseDispatchAction.currentMapping.set(mapping);

			Class<?> formClass = ActionForm.class;

			if (form != null) {
				formClass = formClass.getClass();
			}

			Class<?>[] methodTypes = new Class<?>[] { formClass, HttpServletRequest.class, HttpServletResponse.class };

			Method m = ClassUtils.getMethod(this.getClass(), name, methodTypes);

			StrutsResult result = (StrutsResult) m.invoke(this, form, request, response);

			return result.getForward(mapping);
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if ((throwable != null) && (throwable instanceof Exception)) {
				throw (Exception) throwable;
			}
			throw e;
		}
	}

	protected ActionMapping getMapping() {
		return BaseDispatchAction.currentMapping.get();
	}

	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}
}
