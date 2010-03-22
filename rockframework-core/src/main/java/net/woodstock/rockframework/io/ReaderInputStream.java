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

import net.woodstock.rockframework.config.CoreMessage;

public class ReaderInputStream extends InputStream {

	private Reader	reader;

	public ReaderInputStream(final Reader reader) {
		super();
		if (reader == null) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_NULL, "Reader"));
		}
		this.reader = reader;
	}

	@Override
	public int available() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int read() throws IOException {
		return this.reader.read();
	}

}
