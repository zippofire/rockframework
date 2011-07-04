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
package net.woodstock.rockframework.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.ConditionUtils;
import net.woodstock.rockframework.web.filter.AbstractHttpFilter;

public class ResponseHeaderValueFilter extends AbstractHttpFilter {

	private Map<String, String>	headers;

	@Override
	public void init() {
		super.init();
		this.headers = new HashMap<String, String>();
		for (String name : this.getInitParameterNames()) {
			this.headers.put(name, this.getInitParameter(name));
		}
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);

		for (Entry<String, String> entry : this.headers.entrySet()) {
			this.setHeader(response, entry.getKey(), entry.getValue());
		}
	}

	private void setHeader(final HttpServletResponse response, final String name, final String value) {
		if (ConditionUtils.isNotEmpty(value)) {
			response.setHeader(name, value);
		}
	}

}
