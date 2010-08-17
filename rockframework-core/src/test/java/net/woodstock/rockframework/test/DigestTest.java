package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.digest.DigestType;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.impl.DigesterImpl;

public class DigestTest extends TestCase {

	public void test1() throws Exception {
		Digester digester1 = new DigesterImpl(DigestType.SHA_256);
		System.out.println(digester1.digestAsString("Teste da Silva Sauro\n".getBytes()));
	}

}
