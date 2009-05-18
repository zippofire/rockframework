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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

public abstract class Base64Utils {

	private Base64Utils() {
		//
	}

	public static byte[] toBase64(byte[] b) {
		return Base64.encodeBase64Chunked(b);
	}

	public static byte[] toBase64(String s) {
		return Base64.encodeBase64Chunked(s.getBytes());
	}

	public static byte[] fromBase64(byte[] b) {
		return Base64.decodeBase64(b);
	}

	public static byte[] fromBase64(String s) {
		return Base64.decodeBase64(s.getBytes());
	}

	public static String toBase64String(byte[] b) {
		return new String(Base64.encodeBase64Chunked(b)).trim();
	}

	public static String toBase64String(String s) {
		return new String(Base64.encodeBase64Chunked(s.getBytes())).trim();
	}

	public static String fromBase64String(byte[] b) {
		return new String(Base64.decodeBase64(b));
	}

	public static String fromBase64String(String s) {
		return new String(Base64.decodeBase64(s.getBytes()));
	}

	public static String serialize(Object o) throws IOException {
		if (o instanceof Serializable) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ObjectOutputStream output = new ObjectOutputStream(bos);
			output.writeUnshared(o);
			output.close();
			bos.close();

			return new String(Base64Utils.toBase64(bos.toByteArray()));
		}
		return null;
	}

	public static Object unserialize(String s) throws IOException, ClassNotFoundException {
		byte[] b = Base64Utils.fromBase64(s);

		ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(b));
		Object o = input.readUnshared();
		input.close();

		return o;
	}

	public static void serializeTo(Object o, String file) throws IOException {
		Base64Utils.serializeTo(o, new FileOutputStream(file));
	}

	public static void serializeTo(Object o, File file) throws IOException {
		Base64Utils.serializeTo(o, new FileOutputStream(file));
	}

	public static void serializeTo(Object o, OutputStream output) throws IOException {
		if (o instanceof Serializable) {
			String s = Base64Utils.serialize(o);
			output.write(s.getBytes());
			output.close();
		}
	}

	public static Object unserializeFrom(String file) throws IOException, ClassNotFoundException {
		return Base64Utils.unserializeFrom(new File(file));
	}

	public static Object unserializeFrom(File file) throws IOException, ClassNotFoundException {
		return Base64Utils.unserializeFrom(new FileInputStream(file));
	}

	public static Object unserializeFrom(InputStream input) throws IOException, ClassNotFoundException {
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

		return Base64Utils.unserialize(new String(output.toByteArray()));
	}

}
