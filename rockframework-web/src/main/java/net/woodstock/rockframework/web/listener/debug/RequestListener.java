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

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import net.woodstock.rockframework.web.config.WebLog;

public class RequestListener implements ServletRequestListener {

	public void requestDestroyed(final ServletRequestEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nRequest Destroyed");
		builder.append("\nURL  : " + this.getUrl(event.getServletRequest()));
		WebLog.getInstance().getLog().info(builder.toString());
	}

	public void requestInitialized(final ServletRequestEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nRequest Initialized");
		builder.append("\nURL  : " + this.getUrl(event.getServletRequest()));
		WebLog.getInstance().getLog().info(builder.toString());
	}

	private String getUrl(final ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		return httpServletRequest.getRequestURI();
	}

}
