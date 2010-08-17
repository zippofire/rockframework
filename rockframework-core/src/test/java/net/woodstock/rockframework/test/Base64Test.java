package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.Base64Encoder;

public class Base64Test extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().encode("Teste") + "'");
	}

	public void xtest2() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().decode("VGVzdGU=") + "'");
	}
}
