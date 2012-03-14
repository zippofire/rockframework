package br.net.woodstock.rockframework.core.test;

import java.net.InetAddress;

import br.net.woodstock.rockframework.utils.NetUtils;

import junit.framework.TestCase;

public class NetTest extends TestCase {

	public void xtest1() throws Exception {
		System.out.println(NetUtils.isFromNet("172.25.101.40", "172.25.101.0/24"));
	}

	public void xtest2() throws Exception {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}

	public void xtest3() throws Exception {
		System.out.println(InetAddress.getByAddress("172.25.101.85", null));
	}

	public void test4() throws Exception {
		System.out.println(NetUtils.toAddress("172.25.101.85"));
	}

	public void test5() throws Exception {
		System.out.println(NetUtils.toAddress("1.299.256.256"));
	}

}
