package net.woodstock.rockframework.test;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase {

	public static String[]	excluidos	= { "/", "/faces", "/css/*", "/scripts/*", "/theme/*" };

	public static boolean verificaAcesso(String s) {
		for (String e : RegexTest.excluidos) {
			if (e.indexOf('*') != -1) {
				e = e.replace("*", ".*");
			}
			// if (e.indexOf('*') != -1) {
			if (Pattern.matches(e, s)) {
				return true;
			}
			/*
			 * } else { if (e.equals(s)) { return true; } }
			 */
		}
		return false;
	}

	public void test1() throws Exception {
		System.out.println(RegexTest.verificaAcesso("/faces"));
		System.out.println(RegexTest.verificaAcesso("/index.jsp"));
		System.out.println(RegexTest.verificaAcesso("/css/default.css"));
		System.out.println(RegexTest.verificaAcesso("/theme/default.txt"));
		System.out.println(RegexTest.verificaAcesso("/scripts/util.js"));
	}

}
