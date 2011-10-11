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
package br.net.woodstock.rockframework.web.jsf.spring;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;

import br.net.woodstock.rockframework.domain.spring.AbstractScope;

public class ViewScope extends AbstractScope {

	private static final String	VIEW_SCOPE_KEY	= "br.net.woodstock.rockframework.web.jsf.spring.ViewScope.VIEW_SCOPE_KEY";

	@Override
	@SuppressWarnings("unchecked")
	public Object get(final String name, final ObjectFactory objectFactory) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null) {
				Map<String, Object> attributes = viewRoot.getAttributes();
				Map<String, Object> viewScope = null;
				if (attributes.containsKey(ViewScope.VIEW_SCOPE_KEY)) {
					viewScope = (Map<String, Object>) attributes.get(ViewScope.VIEW_SCOPE_KEY);
				} else {
					viewScope = new HashMap<String, Object>();
					attributes.put(ViewScope.VIEW_SCOPE_KEY, viewScope);
				}

				if (viewScope.containsKey(name)) {
					return viewScope.get(name);
				}

				Object obj = objectFactory.getObject();
				viewScope.put(name, obj);
				return obj;
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object remove(final String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null) {
				Map<String, Object> attributes = viewRoot.getAttributes();
				if (attributes.containsKey(ViewScope.VIEW_SCOPE_KEY)) {
					Map<String, Object> viewScope = (Map<String, Object>) attributes.get(ViewScope.VIEW_SCOPE_KEY);
					return viewScope.remove(name);
				}
			}
		}

		return null;
	}

}
