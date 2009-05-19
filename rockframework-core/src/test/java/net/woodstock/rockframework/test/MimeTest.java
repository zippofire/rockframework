package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.FileUtils;
import net.woodstock.rockframework.utils.MimeUtils;

public class MimeTest extends TestCase {

	public void test1() throws Exception {
		System.out.println(MimeUtils.getExtension("text/plain"));
		System.out.println(MimeUtils.getExtension("application/rtf"));

		System.out.println(MimeUtils.getExtension(FileUtils.getContentType("D:\\SGD\\modelos\\6.tmp")));
	}

}
