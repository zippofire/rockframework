package net.woodstock.rockframework.web.test;

import java.io.FileOutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.web.captcha.CaptchaImage;

public class ImageTest extends TestCase {

	public void test1() throws Exception {
		CaptchaImage image = new CaptchaImage("THIAGO GOULART");
		byte[] bytes = image.getBytes();
		FileOutputStream fos = new FileOutputStream("D:/image.jpeg");
		fos.write(bytes);
		fos.close();
	}

}
