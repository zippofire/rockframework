package br.net.woodstock.rockframework.test.security;

import br.net.woodstock.rockframework.security.util.PasswordEncoder;
import br.net.woodstock.rockframework.security.util.SHA1PasswordEncoder;
import junit.framework.TestCase;

public class PasswordTest extends TestCase {

	public void test1() throws Exception {
		PasswordEncoder encoder = new SHA1PasswordEncoder();
		System.out.println("'" + encoder.encode("123456") + "'");
	}

}
