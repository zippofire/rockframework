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
package net.woodstock.rockframework.web.sso.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.web.filter.AbstractHttpFilter;
import net.woodstock.rockframework.web.sso.SSOConstants;

public abstract class AbstractSSOServerFilter extends AbstractHttpFilter {

	private static final String	DOMAIN_PARAMETER	= "domain";

	private String				domain;

	@Override
	public void init() {
		super.init();
		this.domain = this.getInitParameter(AbstractSSOServerFilter.DOMAIN_PARAMETER);
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		if (request.getParameter(SSOConstants.HASH_PARAMETER) != null) {
			SSOFilterServerHelper.configureHash(request);
		}

		if (request.getParameter(SSOConstants.REDIRECT_PARAMETER) != null) {
			String url = SSOFilterServerHelper.getRedirectURL(request, response, this.domain);
			response.sendRedirect(url);
		} else {
			chain.doFilter(request, response);
		}
	}

}
