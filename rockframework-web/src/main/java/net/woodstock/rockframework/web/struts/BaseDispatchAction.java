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

import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.ClassUtils;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public abstract class BaseDispatchAction extends DispatchAction {

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form, ServletRequest request,
			ServletResponse response) throws Exception {
		return this.execute(mapping, form, (HttpServletRequest) request, (HttpServletResponse) response);
	}

	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return super.execute(mapping, form, request, response);
	}

	@Override
	protected final ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(BaseAction.INPUT);
	}

	@Override
	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		try {
			Class<?> formClass = ActionForm.class;

			if (form != null) {
				formClass = formClass.getClass();
			}

			Class<?>[] methodTypes = new Class<?>[] { formClass, HttpServletRequest.class,
					HttpServletResponse.class };

			Method m = ClassUtils.getMethod(this.getClass(), name, methodTypes);

			Object o = m.invoke(this, form, request, response);

			if (o instanceof StrutsResult) {
				StrutsResult result = (StrutsResult) o;
				return result.getForward(mapping);
			} else if (o instanceof String) {
				return mapping.findForward((String) o);
			}

			throw new IllegalArgumentException("Invalid result type " + o.getClass().getCanonicalName());
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if ((throwable != null) && (throwable instanceof Exception)) {
				throw (Exception) throwable;
			}
			throw e;
		}
	}
}
