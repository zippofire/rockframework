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
package br.net.woodstock.rockframework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import br.net.woodstock.rockframework.io.InputOutputStream;
import br.net.woodstock.rockframework.util.Assert;


public abstract class IOUtils {

	private static final int	BUFFER_SIZE	= 8096;

	private IOUtils() {
		//
	}

	public static void copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(outputStream, "outputStream");

		int i = 0;
		byte[] bytes = new byte[IOUtils.BUFFER_SIZE];
		do {
			i = inputStream.read(bytes);
			if (i != -1) {
				outputStream.write(bytes, 0, i);
			}
		} while (i != -1);
	}

	public static void copy(final Reader reader, final Writer writer) throws IOException {
		Assert.notNull(reader, "reader");
		Assert.notNull(writer, "writer");

		int i = 0;
		char[] chars = new char[IOUtils.BUFFER_SIZE];
		do {
			i = reader.read(chars);
			if (i != -1) {
				writer.write(chars, 0, i);
			}
		} while (i != -1);
	}

	public static byte[] toByteArray(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		IOUtils.copy(inputStream, outputStream);

		return outputStream.toByteArray();
	}

	public static Reader toReader(final InputStream inputStream) {
		Assert.notNull(inputStream, "inputStream");

		return new InputStreamReader(inputStream);
	}

	public static String toString(final InputStream inputStream) throws IOException {
		return IOUtils.toString(inputStream, Charset.defaultCharset());
	}

	public static String toString(final InputStream inputStream, final Charset charset) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(charset, "charset");
		byte[] bytes = IOUtils.toByteArray(inputStream);

		String s = new String(bytes, charset);

		return s;
	}

	public static InputStream gzip(final byte[] bytes) throws IOException {
		Assert.notNull(bytes, "bytes");
		return IOUtils.gzip(new ByteArrayInputStream(bytes));
	}

	public static InputStream gzip(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		InputOutputStream inputOutputStream = new InputOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(inputOutputStream);
		IOUtils.copy(inputStream, gzipOutputStream);

		gzipOutputStream.close();
		return inputOutputStream.getInputStream();
	}

	public static InputStream gunzip(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
		InputOutputStream inputOutputStream = new InputOutputStream();
		IOUtils.copy(gzipInputStream, inputOutputStream);
		return inputOutputStream.getInputStream();
	}

}
