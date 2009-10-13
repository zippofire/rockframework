package net.woodstock.rockframework.test;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase {

	public void test1() throws Exception {
		String regex = "foo\\.bars\\..*";
		String s = "foo.bars.bars";
		System.out.println(Pattern.matches(regex, s));
	}

}
