package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.ArrayUtils;

public class TestArray extends TestCase {

	public void test1() throws Exception {
		System.out.println(ArrayUtils.toString(new int[] { 1, 2, 3, 4 }));
	}

}
