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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import net.woodstock.rockframework.util.Base64Encoder;

public abstract class Base64Utils {

	private Base64Utils() {
		//
	}

	public static byte[] toBase64(final byte[] b) {
		return Base64Encoder.getInstance().encode(b);
	}

	public static byte[] fromBase64(final byte[] b) {
		return Base64Encoder.getInstance().decode(b);
	}

	public static String toBase64(final String s) {
		return Base64Encoder.getInstance().encode(s);
	}

	public static String fromBase64(final String s) {
		return Base64Encoder.getInstance().decode(s);
	}

	public static byte[] serialize(final Object o) throws IOException {
		if (o == null) {
			return null;
		}

		if (!(o instanceof Serializable)) {
			throw new NotSerializableException(o.getClass().getName());
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ObjectOutputStream output = new ObjectOutputStream(bos);
		output.writeUnshared(o);
		output.close();
		bos.close();

		byte[] bytes = Base64Encoder.getInstance().encode(bos.toByteArray());

		return bytes;
	}

	public static void serialize(final Object o, final File file) throws IOException {
		Base64Utils.serialize(o, new FileOutputStream(file));
	}

	public static void serialize(final Object o, final OutputStream output) throws IOException {
		byte[] bytes = Base64Utils.serialize(o);
		output.write(bytes);
		output.close();
	}

	public static Object unserialize(final String object) throws IOException, ClassNotFoundException {
		if (StringUtils.isEmpty(object)) {
			return null;
		}
		return Base64Utils.unserialize(object.getBytes(LocaleUtils.getCharset()));
	}

	public static Object unserialize(final byte[] bytes) throws IOException, ClassNotFoundException {
		byte[] b = Base64Encoder.getInstance().decode(bytes);

		ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(b));
		Object o = input.readUnshared();
		input.close();

		return o;
	}

	public static Object unserialize(final File file) throws IOException, ClassNotFoundException {
		return Base64Utils.unserialize(new FileInputStream(file));
	}

	public static Object unserialize(final InputStream input) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		int b = -1;
		do {
			b = input.read();
			if (b != -1) {
				output.write(b);
			}
		} while (b != -1);

		input.close();
		output.close();

		return Base64Utils.unserialize(output.toByteArray());
	}

}
