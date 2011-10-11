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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import br.net.woodstock.rockframework.utils.IOUtils;

public class ReadableHttpServletRequest extends HttpServletRequestWrapper {

	private byte[]	bytes;

	public ReadableHttpServletRequest(final HttpServletRequest request) throws IOException {
		super(request);
		this.bytes = IOUtils.toByteArray(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.bytes)));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new DelegateServletInputStream(new ByteArrayInputStream(this.bytes));
	}

}
