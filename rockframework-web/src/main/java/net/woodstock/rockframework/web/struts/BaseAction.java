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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class BaseAction<F extends ActionForm> extends Action {

	private static ThreadLocal<ActionMapping>		currentMapping	= new ThreadLocal<ActionMapping>();

	private static ThreadLocal<ActionForm>			currentForm		= new ThreadLocal<ActionForm>();

	private static ThreadLocal<HttpServletRequest>	currentRequest	= new ThreadLocal<HttpServletRequest>();

	private static ThreadLocal<HttpServletResponse>	currentResponse	= new ThreadLocal<HttpServletResponse>();

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
		BaseAction.currentMapping.set(mapping);
		BaseAction.currentForm.set(form);
		BaseAction.currentRequest.set(request);
		BaseAction.currentResponse.set(response);
		ActionForward forward = this.execute();
		return forward;
	}

	protected ActionMapping getMapping() {
		return BaseAction.currentMapping.get();
	}

	@SuppressWarnings("unchecked")
	protected F getForm() {
		return (F) BaseAction.currentForm.get();
	}

	protected HttpServletRequest getRequest() {
		return BaseAction.currentRequest.get();
	}

	protected HttpServletResponse getResponse() {
		return BaseAction.currentResponse.get();
	}

	protected abstract ActionForward execute() throws Exception;

}
