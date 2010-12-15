package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.cache.impl.CacheManagerHolder;

public class CacheTest extends TestCase {

	public void test1() throws Exception {
		CacheManager manager = CacheManagerHolder.getInstance();
		Cache cache = manager.create("test");
		cache.add("1", "Testando");
	}

	public void test2() throws Exception {
		CacheManager manager = CacheManagerHolder.getInstance();
		Cache cache = manager.get("test");
		System.out.println(cache.get("1"));
	}

	public void test3() throws Exception {
		CacheManager manager = CacheManagerHolder.getInstance();
		Cache cache = manager.get("test");
		cache.remove("1");
	}

	public void test4() throws Exception {
		CacheManager manager = CacheManagerHolder.getInstance();
		Cache cache = manager.get("test");
		System.out.println(cache.get("1"));
	}

}
