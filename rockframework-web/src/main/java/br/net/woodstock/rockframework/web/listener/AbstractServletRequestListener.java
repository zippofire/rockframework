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
package br.net.woodstock.rockframework.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import br.net.woodstock.rockframework.web.config.WebLog;

public abstract class AbstractServletRequestListener implements ServletRequestListener {

	@Override
	public final void requestDestroyed(final ServletRequestEvent event) {
		this.requestDestroyed(event, (HttpServletRequest) event.getServletRequest());
	}

	@Override
	public final void requestInitialized(final ServletRequestEvent event) {
		this.requestInitialized(event, (HttpServletRequest) event.getServletRequest());
	}

	@SuppressWarnings("unused")
	public void requestDestroyed(final ServletRequestEvent event, final HttpServletRequest request) {
		WebLog.getInstance().getLogger().info("Destroying request");
	}

	@SuppressWarnings("unused")
	public void requestInitialized(final ServletRequestEvent event, final HttpServletRequest request) {
		WebLog.getInstance().getLogger().info("Initializing request");
	}

}
