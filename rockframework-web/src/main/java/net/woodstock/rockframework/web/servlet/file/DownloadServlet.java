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
import net.woodstock.rockframework.web.servlet.BaseServlet;
import net.woodstock.rockframework.web.utils.ResponseUtils;

public class DownloadServlet extends BaseServlet {

	private static final long	serialVersionUID		= -2921885361697687087L;

	public static final String	CHROOT_CONTEXT_PARAM	= "chroot";

	public static final String	PATH_PARAM				= "path";

	private String				chroot;

	public DownloadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doAll(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doAll(request, response);
	}

	private void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.chroot = this.getServletConfig().getInitParameter(CHROOT_CONTEXT_PARAM);
		if (StringUtils.isEmpty(this.chroot)) {
			throw new ServletException("Invalid root directory");
		}

		String path = request.getParameter(PATH_PARAM);
		if (StringUtils.isEmpty(path)) {
			throw new ServletException("Invalid path");
		}
		path = this.chroot + path;
		ResponseUtils.downloadFile(response, path);
	}

}
