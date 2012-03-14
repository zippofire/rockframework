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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public abstract class ActionForm extends ValidatorForm {

	private static final long	serialVersionUID	= -5299608278184306255L;

	@Override
	public final void reset(final ActionMapping mapping, final ServletRequest request) {
		this.reset();
	}

	@Override
	public final void reset(final ActionMapping mapping, final HttpServletRequest request) {
		this.reset();
	}

	public void reset() {
		//
	}

}
