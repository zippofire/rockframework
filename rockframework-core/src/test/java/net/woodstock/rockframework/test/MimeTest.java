package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.FileUtils;

public class MimeTest extends TestCase {

	public void test1() throws Exception {
		System.out.println(FileUtils.getExtension("text/plain"));
		System.out.println(FileUtils.getExtension("application/rtf"));

		// System.out.println(MimeUtils.getExtension(FileUtils.getContentType("D:\\SGD\\modelos\\6.tmp")));
	}

}
