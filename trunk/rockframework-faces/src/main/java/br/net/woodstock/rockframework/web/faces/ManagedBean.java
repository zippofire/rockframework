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
package br.net.woodstock.rockframework.web.faces;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.net.woodstock.rockframework.web.faces.utils.FacesUtils;


public abstract class ManagedBean implements Serializable {

	private static final long	serialVersionUID	= 184189110204186026L;

	public ManagedBean() {
		super();
	}

	// Faces
	protected void addMessage(final String message) {
		FacesContext context = FacesUtils.getFacesContext();
		context.addMessage(null, new FacesMessage(message));
	}

	protected FacesContext getFacesContext() {
		return FacesUtils.getFacesContext();
	}

	protected String getMessage(final String resource, final String key) {
		ResourceBundle rb = FacesUtils.getResourceBundle(resource);
		if (rb != null) {
			return rb.getString(key);
		}
		return null;
	}

	// Http
	protected HttpServletRequest getRequest() {
		return FacesUtils.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return FacesUtils.getResponse();
	}

	protected HttpSession getSession() {
		return FacesUtils.getSession();
	}

	protected ServletContext getServletContext() {
		return FacesUtils.getServletContext();
	}

}
