package net.woodstock.rockframework.test;

import java.io.Serializable;

import junit.framework.TestCase;
import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.cache.impl.CacheManagerHolder;

public class CacheTest extends TestCase {

	public void test1() throws Exception {
		CacheManager manager = CacheManagerHolder.getInstance();
		Cache cache = manager.create("test");
		Test test = new Test("Testando");
		cache.add("test", test);

		Test tmp = (Test) cache.get("test");
		System.out.println(test.getValue());
		System.out.println(tmp.getValue());
	}

	public static class Test implements Serializable {

		private String	value;

		public Test(final String value) {
			super();
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

}
