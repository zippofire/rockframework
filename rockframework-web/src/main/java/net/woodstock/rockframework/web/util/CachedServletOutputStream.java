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
package net.woodstock.rockframework.web.util;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import net.woodstock.rockframework.io.InputOutputStream;

public class CachedServletOutputStream extends ServletOutputStream {

	private InputOutputStream	outputStream;

	public CachedServletOutputStream() {
		super();
		this.outputStream = new InputOutputStream();
	}

	@Override
	public void write(final int b) {
		this.outputStream.write(b);
	}

	// Cache
	public InputStream getCache() {
		return this.outputStream.getInputStream();
	}

}
