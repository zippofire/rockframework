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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.FileUtils;
import net.woodstock.rockframework.utils.IOUtils;

public abstract class ResponseUtils {

	public static final String	CONTENT_DISPOSITION_HEADER		= "Content-Disposition";

	public static final String	DOWNLOAD_CONTENT_TYPE			= "application/force-download";

	public static final String	HTML_CONTENT_TYPE				= "text/html";

	public static final String	TEXT_CONTENT_TYPE				= "text/plain";

	public static final String	XML_CONTENT_TYPE				= "text/xml";

	public static final String	PDF_CONTENT_TYPE				= "application/pdf";

	public static final String	INLINE_CONTENT_DISPOSITION		= "inline";

	public static final String	ATTACHMENT_CONTENT_DISPOSITION	= "attachment";

	private ResponseUtils() {
		//
	}

	public static void forward(final PageContext context, final String url) throws ServletException, IOException {
		Assert.notNull(context, "context");
		Assert.notEmpty(url, "url");
		RequestDispatcher d = context.getServletContext().getRequestDispatcher(url);
		d.forward(context.getRequest(), context.getResponse());
	}

	public static void include(final PageContext context, final String url) throws ServletException, IOException {
		Assert.notNull(context, "context");
		Assert.notEmpty(url, "url");
		RequestDispatcher d = context.getServletContext().getRequestDispatcher(url);
		d.include(context.getRequest(), context.getResponse());
	}

	public static void downloadFile(final HttpServletResponse response, final String file) throws IOException {
		Assert.notNull(response, "response");
		Assert.notEmpty(file, "file");
		File f = new File(file);

		ResponseUtils.downloadFile(response, f);
	}

	public static void downloadFile(final HttpServletResponse response, final File file) throws IOException {
		Assert.notNull(response, "response");
		Assert.notNull(file, "file");
		if (!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType(ResponseUtils.DOWNLOAD_CONTENT_TYPE);
		response.setContentLength(FileUtils.getSize(file));

		response.setHeader(ResponseUtils.CONTENT_DISPOSITION_HEADER, ResponseUtils.getAttachmentContentDisposition(FileUtils.getName(file)));

		InputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();

		IOUtils.copy(input, output);
	}

	public static void downloadFile(final HttpServletResponse response, final URL url) throws IOException {
		Assert.notNull(response, "response");
		Assert.notNull(url, "url");

		response.setContentType(ResponseUtils.DOWNLOAD_CONTENT_TYPE);
		response.setContentLength(FileUtils.getSize(url));

		response.setHeader(ResponseUtils.CONTENT_DISPOSITION_HEADER, ResponseUtils.getAttachmentContentDisposition(FileUtils.getName(url)));

		InputStream input = url.openStream();
		OutputStream output = response.getOutputStream();

		IOUtils.copy(input, output);
	}

	public static String getAttachmentContentDisposition(final String fileName) {
		return "attachment; filename=\"" + fileName + "\"";
	}

	public static String getInLineContentDisposition(final String fileName) {
		return "inline; filename=\"" + fileName + "\"";
	}

}
