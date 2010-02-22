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
package net.woodstock.rockframework.util;

import net.woodstock.rockframework.config.CoreLog;

public abstract class Base64Encoder {

	private static final String		APACHE_BASE64	= "org.apache.commons.codec.binary.Base64";

	private static final String		SUN_BASE64		= "sun.misc.BASE64Encoder";

	private static Base64Encoder	instance		= Base64Encoder.getAvailableEncoder();

	public abstract String encode(String s);

	public abstract byte[] encode(byte[] b);

	public abstract String decode(String s);

	public abstract byte[] decode(byte[] b);

	public static Base64Encoder getInstance() {
		return Base64Encoder.instance;
	}

	private static Base64Encoder getAvailableEncoder() {
		try {
			Class.forName(Base64Encoder.APACHE_BASE64);
			Base64Encoder encoder = new ApacheBase64Encoder();
			CoreLog.getInstance().getLog().info("Using Apache Base64(Commons Codec)");
			return encoder;
		} catch (ClassNotFoundException e) {
			try {
				Class.forName(Base64Encoder.SUN_BASE64);
				Base64Encoder encoder = new SunBase64Encoder();
				CoreLog.getInstance().getLog().info("Using Sun Base64");
				return encoder;
			} catch (ClassNotFoundException ee) {
				throw new UnsupportedOperationException("No Base64 found");
			}
		}
	}

}
