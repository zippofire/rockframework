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

import net.woodstock.rockframework.util.Assert;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class Action<F extends ActionForm> extends org.apache.struts.action.Action {

	@Override
	public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final ServletRequest request, final ServletResponse response) throws Exception {
		Assert.instanceOf(request, HttpServletRequest.class, "request");
		Assert.instanceOf(response, HttpServletResponse.class, "response");

		return this.execute(mapping, form, (HttpServletRequest) request, (HttpServletResponse) response);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ActionForward forward = this.doExecute(mapping, (F) form, request, response);
		return forward;
	}

	protected abstract ActionForward doExecute(final ActionMapping mapping, final F form, final HttpServletRequest request, final HttpServletResponse response) throws Exception;

}
