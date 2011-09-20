package br.net.woodstock.rockframework.test.util;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.utils.HexUtils;

public class HexTest extends TestCase {

	public void test1() throws Exception {
		byte[] bytes = new byte[] { (byte) -1, (byte) -22, (byte) -33, (byte) -44 };
		String hex = HexUtils.toHexString(bytes);

		System.out.println(hex);

		bytes = HexUtils.fromHexString(hex);

		for (byte b : bytes) {
			System.out.println(b);
		}
	}
}
