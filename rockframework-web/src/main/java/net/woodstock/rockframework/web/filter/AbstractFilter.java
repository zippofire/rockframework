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
package net.woodstock.rockframework.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

public abstract class AbstractFilter implements Filter {

	private FilterConfig	filterConfig;

	public void destroy() {
		//
	}

	public final void init(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		this.init();
	}

	// Override
	public void init() {
		//
	}

	// Config
	protected FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	// Parameter
	protected String getInitParameter(final String name) {
		return this.filterConfig.getInitParameter(name);
	}
}
