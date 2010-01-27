package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.FileUtils;

public class TestMime extends TestCase {

	public void test1() throws Exception {
		System.out.println(FileUtils.getMimeType("pdf"));
		String name = "/opt/dados/documentos/25.rtf";
		System.out.println(name.substring(name.lastIndexOf('.') + 1));

	}

}
