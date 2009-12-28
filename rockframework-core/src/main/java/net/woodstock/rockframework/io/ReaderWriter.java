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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriter extends Writer {

	private InputOutputStream	inputStream;

	private OutputStreamWriter	writer;

	public ReaderWriter() {
		super();
		this.inputStream = new InputOutputStream();
		this.writer = new OutputStreamWriter(this.inputStream);
	}

	@Override
	public void close() throws IOException {
		this.writer.close();
		this.writer = new OutputStreamWriter(this.inputStream);
	}

	@Override
	public void flush() throws IOException {
		this.writer.close();
	}

	@Override
	public void write(final char[] cbuf, final int off, final int len) throws IOException {
		this.writer.write(cbuf, off, len);
	}

	public synchronized InputStream getInputStream() throws IOException {
		this.close();
		return this.inputStream.getInputStream();
	}

	public synchronized Reader getReader() throws IOException {
		this.close();
		return new InputStreamReader(this.inputStream.getInputStream());
	}

}
