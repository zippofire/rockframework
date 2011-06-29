package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.StringFormater;

public class PrinterTest extends TestCase {

	public void test1() throws Exception {
		// System.out.println(StringUtils.format("####-####", "12345678"));
		// System.out.println(StringUtils.format("###.###.###-##", "86216503120"));
		//
		// System.out.println(StringUtils.unformat("####-####", "1234-5678"));
		// System.out.println(StringUtils.unformat("###.###.###-##", "862.165.031-20"));

		System.out.println(new StringFormater("(##)####-####").format("6112345678"));
		System.out.println(new StringFormater("(##)####-####").parse("(61)1234-5678"));

	}

}
