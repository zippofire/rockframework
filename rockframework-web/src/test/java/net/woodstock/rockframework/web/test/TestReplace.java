package net.woodstock.rockframework.web.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import net.woodstock.rockframework.utils.ClassLoaderUtils;
import net.woodstock.rockframework.utils.StringUtils;

public class TestReplace extends TestCase {

	public void testReplace() throws Exception {
		InputStream input = ClassLoaderUtils.getResourceAsStream("rockframework-table.js");
		Map<String, String> map = new HashMap<String, String>();
		map.put("\\$tableId", "tabela_contatos");

		System.out.println(StringUtils.replace(input, map));
	}

}
