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
import net.woodstock.rockframework.web.listener.BaseListener;

public class SessionListener extends BaseListener implements HttpSessionListener {

	private static int	count;

	public void sessionCreated(final HttpSessionEvent se) {
		SessionListener.count++;
		WebLog.getInstance().getLog().info("Created session for class " + se.getSource().getClass().getName());
		WebLog.getInstance().getLog().info("Active sessions " + SessionListener.count);
	}

	public void sessionDestroyed(final HttpSessionEvent se) {
		SessionListener.count--;
		WebLog.getInstance().getLog().info("Destroyed session for class " + se.getSource().getClass().getName());
		WebLog.getInstance().getLog().info("Active sessions " + SessionListener.count);
	}

	public static int getSessionCount() {
		return SessionListener.count;
	}

}
