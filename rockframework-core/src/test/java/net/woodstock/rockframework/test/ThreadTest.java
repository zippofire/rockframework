package net.woodstock.rockframework.test;

import net.woodstock.rockframework.utils.NumberUtils;

public class ThreadTest {

	public static void main(String[] args) {
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
			Double d = thread.get();
			if (d == null) {
				d = new Double(Math.random());
				thread.set(d);
			}
			return ClassX.thread.get();
		}
	}

}
