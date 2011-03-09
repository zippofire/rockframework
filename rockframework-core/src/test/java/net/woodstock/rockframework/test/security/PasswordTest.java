package net.woodstock.rockframework.test.security;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.util.PasswordEncoder;
import net.woodstock.rockframework.security.util.SHA1PasswordEncoder;

public class PasswordTest extends TestCase {

	public void test1() throws Exception {
		PasswordEncoder encoder = new SHA1PasswordEncoder();
		System.out.println("'" + encoder.encode("123456") + "'");
	}

}
