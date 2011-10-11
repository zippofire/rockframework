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
package br.net.woodstock.rockframework.web.resource;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.web.servlet.AbstractHttpServlet;

public abstract class AbstractResourceServlet extends AbstractHttpServlet {

	private static final long	serialVersionUID	= 5332694868752923858L;

	private ResourceManager		resourceManager;

	public AbstractResourceServlet() {
		super();
	}

	public void setResourceManager(final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		Assert.notNull(this.resourceManager, "resourceManager");

		this.resourceManager.manage(request, response);
	}

}
