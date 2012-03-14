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
package br.net.woodstock.rockframework.web.faces.spring;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.domain.spring.AbstractScope;

public class PageViewScope extends AbstractScope {

	private static final String	PAGE_VIEW_SCOPE_KEY	= "br.net.woodstock.rockframework.web.jsf.spring.PageViewScope.PAGE_VIEW_SCOPE";

	public static final String	PAGE_VIEW_SCOPE		= "pageView";

	@Override
	public Object get(final String name, final ObjectFactory<?> objectFactory) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null) {
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				HttpSession session = request.getSession();
				String viewId = viewRoot.getViewId();
				PageView pageView = (PageView) session.getAttribute(PageViewScope.PAGE_VIEW_SCOPE_KEY);
				if (pageView != null) {
					if (!pageView.getViewId().equals(viewId)) {
						CoreLog.getInstance().getLog().fine("Replacing view " + pageView.getViewId() + " by " + viewId);
						pageView.setViewId(viewId);
						pageView.getAttributes().clear();
					}
				} else {
					pageView = new PageView(viewId);
					session.setAttribute(PageViewScope.PAGE_VIEW_SCOPE_KEY, pageView);
				}

				if (pageView.getAttributes().containsKey(name)) {
					CoreLog.getInstance().getLog().fine("Getting " + name + " for view " + viewId);
					return pageView.getAttributes().get(name);
				}

				CoreLog.getInstance().getLog().fine("Creating " + name + " for view " + viewId);
				Object obj = objectFactory.getObject();
				pageView.getAttributes().put(name, obj);
				return obj;
			}
		}
		return null;
	}

	@Override
	public Object remove(final String name) {
		CoreLog.getInstance().getLog().info("Removing " + name);
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null) {
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				HttpSession session = request.getSession();
				String viewId = viewRoot.getViewId();
				PageView pageView = (PageView) session.getAttribute(PageViewScope.PAGE_VIEW_SCOPE_KEY);
				if (pageView == null) {
					pageView = new PageView(viewId);
					session.removeAttribute(name);
				}
			}
		}

		return null;
	}

	@Override
	public void registerDestructionCallback(final String name, final Runnable callback) {
		//
	}

}
