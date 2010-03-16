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
package net.woodstock.rockframework.web.listener.debug;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.woodstock.rockframework.web.config.WebLog;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(final HttpSessionEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nCreated");
		builder.append("\nID      : " + event.getSession().getId());
		builder.append("\nSource  : " + event.getSource().getClass().getCanonicalName());
		builder.append("\nContext : " + event.getSession().getServletContext().getServletContextName());
		WebLog.getInstance().getLog().info(builder.toString());
	}

	public void sessionDestroyed(final HttpSessionEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nDestroyed");
		builder.append("\nID      : " + event.getSession().getId());
		builder.append("\nSource  : " + event.getSource().getClass().getCanonicalName());
		builder.append("\nContext : " + event.getSession().getServletContext().getServletContextName());
		WebLog.getInstance().getLog().info(builder.toString());
	}

}
