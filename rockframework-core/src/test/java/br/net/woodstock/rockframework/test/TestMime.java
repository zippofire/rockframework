package br.net.woodstock.rockframework.test;

import br.net.woodstock.rockframework.utils.FileUtils;
import junit.framework.TestCase;

public class TestMime extends TestCase {

	public void test1() throws Exception {
		System.out.println(FileUtils.getTypeByExtension("pdf"));
		String name = "/opt/dados/documentos/25.rtf";
		System.out.println(name.substring(name.lastIndexOf('.') + 1));

	}

}
