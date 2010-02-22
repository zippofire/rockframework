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
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;

public class AbstractFragment extends Fragment {

	private static final long	serialVersionUID	= 5059064233134210366L;

	public AbstractFragment(final String id, final String markupId, final MarkupContainer markupProvider, final IModel<?> model) {
		super(id, markupId, markupProvider, model);
	}

	public AbstractFragment(final String id, final String markupId, final MarkupContainer markupProvider) {
		super(id, markupId, markupProvider);
	}

	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}

}
