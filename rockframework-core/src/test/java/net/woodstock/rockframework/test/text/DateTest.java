package net.woodstock.rockframework.test.text;

import java.text.DateFormat;
import java.util.Date;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.DateFormatFactory;

public class DateTest extends TestCase {

	public void test1() throws Exception {
		Date d = new Date();
		DateFormat format = DateFormatFactory.getInstance().getFormat("dd/MM/yyyy");
		System.out.println(format.format(d));
	}

}
