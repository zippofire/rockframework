package br.net.woodstock.rockframework.test.util;

import java.lang.reflect.Field;

import junit.framework.TestCase;

public class ReflectionTest extends TestCase {

	public void test1() throws Exception {
		X x = new X(1);
		Field field = x.getClass().getDeclaredField("value");
		field.setAccessible(true);

		System.out.println(field.get(x));
	}

	public static class X {

		private int	value;

		public X(final int value) {
			super();
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

}
