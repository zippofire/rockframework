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
import java.io.OutputStream;
import java.io.Writer;

import net.woodstock.rockframework.util.Assert;

public class WriterOutputStream extends OutputStream {

	private Writer	writer;

	public WriterOutputStream(final Writer writer) {
		super();
		Assert.notNull(writer, "writer");
		this.writer = writer;
	}

	@Override
	public void write(final int b) throws IOException {
		this.writer.write(b);
	}

}
