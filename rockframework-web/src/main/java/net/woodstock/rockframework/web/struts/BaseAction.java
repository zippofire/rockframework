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

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class BaseAction<F extends ActionForm> extends Action {

	public static final String					ERROR			= "error";

	public static final String					INDEX			= "index";

	public static final String					INPUT			= "input";

	public static final String					LOGIN			= "login";

	public static final String					LOGOUT			= "logout";

	public static final String					NO_ACCESS		= "no-access";

	public static final String					NO_LOGIN		= "no-login";

	public static final String					SUCCESS			= "success";

	private static ThreadLocal<ActionMapping>	currentMapping	= new ThreadLocal<ActionMapping>();

	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form, ServletRequest request,
			ServletResponse response) throws Exception {
		return this.execute(mapping, form, (HttpServletRequest) request, (HttpServletResponse) response);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseAction.currentMapping.set(mapping);
		StrutsResult result = this.execute((F) form, request, response);
		return result.getForward(mapping);
	}

	protected ActionMapping getMapping() {
		return BaseAction.currentMapping.get();
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	protected abstract StrutsResult execute(F form, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
