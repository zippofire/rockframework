package br.net.woodstock.rockframework.core.test.util;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.util.Assert;

public class EnumTest extends TestCase {

	public void test1() throws Exception {
		Assert.validEnum("A", X.class, "enum");
		Assert.validEnum("X", X.class, "enum");
	}

	public static enum X {
		A, B, C;
	}

}
