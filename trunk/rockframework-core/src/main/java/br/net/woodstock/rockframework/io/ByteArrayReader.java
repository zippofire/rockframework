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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ByteArrayReader extends Reader {

	private ByteArrayInputStream	inputStream;

	private InputStreamReader		reader;

	public ByteArrayReader(final byte[] buffer) {
		super();
		this.inputStream = new ByteArrayInputStream(buffer);
		this.reader = new InputStreamReader(this.inputStream);
	}

	@Override
	public int read(final char[] cbuf, final int offset, final int length) throws IOException {
		return this.reader.read(cbuf, offset, length);
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

}
