package net.woodstock.rockframework.test;

import java.io.Serializable;
import java.util.Random;

import junit.framework.TestCase;
import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.cache.impl.CacheManagerImpl;

public class CacheTest extends TestCase {

	public void test1() throws Exception {
		CacheManager manager = CacheManagerImpl.getInstance();
		Cache cache = manager.create("test");

		System.out.println("Cache: " + cache.getClass().getCanonicalName());

		int start = 0;
		int end = 10000;

		for (int i = start; i <= end; i++) {
			if (i % 500 == 0) {
				System.out.println("Qtd: " + i);
			}
			Test test = new Test("Testando " + i);
			cache.add("test" + i, test);
		}
		System.out.println(cache.contains("test" + start));
		System.out.println(cache.contains("test" + end));
		System.out.println(cache.get("test" + start));
		System.out.println(cache.get("test" + end));
	}

	public static void main(final String[] args) {
		CacheManager manager = CacheManagerImpl.getInstance();
		Cache cache = manager.create("test");
		Thread t1 = new Thread(new TestRun("t1", cache));
		Thread t2 = new Thread(new TestRun("t2", cache));
		Thread t3 = new Thread(new TestRun("t3", cache));

		t1.start();
		t2.start();
		t3.start();
	}

	public static class TestRun implements Runnable {

		private String	name;

		private Cache	cache;

		public TestRun(final String name, final Cache cache) {
			super();
			this.name = name;
			this.cache = cache;
		}

		@Override
		public void run() {
			Random random = new Random();
			for (int i = 0; i < 10000; i++) {
				if (i % 500 == 0) {
					System.out.println(this.name + " Qtd: " + i);
				}
				Test test = new Test(this.name + " " + i);
				this.cache.add(this.name + i, test);
				try {
					Thread.sleep(random.nextInt(20));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static class Test implements Serializable {

		private static final long	serialVersionUID	= 1L;

		private String				value;

		public Test(final String value) {
			super();
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

}
