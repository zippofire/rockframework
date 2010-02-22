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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public abstract class NetUtils {

	public static final String	IPV4_REGEX	= "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

	private NetUtils() {
		//
	}

	public static boolean isFromNet(final String address, final String netAddress) throws UnknownHostException {
		InetAddress hostAddress = InetAddress.getByName(address);
		InetAddress networkAddress = InetAddress.getByName(netAddress.substring(0, netAddress.indexOf('/')));
		int mask = Integer.parseInt(netAddress.substring(netAddress.indexOf('/') + 1));
		if (mask > 31) {
			throw new IllegalArgumentException("Invalid network mask '" + mask + "'");
		}
		StringBuilder bufferAddress = new StringBuilder();
		StringBuilder bufferNetwork = new StringBuilder();
		for (byte b : hostAddress.getAddress()) {
			int i = b;
			if (i < 0) {
				i += 256;
			}
			bufferAddress.append(StringUtils.lpad(Integer.toBinaryString(i), 8, '0'));
		}
		for (byte b : networkAddress.getAddress()) {
			int i = b;
			if (i < 0) {
				i += 256;
			}
			bufferNetwork.append(StringUtils.lpad(Integer.toBinaryString(i), 8, '0'));
		}
		return bufferNetwork.toString().substring(0, mask).equals(bufferAddress.toString().subSequence(0, mask));
	}

	public static InetAddress toAddress(final String address) throws UnknownHostException {
		if (!Pattern.matches(NetUtils.IPV4_REGEX, address)) {
			throw new IllegalArgumentException("Invalid IPV4 address " + address);
		}
		String[] array = address.split("\\.");
		byte b0 = (byte) Integer.parseInt(array[0]);
		byte b1 = (byte) Integer.parseInt(array[1]);
		byte b2 = (byte) Integer.parseInt(array[2]);
		byte b3 = (byte) Integer.parseInt(array[3]);
		return InetAddress.getByAddress(new byte[] { b0, b1, b2, b3 });
	}
}
