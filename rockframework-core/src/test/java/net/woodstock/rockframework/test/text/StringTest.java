package net.woodstock.rockframework.test.text;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.RandomGenerator;
import net.woodstock.rockframework.util.RandomGenerator.RandomPattern;
import net.woodstock.rockframework.util.StringFormat;
import net.woodstock.rockframework.util.StringFormatFactory;

public class StringTest extends TestCase {

	public void xtest1() throws Exception {
		String s = "530000000012010";
		StringFormat format = StringFormatFactory.getInstance().getFormat("#####.######/####");
		System.out.println(format.format(s));
	}

	public void xtest2() throws Exception {
		String s = "53000.000001/2010";
		StringFormat format = StringFormatFactory.getInstance().getFormat("#####.######/####");
		System.out.println(format.parse(s));
	}

	public void test3() throws Exception {
		RandomGenerator randomString = new RandomGenerator(15);
		for (int i = 0; i < 5; i++) {
			System.out.println(randomString.generate());
		}
		randomString = new RandomGenerator(15, RandomPattern.DIGITS);
		for (int i = 0; i < 5; i++) {
			System.out.println(randomString.generate());
		}
		randomString = new RandomGenerator(15, RandomPattern.LETTER);
		for (int i = 0; i < 5; i++) {
			System.out.println(randomString.generate());
		}
	}
}
