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
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import br.net.woodstock.rockframework.util.Base64Encoder;


public abstract class Base64Utils {

	private static Base64Encoder	encoder	= Base64Encoder.getInstance();

	private Base64Utils() {
		//
	}

	public static byte[] toBase64(final byte[] b) {
		return encoder.encode(b);
	}

	public static byte[] fromBase64(final byte[] b) {
		return encoder.decode(b);
	}

	public static String toBase64(final String s) {
		return encoder.encode(s);
	}

	public static String fromBase64(final String s) {
		return encoder.decode(s);
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

		byte[] bytes = encoder.encode(bos.toByteArray());

		return bytes;
	}

	public static Object unserialize(final byte[] bytes) throws IOException, ClassNotFoundException {
		byte[] b = encoder.decode(bytes);

		ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(b));
		Object o = input.readUnshared();
		input.close();

		return o;
	}

}
