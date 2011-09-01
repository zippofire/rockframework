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
package br.net.woodstock.rockframework.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CachedHttpServletResponse extends HttpServletResponseWrapper {

	private CachedServletOutputStream	outputStream;

	private PrintWriter					writer;

	public CachedHttpServletResponse(final HttpServletResponse response) {
		super(response);
		this.outputStream = new CachedServletOutputStream();
		this.writer = new PrintWriter(this.outputStream);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return this.outputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return this.writer;
	}

	// Cache
	public InputStream getCache() {
		return this.outputStream.getCache();
	}

}
