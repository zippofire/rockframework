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
package net.woodstock.rockframework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public abstract class IOUtils {

	private IOUtils() {
		//
	}

	public static void copy(final InputStream input, final OutputStream output) throws IOException {
		IOUtils.copy(input, output, true);
	}

	public static void copy(final InputStream input, final OutputStream output, final boolean close) throws IOException {
		int b = -1;
		do {
			b = input.read();
			if (b != -1) {
				output.write(b);
			}
		} while (b != -1);

		if (close) {
			input.close();
			output.flush();
			output.close();
		}
	}

	public static void copy(final Reader reader, final Writer writer) throws IOException {
		IOUtils.copy(reader, writer, true);
	}

	public static void copy(final Reader reader, final Writer writer, final boolean close) throws IOException {
		int b = -1;
		do {
			b = reader.read();
			if (b != -1) {
				writer.write(b);
			}
		} while (b != -1);

		if (close) {
			reader.close();
			writer.flush();
			writer.close();
		}
	}

	public static byte[] toByteArray(final InputStream inputStream) throws IOException {
		byte[] b = new byte[inputStream.available()];
		inputStream.read(b);
		return b;
	}

	public static InputStream toInputStream(final byte[] bytes) {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		return inputStream;
	}

	public static InputStream toInputStream(final Reader reader) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int i = -1;
		while ((i = reader.read()) != -1) {
			output.write(i);
		}
		return new ByteArrayInputStream(output.toByteArray());
	}

}
