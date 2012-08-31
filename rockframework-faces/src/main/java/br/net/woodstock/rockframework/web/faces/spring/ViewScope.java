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

import java.util.Iterator;
import java.util.Map.Entry;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;

import br.net.woodstock.rockframework.domain.spring.AbstractScope;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;

public class ViewScope extends AbstractScope {

	protected static final String	VIEW_SCOPE_KEY	= "br.net.woodstock.rockframework.web.faces.spring.ViewScope.VIEW_SCOPE";

	public static final String		VIEW_SCOPE		= "view";

	@Override
	public Object get(final String name, final ObjectFactory<?> objectFactory) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null) {
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				HttpSession session = request.getSession();
				String viewId = viewRoot.getViewId();
				ViewBean viewBean = (ViewBean) session.getAttribute(ViewScope.VIEW_SCOPE_KEY);

				if (viewBean == null) {
					WebLog.getInstance().getLogger().debug("Creating new PageViewBean");
					viewBean = new ViewBean(viewId);
					session.setAttribute(ViewScope.VIEW_SCOPE_KEY, viewBean);
				} else {
					viewBean.setViewId(viewId);

					Iterator<Entry<String, ViewAttribute>> iterator = viewBean.getAttributes().entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, ViewAttribute> entry = iterator.next();
						ViewAttribute viewAttribute = entry.getValue();
						boolean remove = true;
						for (String grantedViewId : viewAttribute.getViews()) {
							if (grantedViewId.equals(viewId)) {
								remove = false;
								break;
							}
						}
						if (remove) {
							WebLog.getInstance().getLogger().debug("Removing " + entry.getKey() + " from view " + viewId);
							iterator.remove();
						}
					}

				}

				if (viewBean.getAttributes().containsKey(name)) {
					WebLog.getInstance().getLogger().debug("Getting " + name + " for view " + viewId);
					ViewAttribute viewAttribute = viewBean.getAttributes().get(name);
					return viewAttribute.getValue();
				}

				WebLog.getInstance().getLogger().debug("Creating " + name + " for view " + viewId);
				Object obj = objectFactory.getObject();
				String[] views = null;

				if ((obj != null) && (obj.getClass().isAnnotationPresent(ViewConfig.class))) {
					ViewConfig viewConfig = obj.getClass().getAnnotation(ViewConfig.class);
					views = viewConfig.views();
				}

				if (ConditionUtils.isEmpty(views)) {
					views = new String[] { viewId };
				}

				ViewAttribute viewAttribute = new ViewAttribute(views, name, obj);
				viewBean.getAttributes().put(name, viewAttribute);

				return obj;
			}
		}
		return null;
	}

	@Override
	public String getConversationId() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			return session.getId();
		}
		return null;
	}

	@Override
	public Object remove(final String name) {
		WebLog.getInstance().getLogger().info("Removing " + name);
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			ViewBean bean = (ViewBean) session.getAttribute(ViewScope.VIEW_SCOPE_KEY);
			if (bean != null) {
				bean.getAttributes().remove(name);
			}
		}
		return null;
	}

	@Override
	public void registerDestructionCallback(final String name, final Runnable callback) {
		WebLog.getInstance().getLogger().debug("registerDestructionCallback " + name + " => " + callback);
	}

}