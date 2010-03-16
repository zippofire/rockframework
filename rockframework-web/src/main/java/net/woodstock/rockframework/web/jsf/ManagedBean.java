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
package net.woodstock.rockframework.web.jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class ManagedBean implements Serializable {

	private static final long	serialVersionUID	= 184189110204186026L;

	public ManagedBean() {
		super();
	}

	// Faces
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	// Http
	protected HttpServletRequest getRequest() {
		FacesContext fc = this.getFacesContext();
		return (HttpServletRequest) fc.getExternalContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		FacesContext fc = this.getFacesContext();
		return (HttpServletResponse) fc.getExternalContext().getResponse();
	}

	protected HttpSession getSession() {
		FacesContext fc = this.getFacesContext();
		return (HttpSession) fc.getExternalContext().getSession(false);
	}

	protected ServletContext getServletContext() {
		FacesContext fc = this.getFacesContext();
		return (ServletContext) fc.getExternalContext().getContext();
	}

}
