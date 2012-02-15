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
package br.net.woodstock.rockframework.web.jsf.utils;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.net.woodstock.rockframework.web.utils.ResponseUtils;

public abstract class FacesUtils {

	private FacesUtils() {
		//
	}

	// Faces
	public static void addMessage(final String message) {
		FacesContext fc = FacesUtils.getFacesContext();
		fc.addMessage(null, new FacesMessage(message));
	}

	public static void addError(final Exception exception) {
		FacesContext fc = FacesUtils.getFacesContext();
		fc.addMessage(null, new FacesMessage(exception.getMessage()));
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ResourceBundle getResourceBundle(final String name) {
		FacesContext fc = FacesUtils.getFacesContext();
		Application application = fc.getApplication();
		ResourceBundle resource = application.getResourceBundle(fc, name);
		return resource;
	}

	@SuppressWarnings({ "cast", "unchecked" })
	public static <T> T getBean(final String name, final Class<T> clazz) {
		FacesContext fc = FacesUtils.getFacesContext();
		Application app = fc.getApplication();
		ELContext el = fc.getELContext();
		ExpressionFactory factory = app.getExpressionFactory();
		ValueExpression expression = (ValueExpression) factory.createValueExpression(el, "#{" + name + "}", clazz);
		T value = (T) expression.getValue(el);
		return value;
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

	// Utils
	public static void download(final byte[] bytes, final String fileName) throws IOException {
		FacesContext fc = FacesUtils.getFacesContext();
		HttpServletResponse response = FacesUtils.getResponse();
		ResponseUtils.downloadFile(response, bytes, fileName);
		fc.responseComplete();
	}

}
