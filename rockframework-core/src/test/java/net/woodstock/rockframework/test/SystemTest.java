package net.woodstock.rockframework.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import junit.framework.TestCase;

public class SystemTest extends TestCase {

	@SuppressWarnings("rawtypes")
	public void test1() throws Exception {
		Properties properties = System.getProperties();
		List<String> list = new ArrayList<String>();
		for (Entry entry : properties.entrySet()) {
			list.add(entry.getKey() + " => " + entry.getValue());
		}

		Collections.sort(list);

		for (String s : list) {
			System.out.println(s);
		}
	}

}
