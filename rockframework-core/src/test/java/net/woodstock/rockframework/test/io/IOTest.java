package net.woodstock.rockframework.test.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.IOUtils;

public class IOTest extends TestCase {

	private static final String	URL		= "http://localhost:8080/cache-test/video.mp4";

	private static final String	FILE	= "C:/Temp/66_anos_tse.mp4";

	public void testURL1() throws Exception {
		URL url = new URL(IOTest.URL);
		InputStream inputStream = url.openStream();
		OutputStream outputStream = new FileOutputStream("C:/Temp/testURL1.mp4");

		IOUtils.copy(inputStream, outputStream);

		inputStream.close();
		outputStream.close();
	}

	public void testURL2() throws Exception {
		URL url = new URL(IOTest.URL);
		InputStream inputStream = url.openStream();
		OutputStream outputStream = new FileOutputStream("C:/Temp/testURL2.mp4");

		IOUtils.copy(inputStream, outputStream);

		inputStream.close();
		outputStream.close();
	}

	public void xtestFile1() throws Exception {
		InputStream inputStream = new FileInputStream(IOTest.FILE);
		OutputStream outputStream = new FileOutputStream("C:/Temp/testFile1.mp4");

		IOUtils.copy(inputStream, outputStream);

		inputStream.close();
		outputStream.close();
	}

	public void xtestFile2() throws Exception {
		InputStream inputStream = new FileInputStream(IOTest.FILE);
		OutputStream outputStream = new FileOutputStream("C:/Temp/testFile2.mp4");

		IOUtils.copy(inputStream, outputStream);

		inputStream.close();
		outputStream.close();
	}

}
