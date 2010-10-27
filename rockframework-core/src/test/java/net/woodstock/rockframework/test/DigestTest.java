package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.digest.DigestType;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.impl.BasicDigester;
import net.woodstock.rockframework.security.digest.impl.HexDigester;
import net.woodstock.rockframework.security.util.MD5PasswordEncoder;
import net.woodstock.rockframework.security.util.PasswordEncoder;
import net.woodstock.rockframework.security.util.SHA1PasswordEncoder;

public class DigestTest extends TestCase {

	public void xtest1() throws Exception {
		Digester digester = new BasicDigester(DigestType.SHA_256);
		Digester delegate = new HexDigester(digester);

		System.out.println(new String(delegate.digest("Teste da Silva Sauro\n".getBytes())));
	}

	public void test2() throws Exception {
		PasswordEncoder encoder = new MD5PasswordEncoder();
		System.out.println(encoder.encode("lourival"));
	}

	public void test3() throws Exception {
		PasswordEncoder encoder = new SHA1PasswordEncoder();
		System.out.println(encoder.encode("lourival"));
	}

}
