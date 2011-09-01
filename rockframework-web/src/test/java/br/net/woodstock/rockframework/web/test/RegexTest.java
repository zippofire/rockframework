package br.net.woodstock.rockframework.web.test;

import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RegexTest extends TestCase {

	protected String getRegex() {
		return "^(http|https)://(sistema\\.mc\\.gov.br|localhost|192\\.168\\.50\\.65)(:[0-9]{2,5})?(/|(/.*)?)";
	}

	public void testRegex1() throws Exception {
		// String regex = "(http|https)://sistema\\.mc\\.gov.br(:[0-9]{2,5})?(/|(/.*)?)";
		String regex = this.getRegex();
		String url = "http://sistema.mc.gov.br/x/y/x";

		Assert.assertTrue(Pattern.matches(regex, url));
	}

	public void testRegex2() throws Exception {
		// String regex = "(http|https)://sistema\\.mc\\.gov.br(:[0-9]{2,5})?(/|(/.*)?)";
		String regex = this.getRegex();
		String url = "http://sistema.mc.gov.br:8080/x/y/x";

		Assert.assertTrue(Pattern.matches(regex, url));
	}

	public void testRegex3() throws Exception {
		String regex = this.getRegex();
		String url = "http://localhost:8080/x/y/x";

		Assert.assertTrue(Pattern.matches(regex, url));
	}

	public void testRegex4() throws Exception {
		String regex = this.getRegex();
		String url = "http://localhost:18080";

		Assert.assertTrue(Pattern.matches(regex, url));
	}

	public void testRegex5() throws Exception {
		String regex = this.getRegex();
		String url = "http://cracker/x/y/x";

		Assert.assertFalse(Pattern.matches(regex, url));
	}

	public void testRegex6() throws Exception {
		String regex = this.getRegex();
		String url = "http://cracker:8080/x/y/x";

		Assert.assertTrue(Pattern.matches(regex, url));
	}

}
