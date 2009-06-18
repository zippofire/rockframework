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
package net.woodstock.rockframework.security.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import net.woodstock.rockframework.utils.Base64Utils;

public abstract class Digester {

	private static Map<String, MessageDigest>	digesters;

	private Digester() {
		//
	}

	private static byte[] _digest(String data, Algorithm algorithm) throws NoSuchAlgorithmException {
		if (!Digester.digesters.containsKey(algorithm.algorithm())) {
			Digester.digesters.put(algorithm.algorithm(), MessageDigest.getInstance(algorithm.algorithm()));
		}
		MessageDigest d = Digester.digesters.get(algorithm.algorithm());
		byte[] b = d.digest(data.getBytes());
		d.reset();
		return b;
	}

	public static String digest(String data) throws NoSuchAlgorithmException {
		return Digester.digest(data, Algorithm.DEFAULT);
	}

	public static String digest(String data, Algorithm algorithm) throws NoSuchAlgorithmException {
		byte[] b = Digester._digest(data, algorithm);
		return new String(b);
	}

	public static String digestBase64(String data) throws NoSuchAlgorithmException {
		return Digester.digestBase64(data, Algorithm.DEFAULT);
	}

	public static String digestBase64(String data, Algorithm algorithm) throws NoSuchAlgorithmException {
		byte[] b = Digester._digest(data, algorithm);
		String str = new String(Base64Utils.toBase64(b));
		return str;
	}

	public static boolean isEquals(String d1, String d2) {
		return MessageDigest.isEqual(d1.getBytes(), d2.getBytes());
	}

	static {
		Digester.digesters = new HashMap<String, MessageDigest>();
	}

}
