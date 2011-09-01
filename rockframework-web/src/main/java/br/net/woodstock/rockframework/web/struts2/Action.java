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
package br.net.woodstock.rockframework.web.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import br.net.woodstock.rockframework.web.struts2.utils.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class Action extends ActionSupport implements Preparable {

	private static final long	serialVersionUID	= 655502050649662609L;

	private static final String	STORED_DATA_SUFFIX	= ".StoredData";

	private String				name;

	public Action() {
		super();
		this.name = this.getClass().getCanonicalName() + Action.STORED_DATA_SUFFIX;
	}

	@Override
	public final void prepare() throws Exception {
		this.prepare(this.getRequest());
	}

	@SuppressWarnings("unused")
	public void prepare(final HttpServletRequest request) throws Exception {
		//
	}

	protected HttpServletRequest getRequest() {
		return Struts2Utils.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return Struts2Utils.getResponse();
	}

	protected HttpSession getSession() {
		return Struts2Utils.getSession();
	}

	// Stored Data
	protected Object[] getStoredData() {
		return (Object[]) this.getSession().getAttribute(this.name);
	}

	protected boolean hasStoredData() {
		Object[] o = (Object[]) this.getSession().getAttribute(this.name);
		if (o != null) {
			return true;
		}
		return false;
	}

	protected void setStoredData(final Object[] data) {
		this.getSession().setAttribute(this.name, data);
	}

}
