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
import java.io.Reader;

import net.woodstock.rockframework.util.Assert;

public class ReaderInputStream extends InputStream {

	private InputStream	wrapper;

	public ReaderInputStream(final Reader reader) throws IOException {
		super();
		Assert.notNull(reader, "reader");
		InputOutputStream ios = new InputOutputStream();
		int b = -1;
		while ((b = reader.read()) != -1) {
			ios.write(b);
		}
		this.wrapper = ios.getInputStream();
	}

	@Override
	public int available() throws IOException {
		return this.wrapper.available();
	}

	@Override
	public int read() throws IOException {
		return this.wrapper.read();
	}

}
