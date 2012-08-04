package br.net.woodstock.rockframework.web.spring.test;

import java.net.URLEncoder;
import java.nio.charset.Charset;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.web.struts2.converter.EncryptedConverter;
import br.net.woodstock.rockframework.web.types.EncryptedType;

public class EncryptedTypeTest extends TestCase {

	public void test1() throws Exception {
		EncryptedType type = new EncryptedType("12345");
		EncryptedConverter converter = new EncryptedConverter();
		String enc = converter.convertToString(null, type);
		EncryptedType type2 = (EncryptedType) converter.convertFromString(null, new String[] { enc }, null);
		System.out.println(URLEncoder.encode(enc, Charset.defaultCharset().name()));
		System.out.println(type2.getValue());
	}

}
