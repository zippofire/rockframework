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
package net.woodstock.rockframework.web.jsp.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCountListener implements HttpSessionListener {

	private static int	count	= 0;

	@Override
	public void sessionCreated(final HttpSessionEvent event) {
		SessionCountListener.count++;
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent event) {
		SessionCountListener.count--;
	}

	public static final int getSessionCount() {
		return SessionCountListener.count;
	}

}
