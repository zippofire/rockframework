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
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.StringUtils;

public class RegexRefererFilter extends NoRefererFilter {

	public static final String	REGEX_PARAMETER	= "regex";

	private String				regex;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (this.regex == null) {
			this.regex = this.getInitParameter(RegexRefererFilter.REGEX_PARAMETER);
		}
		if (StringUtils.isEmpty(this.regex)) {
			throw new ServletException("Parameter '" + RegexRefererFilter.REGEX_PARAMETER + "' must be set");
		}
		if (!this.hasReferer(request)) {
			this.handleNoReferer(response);
			return;
		}
		String url = this.getReferer(request);
		if (!Pattern.matches(this.regex, url)) {
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
