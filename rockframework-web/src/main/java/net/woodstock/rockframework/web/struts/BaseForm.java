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
import javax.servlet.http.HttpServletRequest;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract class BaseForm extends ActionForm {

	private static final long	serialVersionUID	= -5299608278184306255L;

	@Override
	public final void reset(ActionMapping mapping, ServletRequest request) {
		this.reset();
	}

	@Override
	public final void reset(ActionMapping mapping, HttpServletRequest request) {
		this.reset();
	}

	public abstract void reset();

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
