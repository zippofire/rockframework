package br.net.woodstock.rockframework.core.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.CharacterUtils;
import br.net.woodstock.rockframework.utils.StringUtils;

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

	public void xtest7() throws Exception {
		System.out.println(StringUtils.escapeHTML("<html lang=\"en\" lang='en'>consulta.jsp?x=1</html>"));
	}

	public void testFormat() {
		List<String[]> dados = new ArrayList<String[]>();
		dados.add(new String[] { "Pessoa 1", "00000000000" });
		dados.add(new String[] { "Pessoa da Silva Sauro", "11111111111" });
		dados.add(new String[] { "Pessoa sem nome", "22222222222" });
		dados.add(new String[] { "Teste", "33333333333" });

		StringBuilder builder = new StringBuilder();
		for (String[] dado : dados) {
			builder.append(String.format("Nome: %-50s CPF: %11s\n", dado[0], dado[1]));
		}
		System.out.println(builder.toString());
	}

}
