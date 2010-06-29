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
package net.woodstock.rockframework.web.servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.servlet.AbstractHttpServlet;
import net.woodstock.rockframework.web.utils.ResponseUtils;

public class DownloadServlet extends AbstractHttpServlet {

	private static final long	serialVersionUID	= -2921885361697687087L;

	public static final String	ROOT_PARAMETER		= "root";

	public static final String	PATH_PARAMETER		= "path";

	private String				root;

	public DownloadServlet() {
		super();
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		this.doAll(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		this.doAll(request, response);
	}

	private void doAll(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		this.root = this.getServletConfig().getInitParameter(DownloadServlet.ROOT_PARAMETER);
		if (StringUtils.isEmpty(this.root)) {
			throw new ServletException("Invalid root directory");
		}

		String path = request.getParameter(DownloadServlet.PATH_PARAMETER);
		if (StringUtils.isEmpty(path)) {
			throw new ServletException("Invalid path");
		}
		path = this.root + path;
		ResponseUtils.downloadFile(response, path);
	}

}
