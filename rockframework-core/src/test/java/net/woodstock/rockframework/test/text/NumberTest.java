package net.woodstock.rockframework.test.text;

import java.text.NumberFormat;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.NumberFormatFactory;

public class NumberTest extends TestCase {

	public void test1() throws Exception {
		Integer i = Integer.valueOf(123456789);
		NumberFormat format = NumberFormatFactory.getInstance().getFormat("###,##0");
		System.out.println(format.format(i));
	}

}
