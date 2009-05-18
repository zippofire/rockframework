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
package net.woodstock.rockframework.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import net.woodstock.rockframework.utils.NetUtils;

public class PortTest {

	public PortTest() throws UnknownHostException {
		super();

		Collection<Integer> ports = NetUtils.scan(InetAddress.getByName("rock.woodstock.net"), 1, 1024);

		for (Integer port : ports) {
			System.out.println(port);
		}
	}

	public static void main(String[] args) {
		try {
			new PortTest();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
