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
package br.net.woodstock.rockframework.web.wicket;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

public class AbstractPage extends WebPage {

	public AbstractPage() {
		super();
	}

	public AbstractPage(final IModel<?> model) {
		super(model);
	}

	public AbstractPage(final IPageMap pageMap, final IModel<?> model) {
		super(pageMap, model);
	}

	public AbstractPage(final IPageMap pageMap, final PageParameters parameters) {
		super(pageMap, parameters);
	}

	public AbstractPage(final IPageMap pageMap) {
		super(pageMap);
	}

	public AbstractPage(final PageParameters parameters) {
		super(parameters);
	}

}
