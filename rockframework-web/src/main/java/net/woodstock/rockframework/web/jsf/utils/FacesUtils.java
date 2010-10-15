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
package net.woodstock.rockframework.web.jsf.utils;

import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class FacesUtils {

	private FacesUtils() {
		//
	}

	// Faces
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ResourceBundle getResourceBundle(final String name) {
		FacesContext fc = FacesUtils.getFacesContext();
		Application application = fc.getApplication();
		ResourceBundle resource = application.getResourceBundle(fc, name);
		return resource;
	}

	// Http
	public static HttpServletRequest getRequest() {
		FacesContext fc = FacesUtils.getFacesContext();
		return (HttpServletRequest) fc.getExternalContext().getRequest();
	}

	public static HttpServletResponse getResponse() {
		FacesContext fc = FacesUtils.getFacesContext();
		return (HttpServletResponse) fc.getExternalContext().getResponse();
	}

	public static HttpSession getSession() {
		FacesContext fc = FacesUtils.getFacesContext();
		return (HttpSession) fc.getExternalContext().getSession(true);
	}

	public static ServletContext getServletContext() {
		FacesContext fc = FacesUtils.getFacesContext();
		return (ServletContext) fc.getExternalContext().getContext();
	}

}
