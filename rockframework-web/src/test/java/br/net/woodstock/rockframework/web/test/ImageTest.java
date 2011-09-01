package br.net.woodstock.rockframework.web.test;

import java.io.FileOutputStream;

import br.net.woodstock.rockframework.web.captcha.CaptchaImage;

import junit.framework.TestCase;

public class ImageTest extends TestCase {

	public void test1() throws Exception {
		CaptchaImage image = new CaptchaImage("Rock Framework");
		byte[] bytes = image.getBytes();
		FileOutputStream fos = new FileOutputStream("C:/Temp/image.gif");
		fos.write(bytes);
		fos.close();
	}

}
