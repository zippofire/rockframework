package br.net.woodstock.rockframework.test;

import junit.framework.TestCase;

public class TestArray extends TestCase {

	public void test2() throws Exception {
		int[] array = new int[] { 1, 2, 3, 4, 5 };
		for (int i : array) {
			System.out.println(i);
		}
	}

}
