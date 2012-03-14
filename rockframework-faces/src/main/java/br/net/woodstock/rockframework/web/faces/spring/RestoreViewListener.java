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

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RestoreViewListener implements HttpSessionActivationListener {

	@Override
	public void sessionDidActivate(final HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext servletContext = session.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		AutowireCapableBeanFactory autowireFactory = webAppContext.getAutowireCapableBeanFactory();

		Enumeration<String> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();

			if (webAppContext.containsBean(name)) {
				Object bean = session.getAttribute(name);

				bean = autowireFactory.configureBean(bean, name);
				session.setAttribute(name, bean);
			}
		}
	}

	@Override
	public void sessionWillPassivate(final HttpSessionEvent event) {
		//
	}

}
