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

import net.woodstock.rockframework.web.utils.RequestUtils;

public class LocalRefererFilter extends NoRefererFilter {

	private String	applicationUrl;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (this.applicationUrl == null) {
			this.applicationUrl = RequestUtils.getApplicationUrl(request);
		}
		if (!this.hasReferer(request)) {
			this.handleNoReferer(response);
			return;
		}
		String url = this.getReferer(request);
		if (!url.startsWith(this.applicationUrl)) {
			this.handleInvalidReferer(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	protected void handleInvalidReferer(HttpServletRequest request, HttpServletResponse response) {
		String url = this.getReferer(request);
		this.getLogger().warn("Invalid referer " + url);
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
