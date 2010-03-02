package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.StringUtils;

public class StringTest extends TestCase {

	public void xtest1() throws Exception {
		// FileReader reader = new FileReader("D:/temp/teste.txt");
		// Scanner scanner = new Scanner(reader);
		//
		// Charset charsetFrom = Charset.forName("UTF-8");
		//
		// while (scanner.hasNextLine()) {
		// String line = scanner.nextLine();
		// System.out.println("1|" + line);
		// System.out.println("2|" + StringUtils.convertCharset(charsetFrom, line));
		// }
		//
		// scanner.close();
		// reader.close();

		System.out.println(Base64Utils.toBase64("37893432349"));
	}

	public void xtest2() throws Exception {
		System.out.println(StringUtils.random(10000));
	}

	public void test3() throws Exception {
		String s = "12345678";
		s = s.substring(0, 5) + "-" + s.substring(5);
		System.out.println(s);
	}

}
