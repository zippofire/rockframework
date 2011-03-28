package net.woodstock.rockframework.test.text;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.RandomGenerator;
import net.woodstock.rockframework.util.RandomGenerator.RandomPattern;
import net.woodstock.rockframework.util.StringFormat;
import net.woodstock.rockframework.util.StringFormatFactory;
import net.woodstock.rockframework.utils.StringUtils;

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

	public void xtest3() throws Exception {
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

	public void xtest4() throws Exception {
		String texto = "Frase um. Frase dois. Frase 3";
		Pattern pattern = Pattern.compile("(\\.?.*dois.*\\.)");
		// Pattern pattern = Pattern.compile("\\.?.*dois.*\\..*");
		// Pattern pattern = Pattern.compile("dois");
		Matcher matcher = pattern.matcher(texto);
		System.out.println(matcher);
		System.out.println(matcher.matches());
		System.out.println(matcher.group());
		System.out.println(matcher.groupCount());
		System.out.println(matcher.regionStart());
		System.out.println(matcher.regionEnd());
	}

	public void test5() throws Exception {
		System.out.println(StringUtils.convertCharset(Charset.forName("ISO-8859-1"), Charset.forName("UTF-8"), "Júnior"));
	}
}
