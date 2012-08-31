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
package br.net.woodstock.rockframework.web.filter.debug;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.filter.AbstractHttpFilter;
import br.net.woodstock.rockframework.web.util.CachedHttpServletResponse;

public class DebugFilter extends AbstractHttpFilter {

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		if (this.isDebugEnabled(request)) {
			CachedHttpServletResponse debugResponse = new CachedHttpServletResponse(response);
			chain.doFilter(request, debugResponse);
			byte[] responseBytes = debugResponse.getBytes();
			String responseText = this.getResponseText(responseBytes);
			WebLog.getInstance().getLogger().warn(responseText);
			response.getOutputStream().write(responseBytes);
		} else {
			chain.doFilter(request, response);
		}
	}

	@SuppressWarnings("unused")
	protected boolean isDebugEnabled(final HttpServletRequest request) {
		return true;
	}

	@SuppressWarnings("unused")
	protected String getRequestText(final byte[] bytes) throws IOException {
		return new String(bytes);
	}

	@SuppressWarnings("unused")
	protected String getResponseText(final byte[] bytes) throws IOException {
		return new String(bytes);
	}

}
