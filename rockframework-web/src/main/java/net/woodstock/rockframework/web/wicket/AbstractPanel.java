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
package net.woodstock.rockframework.web.wicket;

import net.woodstock.rockframework.web.config.WebLog;

import org.apache.commons.logging.Log;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class AbstractPanel extends Panel {

	private static final long	serialVersionUID	= 3292253766011511213L;

	public AbstractPanel(final String id, final IModel<?> model) {
		super(id, model);
	}

	public AbstractPanel(final String id) {
		super(id);
	}

	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}

}
