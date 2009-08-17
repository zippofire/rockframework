package net.woodstock.rockframework.test;

import java.text.Normalizer;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.Base64Utils;

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

	public void test2() throws Exception {
		String s = " mária júnior ";

		s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

		System.out.println("'" + s + "'");
	}

}
