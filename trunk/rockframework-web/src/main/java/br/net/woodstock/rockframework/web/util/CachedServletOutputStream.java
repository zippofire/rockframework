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

import javax.servlet.ServletOutputStream;

import br.net.woodstock.rockframework.io.InputOutputStream;

public class CachedServletOutputStream extends ServletOutputStream {

	private InputOutputStream	inputOutputStream;

	public CachedServletOutputStream() {
		super();
		this.inputOutputStream = new InputOutputStream();
	}

	@Override
	public void write(final int b) throws IOException {
		this.inputOutputStream.write(b);
	}

	// Cache
	public InputStream getInputStream() {
		return this.inputOutputStream.getInputStream();
	}

	public byte[] getBytes() {
		return this.inputOutputStream.getBytes();
	}

}
