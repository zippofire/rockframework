package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.NetUtils;

public class NetTest extends TestCase {

	public void test1() throws Exception {
		System.out.println(NetUtils.isFromNet("172.25.101.40", "172.25.101.0/24"));
	}

}
