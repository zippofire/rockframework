package br.net.woodstock.rockframework.core.test;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.CharacterUtils;
import br.net.woodstock.rockframework.utils.StringUtils;
import junit.framework.TestCase;

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

	public void xtest3() throws Exception {
		String s = "12345678";
		s = s.substring(0, 5) + "-" + s.substring(5);
		System.out.println(s);
	}

	public void xtest4() throws Exception {
		String s = "Júnior";
		char[] chars = s.toCharArray();
		for (char c : chars) {
			System.out.println(c + " int: " + ((int) c) + " ascii: " + CharacterUtils.isASCII(c) + " iso-8859-1: " + CharacterUtils.isISO88591(c));
		}
	}

	public void xtest5() throws Exception {
		// Assert.equals(null, null, "null");
		Assert.equals("nulo", "nulo", "nulo");
	}

	public void xtest6() throws Exception {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str = "Texto que sera modificado";
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char currentChar = chars[i];
			if (Character.isLetter(currentChar)) {
				boolean isUpperCase = Character.isUpperCase(currentChar);
				currentChar = Character.toUpperCase(currentChar);
				int newIndex = letters.indexOf(currentChar) + 13;
				char newChar = '\0';
				if (newIndex >= letters.length()) {
					newIndex -= letters.length();
				}
				newChar = letters.charAt(newIndex);
				if (!isUpperCase) {
					newChar = Character.toLowerCase(newChar);
				}
				chars[i] = newChar;
			}
		}
		String newStr = new String(chars);
		System.out.println("Original: " + str);
		System.out.println("Novo    : " + newStr);
	}
	
	public void test7() throws Exception {
		System.out.println(StringUtils.escapeHTML("<html lang=\"en\" lang='en'>consulta.jsp?x=1</html>"));
	}
		

}
