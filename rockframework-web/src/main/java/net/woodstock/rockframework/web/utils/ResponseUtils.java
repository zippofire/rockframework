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
package net.woodstock.rockframework.web.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import net.woodstock.rockframework.utils.FileUtils;
import net.woodstock.rockframework.utils.IOUtils;

public abstract class ResponseUtils {

	public static final String	DOWNLOAD_CONTENT_TYPE	= "application/force-download";

	public static final String	HTML_CONTENT_TYPE		= "text/html";

	public static final String	TEXT_CONTENT_TYPE		= "text/plain";

	public static final String	XML_CONTENT_TYPE		= "text/xml";

	private ResponseUtils() {
		//
	}

	public static void redirect(PageContext context, String url) throws ServletException, IOException {
		RequestDispatcher d = context.getServletContext().getRequestDispatcher(url);
		d.forward(context.getRequest(), context.getResponse());
	}

	public static void downloadFile(HttpServletResponse response, String file) throws IOException {
		response.setContentType(FileUtils.getContentType(file));
		response.setContentLength(FileUtils.getContentLength(file));

		response.setHeader("Content-type", ResponseUtils.DOWNLOAD_CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + FileUtils.getFileName(file)
				+ "\"");
		response.setHeader("Content-Length", Integer.toString(FileUtils.getContentLength(file)));

		InputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();

		IOUtils.copy(input, output, true);
	}

	public static void copyFile(HttpServletResponse response, String file) throws IOException {
		response.setContentType(FileUtils.getContentType(file));
		response.setContentLength(FileUtils.getContentLength(file));

		response.setHeader("Content-Length", Integer.toString(FileUtils.getContentLength(file)));

		InputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();
		IOUtils.copy(input, output, true);
	}

}
