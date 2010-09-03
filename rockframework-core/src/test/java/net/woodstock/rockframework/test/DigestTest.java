package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.digest.DigestType;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.impl.BasicDigester;
import net.woodstock.rockframework.security.digest.impl.HexDigester;

public class DigestTest extends TestCase {

	public void test1() throws Exception {
		Digester digester = new BasicDigester(DigestType.SHA_256);
		Digester delegate = new HexDigester(digester);

		System.out.println(new String(delegate.digest("Teste da Silva Sauro\n".getBytes())));
	}

}
