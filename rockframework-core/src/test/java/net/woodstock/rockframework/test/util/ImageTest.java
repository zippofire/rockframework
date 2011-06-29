package net.woodstock.rockframework.test.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.woodstock.rockframework.image.ExifReader;
import net.woodstock.rockframework.image.Image;
import net.woodstock.rockframework.image.ImageCutTransformer;
import net.woodstock.rockframework.image.ImageResizeTransformer;
import net.woodstock.rockframework.image.ImageTransformer;
import net.woodstock.rockframework.image.ImageType;

public class ImageTest extends TestCase {

	private static final int	WIDTH	= 1024;

	private static final int	HEIGTH	= 768;

	private void print(final int width, final int height) {
		int w = width;
		int h = height;

		int percentWidth = (width * 100) / ImageTest.WIDTH;
		int percentHeight = (height * 100) / ImageTest.HEIGTH;

		if (percentWidth != percentHeight) {
			if (percentWidth > percentHeight) {
				float f = (ImageTest.WIDTH * 1.0f) * ((percentHeight * 1.0f) / 100f);
				w = (int) f;
			} else {
				float f = (ImageTest.HEIGTH * 1.0f) * ((percentWidth * 1.0f) / 100f);
				h = (int) f;
			}
		}

		System.out.println("=================================");
		System.out.println("Width   : " + ImageTest.WIDTH);
		System.out.println("Height  : " + ImageTest.HEIGTH);
		System.out.println("Width 1 : " + width);
		System.out.println("Height 1: " + height);
		System.out.println("Width F : " + w);
		System.out.println("Height F: " + h);
	}

	public void xtest1() throws Exception {
		this.print(800, 600);
		this.print(512, 128);
		this.print(128, 512);
	}

	public void xtest2() throws Exception {
		InputStream inputStream = new FileInputStream("C:/Temp/21035921535559_0.jpg");
		Image image = Image.read(inputStream);
		ImageTransformer transformer = new ImageResizeTransformer(200, 200, true);
		Image resized = transformer.transform(image);

		OutputStream outputStream = new FileOutputStream("C:/Temp/21035921535559_0_resized.jpg");
		resized.write(outputStream, ImageType.JPEG);

		inputStream.close();
		outputStream.close();
	}

	public void xtest3() throws Exception {
		InputStream inputStream = new FileInputStream("C:/Temp/21035921535559_0.jpg");
		Image image = Image.read(inputStream);
		ImageTransformer transformer = new ImageCutTransformer(9999, 9999, 300, 300);
		Image resized = transformer.transform(image);

		OutputStream outputStream = new FileOutputStream("C:/Temp/21035921535559_0_cuted.jpg");
		resized.write(outputStream, ImageType.JPEG);

		inputStream.close();
		outputStream.close();
	}

	public void xtest4() throws Exception {
		InputStream inputStream = new FileInputStream("C:/Temp/21035921535559_0.jpg");
		Image image = Image.read(inputStream);
		OutputStream outputStream = null;

		outputStream = new FileOutputStream("C:/Temp/21035921535559_0.png");
		image.write(outputStream, ImageType.PNG);
		outputStream.close();

		outputStream = new FileOutputStream("C:/Temp/21035921535559_0.gif");
		image.write(outputStream, ImageType.GIF);
		outputStream.close();

		outputStream = new FileOutputStream("C:/Temp/21035921535559_0.bmp");
		image.write(outputStream, ImageType.BMP);
		outputStream.close();

		inputStream.close();

	}

	public void test4() throws Exception {
		InputStream inputStream = new FileInputStream("C:/Temp/exif/canon-ixus.jpg");
		ExifReader reader = new ExifReader(inputStream);
		for (Entry<String, String> entry : reader.getParams().entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
	}

}
