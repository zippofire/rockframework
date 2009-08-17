package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.NumberUtils;

public class ThreadTest extends TestCase {

	public void test1() throws Exception {
		for (int i = 0; i < 3; i++) {
			new Thread() {

				@Override
				public void run() {
					for (int j = 0; j < 3; j++) {
						System.out.println(j + " " + ClassX.getDouble());
						try {
							Thread.sleep(NumberUtils.random(3) * 1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

		}
	}

	public static class ClassX {

		private static final ThreadLocal<Double>	thread	= new ThreadLocal<Double>();

		public static Double getDouble() {
			Double d = ClassX.thread.get();
			if (d == null) {
				d = new Double(Math.random());
				ClassX.thread.set(d);
			}
			return ClassX.thread.get();
		}
	}

}
