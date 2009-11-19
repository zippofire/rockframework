package net.woodstock.rockframework.test;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.ClassLoaderUtils;

public class TestProperties extends TestCase {

	public void test1() throws Exception {
		InputStream i1 = ClassLoaderUtils.getResourceAsStream("f1.properties");
		InputStream i2 = ClassLoaderUtils.getResourceAsStream("f2.properties");

		Properties properties = new Properties();
		properties.load(i1);
		properties.load(i2);

		Set<Entry<Object, Object>> set = properties.entrySet();
		for (Entry<Object, Object> e : set) {
			System.out.println(e.getKey() + " => " + e.getValue());
		}
	}

}
