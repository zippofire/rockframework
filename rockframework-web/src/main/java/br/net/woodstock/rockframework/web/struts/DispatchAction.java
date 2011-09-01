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
package br.net.woodstock.rockframework.web.struts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.net.woodstock.rockframework.utils.ClassUtils;

public abstract class DispatchAction extends AbstractDispatchAction {

	@Override
	protected ActionForward dispatchMethod(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response, final String name) throws Exception {
		try {

			Class<?> formClass = ActionForm.class;

			if (form != null) {
				formClass = formClass.getClass();
			}

			Class<?>[] methodTypes = new Class<?>[] { ActionMapping.class, formClass, HttpServletRequest.class, HttpServletResponse.class };

			Method m = ClassUtils.getMethod(this.getClass(), name, methodTypes);

			ActionForward forward = (ActionForward) m.invoke(this, new Object[] { mapping, form, request, response });

			return forward;
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if ((throwable != null) && (throwable instanceof Exception)) {
				throw (Exception) throwable;
			}
			throw e;
		}
	}
}
