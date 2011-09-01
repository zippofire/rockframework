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
package br.net.woodstock.rockframework.web.jsp.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.web.jsp.util.RequestTimeListener.RequestDebug;
import br.net.woodstock.rockframework.web.servlet.AbstractHttpServlet;


public class RequestTimeServlet extends AbstractHttpServlet {

	private static final long	serialVersionUID	= 1186351388097961933L;

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		for (RequestDebug r : RequestTimeListener.getRequests()) {
			StringBuilder builder = new StringBuilder();
			builder.append(r.getAddress());
			builder.append("\tStart: ");
			builder.append(r.getStart());
			builder.append("\tEnd: ");
			builder.append(r.getEnd());
			builder.append("\tTime: ");
			builder.append(r.getTime());
			builder.append("\tURL: ");
			builder.append(r.getUrl());
			builder.append("\n");
			response.getWriter().write(builder.toString());
		}
	}

}
