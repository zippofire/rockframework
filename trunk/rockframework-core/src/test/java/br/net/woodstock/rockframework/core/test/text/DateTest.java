package br.net.woodstock.rockframework.core.test.text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class DateTest extends TestCase {

	public void test1() throws Exception {
		Date d = new Date();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(d));
	}

}
