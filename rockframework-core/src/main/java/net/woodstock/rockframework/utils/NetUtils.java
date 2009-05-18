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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedHashSet;

import net.woodstock.rockframework.sys.SysLogger;

public abstract class NetUtils {

	public static int	TIMEOUT	= 3000;

	private NetUtils() {
		//
	}

	public static Collection<Integer> scan(InetAddress host, int startPort, int endPort) {
		Collection<Integer> list = new LinkedHashSet<Integer>();

		for (int port = startPort; port <= endPort; port++) {
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(host, port);
			try {
				socket.setSoTimeout(NetUtils.TIMEOUT);
				socket.connect(address);
				socket.close();
				list.add(Integer.valueOf(port));
				SysLogger.getLogger().debug("Connected on " + host.getHostAddress() + ":" + port);
			} catch (IOException e) {
				//
			}
			socket = null;
		}

		return list;
	}

	public static boolean isFromNet(String address, String netAddress) throws UnknownHostException {
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
		return bufferNetwork.toString().substring(0, mask).equals(
				bufferAddress.toString().subSequence(0, mask));
	}
}
