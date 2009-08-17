package net.woodstock.rockframework.web.test;

import junit.framework.TestCase;

public class TestUrl extends TestCase {

	public void test() throws Exception {
		String context = "http://localhost:8080/PerfilWeb/";
		String url = "http://localhost:8080/PerfilWeb/headers.jsp";

		System.out.println(url.startsWith(context));

	}

}
