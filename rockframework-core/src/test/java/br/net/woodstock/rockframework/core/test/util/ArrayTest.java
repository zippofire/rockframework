package br.net.woodstock.rockframework.core.test.util;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.utils.ArrayUtils;

public class ArrayTest extends TestCase {

	public void test1() throws Exception {
		Object[] array0 = new Object[0];
		String[] array1 = new String[0];
		Integer[] array2 = new Integer[0];
		int[] array3 = new int[0];
		boolean[] array4 = new boolean[0];
		System.out.println(ArrayUtils.getArrayType(array0).getCanonicalName());
		System.out.println(ArrayUtils.getArrayType(array1).getCanonicalName());
		System.out.println(ArrayUtils.getArrayType(array2).getCanonicalName());
		System.out.println(ArrayUtils.getArrayType(array3).getCanonicalName());
		System.out.println(ArrayUtils.getArrayType(array4).getCanonicalName());
	}

}
