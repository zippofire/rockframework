package br.net.woodstock.rockframework.core.test;

import br.net.woodstock.rockframework.utils.NumberUtils;
import junit.framework.TestCase;

public class ThreadTest extends TestCase {

	public void test1() throws Exception {
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread() {

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
			};
			t.start();
		}
	}

	public static class ClassX {

		private static final ThreadLocal<Double>	THREAD	= new ThreadLocal<Double>();

		protected ClassX() {
			super();
		}

		public static Double getDouble() {
			Double d = ClassX.THREAD.get();
			if (d == null) {
				d = new Double(Math.random());
				ClassX.THREAD.set(d);
			}
			return ClassX.THREAD.get();
		}
	}

}
