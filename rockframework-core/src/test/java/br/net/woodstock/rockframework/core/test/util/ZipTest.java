package br.net.woodstock.rockframework.core.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.utils.IOUtils;

public class ZipTest extends TestCase {

	public void test1() throws Exception {
		String[] files = new String[] { "/tmp/image1.png", "/tmp/image2.png", "/tmp/image3.png" };
		FileOutputStream outputStream = new FileOutputStream("/tmp/zip.zip");
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		for (String s : files) {
			File file = new File(s);
			FileInputStream inputStream = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOutputStream.putNextEntry(zipEntry);
			IOUtils.copy(inputStream, zipOutputStream);
			zipOutputStream.closeEntry();
		}
		zipOutputStream.close();
		outputStream.close();
	}

}
