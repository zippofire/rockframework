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
package net.woodstock.rockframework.web.filter.referer;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.web.filter.HttpFilter;

public abstract class RefererFilter extends HttpFilter {

	protected static final int		BAD_REQUEST			= HttpServletResponse.SC_BAD_REQUEST;

	protected static final String	REFERER_HEADER_KEY	= "referer";

	protected String getReferer(final HttpServletRequest request) {
		return request.getHeader(RefererFilter.REFERER_HEADER_KEY);
	}

	@Override
	public final void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		boolean b = this.validateReferer(request);
		if (!b) {
			String url = request.getRequestURI();
			String referer = this.getReferer(request);
			this.getLogger().info("Invalid referer for URL: " + url + " referer: " + referer);
			response.setStatus(RefererFilter.BAD_REQUEST);
			return;
		}
		chain.doFilter(request, response);
	}

	protected abstract boolean validateReferer(HttpServletRequest request);
}
