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
package br.net.woodstock.rockframework.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class InputOutputStream extends OutputStream {

	private ByteArrayOutputStream	outputStream;

	public InputOutputStream() {
		super();
		this.outputStream = new ByteArrayOutputStream();
	}

	@Override
	public void write(final int b) {
		this.outputStream.write(b);
	}

	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.outputStream.toByteArray());
	}
}
