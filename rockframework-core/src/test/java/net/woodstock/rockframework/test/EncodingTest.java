package net.woodstock.rockframework.test;

import java.nio.charset.Charset;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.Base64Utils;

public class EncodingTest extends TestCase {

	public void xtest1() throws Exception {
		String s = "Lourival Sabino da Silva Júnior";
		byte[] bytes = s.getBytes(Charset.forName("ISO-8859-1"));
		String ss = new String(bytes, Charset.forName("UTF-8"));
		System.out.println(s);
		System.out.println(ss);
	}
	
	public void xtest2() throws Exception {
		String s = "Lourival Sabino da Silva Júnior";
		byte[] bytes = s.getBytes(Charset.forName("ISO-8859-1"));
		String ss = new String(bytes, Charset.forName("UTF-8"));
		System.out.println(s);
		System.out.println(ss);
	}
	
	public void xtest3() throws Exception {
		System.out.println(Base64Utils.toBase64("A"));
	}

	public void test4() throws Exception {
		System.out.println(Charset.forName("UTF-8").displayName());
		System.out.println(Charset.forName("UTF-8").name());
		System.out.println(Charset.forName("ISO-8859-1").displayName());
		System.out.println(Charset.forName("ISO-8859-1").name());
	}
	
}
