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
 * GNU General Public License for more details.es
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.timestamp.web;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.security.config.SecurityLog;
import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.IOUtils;

public abstract class AbstractTimeStampServlet extends HttpServlet {

	private static final long	serialVersionUID		= -7125952867120963318L;

	public static final String	REQUEST_CONTENT_TYPE	= "application/timestamp-query";

	public static final String	RESPONSE_CONTENT_TYPE	= "application/timestamp-reply";

	public static final String	ERROR_CONTENT_TYPE		= "text/plain";

	public static final String	CONTENT_ENCODING_HEADER	= "Content-Encoding";

	public static final String	BINARY_CONTENT_ENCODING	= "binary";

	public static final String	BASE64_CONTENT_ENCODING	= "base64";

	protected abstract TimeStampServer getTimeStampServer();

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		this.doAll(req, resp);
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		this.doAll(req, resp);
	}

	protected void doAll(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		try {
			String requestContentType = req.getContentType();
			if (!AbstractTimeStampServlet.REQUEST_CONTENT_TYPE.equalsIgnoreCase(requestContentType)) {
				SecurityLog.getInstance().getLogger().warn("Invalid request content-type " + requestContentType);
			}

			boolean base64 = false;
			byte[] requestBytes = null;
			if (AbstractTimeStampServlet.CONTENT_ENCODING_HEADER.equalsIgnoreCase(req.getHeader(AbstractTimeStampServlet.BASE64_CONTENT_ENCODING))) {
				base64 = true;
				requestBytes = Base64Utils.fromBase64(IOUtils.toByteArray(req.getInputStream()));
			} else {
				requestBytes = IOUtils.toByteArray(req.getInputStream());
			}

			byte[] timestampBytes = this.getTimeStampServer().getTimeStamp(requestBytes);
			byte[] responseBytes = null;
			String contentEncoding = null;

			if (base64) {
				contentEncoding = AbstractTimeStampServlet.BASE64_CONTENT_ENCODING;
				responseBytes = Base64Utils.toBase64(timestampBytes);
			} else {
				contentEncoding = AbstractTimeStampServlet.BINARY_CONTENT_ENCODING;
				responseBytes = timestampBytes;
			}

			resp.setContentType(AbstractTimeStampServlet.RESPONSE_CONTENT_TYPE);
			resp.setHeader(AbstractTimeStampServlet.CONTENT_ENCODING_HEADER, contentEncoding);
			resp.setContentLength(responseBytes.length);
			resp.getOutputStream().write(responseBytes);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType(AbstractTimeStampServlet.ERROR_CONTENT_TYPE);
			e.printStackTrace(new PrintStream(resp.getOutputStream()));
		}
	}
}
