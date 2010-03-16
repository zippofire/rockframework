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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.ClassUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class SimpleDispatchAction extends DispatchAction {

	private static ThreadLocal<ActionMapping>		currentMapping	= new ThreadLocal<ActionMapping>();

	private static ThreadLocal<ActionForm>			currentForm		= new ThreadLocal<ActionForm>();

	private static ThreadLocal<HttpServletRequest>	currentRequest	= new ThreadLocal<HttpServletRequest>();

	private static ThreadLocal<HttpServletResponse>	currentResponse	= new ThreadLocal<HttpServletResponse>();

	@Override
	protected ActionForward dispatchMethod(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response, final String name) throws Exception {
		try {
			SimpleDispatchAction.currentMapping.set(mapping);
			SimpleDispatchAction.currentForm.set(form);
			SimpleDispatchAction.currentRequest.set(request);
			SimpleDispatchAction.currentResponse.set(response);

			Method m = ClassUtils.getMethod(this.getClass(), name, new Class<?>[] {});

			ActionForward forward = (ActionForward) m.invoke(this);

			return forward;
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if ((throwable != null) && (throwable instanceof Exception)) {
				throw (Exception) throwable;
			}
			throw e;
		}
	}

	protected ActionMapping getMapping() {
		return SimpleDispatchAction.currentMapping.get();
	}

	protected ActionForm getForm() {
		return SimpleDispatchAction.currentForm.get();
	}

	protected HttpServletRequest getRequest() {
		return SimpleDispatchAction.currentRequest.get();
	}

	protected HttpServletResponse getResponse() {
		return SimpleDispatchAction.currentResponse.get();
	}
}
