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
package br.net.woodstock.rockframework.web.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.utils.IOUtils;
import br.net.woodstock.rockframework.web.util.ReadableHttpServletRequest;


public class HttpRequestWriterFilter extends AbstractHttpFilter {

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		HttpServletRequestWrapper wrapper = new ReadableHttpServletRequest(request);
		InputStream inputStream = wrapper.getInputStream();
		String body = IOUtils.toString(inputStream);
		this.printBody(body);
		chain.doFilter(wrapper, response);
	}

	protected void printBody(final String body) {
		System.out.println(body);
	}

}
