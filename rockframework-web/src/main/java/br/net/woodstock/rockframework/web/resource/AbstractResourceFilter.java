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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.web.filter.AbstractHttpFilter;


public abstract class AbstractResourceFilter extends AbstractHttpFilter {

	private ResourceManager	resourceManager;

	public AbstractResourceFilter() {
		super();
	}

	public void setResourceManager(final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		Assert.notNull(this.resourceManager, "resourceManager");

		this.resourceManager.manage(request, response);
	}

}
