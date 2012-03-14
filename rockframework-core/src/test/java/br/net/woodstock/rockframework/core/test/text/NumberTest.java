package br.net.woodstock.rockframework.core.test.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import junit.framework.TestCase;

public class NumberTest extends TestCase {

	public void test1() throws Exception {
		Integer i = Integer.valueOf(123456789);
		NumberFormat format = new DecimalFormat("###,##0");
		System.out.println(format.format(i));
	}

}
