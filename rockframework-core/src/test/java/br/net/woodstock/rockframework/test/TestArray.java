package br.net.woodstock.rockframework.test;

import br.net.woodstock.rockframework.utils.ArrayUtils;
import junit.framework.TestCase;

public class TestArray extends TestCase {

	public void test1() throws Exception {
		System.out.println(ArrayUtils.toString(new int[] { 1, 2, 3, 4 }));
	}

}
