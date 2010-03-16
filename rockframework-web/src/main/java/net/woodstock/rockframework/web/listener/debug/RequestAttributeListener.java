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

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

import net.woodstock.rockframework.web.config.WebLog;

public class RequestAttributeListener implements ServletRequestAttributeListener {

	public void attributeAdded(final ServletRequestAttributeEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nAdded");
		builder.append("\nSource  : " + event.getSource().getClass().getCanonicalName());
		builder.append("\nContext : " + event.getServletContext().getServletContextName());
		builder.append("\nName    : " + event.getName());
		builder.append("\nValue   : " + event.getValue());
		builder.append("\nClass   : " + event.getValue().getClass().getCanonicalName());
		WebLog.getInstance().getLog().info(builder.toString());
	}

	public void attributeRemoved(final ServletRequestAttributeEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nAdded");
		builder.append("\nSource  : " + event.getSource().getClass().getCanonicalName());
		builder.append("\nContext : " + event.getServletContext().getServletContextName());
		builder.append("\nName    : " + event.getName());
		builder.append("\nValue   : " + event.getValue());
		builder.append("\nClass   : " + event.getValue().getClass().getCanonicalName());
		WebLog.getInstance().getLog().info(builder.toString());
	}

	public void attributeReplaced(final ServletRequestAttributeEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nAdded");
		builder.append("\nSource  : " + event.getSource().getClass().getCanonicalName());
		builder.append("\nContext : " + event.getServletContext().getServletContextName());
		builder.append("\nName    : " + event.getName());
		builder.append("\nValue   : " + event.getValue());
		builder.append("\nClass   : " + event.getValue().getClass().getCanonicalName());
		WebLog.getInstance().getLog().info(builder.toString());
	}

}
