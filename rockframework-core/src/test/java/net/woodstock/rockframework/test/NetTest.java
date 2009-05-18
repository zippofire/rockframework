/**
 * 
 */
package net.woodstock.rockframework.test;

import net.woodstock.rockframework.utils.NetUtils;

public class NetTest {

	public static void main(String[] args) {
		try {
			System.out.println(NetUtils.isFromNet("172.25.101.40", "172.25.101.0/24"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
