package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.Base64Encoder;

public class Base64Test extends TestCase {

	public void test1() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().encode("Teste") + "'");
	}

	public void test2() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().decode("VGVzdGU=") + "'");
	}
}
