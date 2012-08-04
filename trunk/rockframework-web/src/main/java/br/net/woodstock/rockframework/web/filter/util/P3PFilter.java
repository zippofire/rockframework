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
package br.net.woodstock.rockframework.web.filter.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.filter.AbstractHttpFilter;
import br.net.woodstock.rockframework.web.utils.RequestUtils;

public class P3PFilter extends AbstractHttpFilter {

	private static final String	INTERNET_EXPLORER_IDENTIFIER	= "MSIE";

	private static final String	P3P_KEY							= "P3P";

	private static final String	P3P_VALUE						= "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"";

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} finally {
			String userAgent = RequestUtils.getUserAgent(request);
			if (this.isInternetExplorer(userAgent)) {
				response.setHeader(P3PFilter.P3P_KEY, P3PFilter.P3P_VALUE);
			}
		}
	}

	private boolean isInternetExplorer(final String userAgent) {
		if (ConditionUtils.isEmpty(userAgent)) {
			return false;
		}
		if (userAgent.indexOf(P3PFilter.INTERNET_EXPLORER_IDENTIFIER) != -1) {
			return true;
		}
		return false;
	}

}
