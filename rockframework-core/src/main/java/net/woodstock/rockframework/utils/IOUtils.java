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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import net.woodstock.rockframework.util.Assert;

public abstract class IOUtils {

	private IOUtils() {
		//
	}

	public static void copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		Assert.notNull(outputStream, "outputStream");
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		outputStream.write(bytes);
	}

	public static byte[] toByteArray(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		return bytes;
	}

	public static Reader toReader(final InputStream inputStream) {
		Assert.notNull(inputStream, "inputStream");

		return new InputStreamReader(inputStream);
	}

	public static String toString(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		String s = new String(bytes);

		return s;
	}

}
