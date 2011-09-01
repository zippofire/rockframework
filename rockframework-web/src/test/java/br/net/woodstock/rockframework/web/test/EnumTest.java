package br.net.woodstock.rockframework.web.test;

import java.util.HashSet;
import java.util.Set;

import br.net.woodstock.rockframework.web.struts2.security.HttpMethodType;

import junit.framework.TestCase;


public class EnumTest extends TestCase {

	public void xtest1() throws Exception {
		HttpMethodType type = HttpMethodType.valueOf("GET");
		System.out.println(type);
	}
	
	public void test2() throws Exception {
		Set<HttpMethodType> set = new HashSet<HttpMethodType>();
		set.add(HttpMethodType.valueOf("GET"));
		set.add(HttpMethodType.valueOf("POST"));
		set.add(HttpMethodType.valueOf("PUT"));
		System.out.println(set);
	}
	
}
