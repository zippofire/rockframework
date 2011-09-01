package br.net.woodstock.rockframework.domain.test.util;

import br.net.woodstock.rockframework.domain.util.Range;
import junit.framework.TestCase;

public class RangeTest extends TestCase {

	public void test1() throws Exception {
		Range<Integer> range = new Range<Integer>(Integer.valueOf(1), Integer.valueOf(10));
		System.out.println(range.isValid(Integer.valueOf(4)));
		System.out.println(range.isValid(Integer.valueOf(14)));
	}

}
