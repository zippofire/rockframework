package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.impl.Base64Digester;
import net.woodstock.rockframework.security.digest.impl.SimpleDigester;
import net.woodstock.rockframework.security.digest.impl.SimpleDigester.Algorithm;
import net.woodstock.rockframework.util.Base64Encoder;

public class Base64Test extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().encode("Teste") + "'");
	}

	public void xtest2() throws Exception {
		System.out.println("'" + Base64Encoder.getInstance().decode("VGVzdGU=") + "'");
	}

	public void test3() throws Exception {
		Digester d1 = new SimpleDigester(Algorithm.DEFAULT);
		Digester d2 = new Base64Digester(d1);
		System.out.println(d2.digest("lourival"));
	}
}
