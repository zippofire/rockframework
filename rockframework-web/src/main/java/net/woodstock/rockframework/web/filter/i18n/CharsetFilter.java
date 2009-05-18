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
package net.woodstock.rockframework.web.filter.i18n;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.filter.HttpFilter;
import net.woodstock.rockframework.web.wrapper.BufferedServletResponseWrapper;
import net.woodstock.rockframework.web.wrapper.ServletOutputStreamWrapper;

public class CharsetFilter extends HttpFilter {

	public static final String	FROM_PARAMETER	= "from";

	private String				from;

	private Charset				charset;

	@Override
	public void doInit() {
		this.from = this.getInitParameter(CharsetFilter.FROM_PARAMETER);
		this.charset = Charset.forName(this.from);
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		BufferedServletResponseWrapper responseWrapper = new BufferedServletResponseWrapper(response);

		this.getLogger().debug("Filtering " + request.getRequestURI());
		this.getLogger().debug("Send request to next chain");
		chain.doFilter(request, responseWrapper);

		this.getLogger().debug("Getting response content");
		ServletOutputStreamWrapper wrapper = responseWrapper.getOutputStreamWrapper();

		this.getLogger().debug("Convert charset from response");
		String text = wrapper.getOutputText();
		String content = StringUtils.convertCharset(this.charset, text);

		this.getLogger().debug("Writing converted text to output");
		response.getOutputStream().write(content.getBytes());
	}
}
