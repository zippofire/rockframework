package br.net.woodstock.rockframework.test;

import br.net.woodstock.rockframework.util.Base64Encoder;
import junit.framework.TestCase;

public class Base64Test extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().encode("Teste") + "'");
	}

	public void xtest2() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().decode("VGVzdGU=") + "'");
	}
}
