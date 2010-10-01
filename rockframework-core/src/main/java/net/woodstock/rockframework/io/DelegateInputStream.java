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
package net.woodstock.rockframework.io;

import java.io.IOException;
import java.io.InputStream;

public class DelegateInputStream extends InputStream {

	private InputStream	inputStream;

	public DelegateInputStream(final InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	@Override
	public long skip(final long n) throws IOException {
		return this.inputStream.skip(n);
	}

	@Override
	public int available() throws IOException {
		return this.inputStream.available();
	}

	@Override
	public void mark(final int readlimit) {
		this.inputStream.mark(readlimit);
	}

	@Override
	public int read() throws IOException {
		return this.inputStream.read();
	}

	@Override
	public void reset() throws IOException {
		this.inputStream.reset();
	}

	@Override
	public boolean markSupported() {
		return this.inputStream.markSupported();
	}

}
