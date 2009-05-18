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

import net.woodstock.rockframework.web.config.WebMessage;
import net.woodstock.rockframework.web.servlet.BaseServlet;
import net.woodstock.rockframework.web.utils.ResponseUtils;

public class DownloadServlet extends BaseServlet {

	private static final long	serialVersionUID		= -2921885361697687087L;

	private static final String	MESSAGE_INVALID_CHROOT	= "jsp.exception.invalidChroot";

	private static final String	MESSAGE_INVALID_PATH	= "jsp.exception.invalidPath";

	private String				chroot;

	public DownloadServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		this.chroot = this.getServletConfig().getInitParameter("chroot");
		if (this.chroot == null) {
			throw new ServletException(WebMessage.getInstance().getMessage(
					DownloadServlet.MESSAGE_INVALID_CHROOT));
		}
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
		String path = request.getParameter("path");
		if (path == null) {
			throw new ServletException(WebMessage.getInstance().getMessage(
					DownloadServlet.MESSAGE_INVALID_PATH));
		}
		path = this.chroot + path;
		ResponseUtils.downloadFile(response, path);
	}

}
