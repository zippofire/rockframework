package net.woodstock.rockframework.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.io.FileInfo;

public class FileTest extends TestCase {

	public void test1() throws Exception {
		System.out.println("======================================");
		File file = new File("C:/Temp/split.pdf");
		FileInfo fileInfo = new FileInfo(file);

		System.out.println(fileInfo.getExtension());
		System.out.println(fileInfo.getMimeType());
		System.out.println(fileInfo.getName());
		System.out.println(fileInfo.getParent());
		System.out.println(fileInfo.getPath());
		System.out.println(fileInfo.getSize());
	}

	public void test2() throws Exception {
		System.out.println("======================================");
		FileInfo fileInfo = new FileInfo("C:/Temp/split.pdf");

		System.out.println(fileInfo.getExtension());
		System.out.println(fileInfo.getMimeType());
		System.out.println(fileInfo.getName());
		System.out.println(fileInfo.getParent());
		System.out.println(fileInfo.getPath());
		System.out.println(fileInfo.getSize());
	}

	public void test3() throws Exception {
		System.out.println("======================================");
		File file = new File("C:/Temp/split.pdf");
		FileInfo fileInfo = new FileInfo(file);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);

		oos.writeObject(file);
		oos.close();
		baos.close();
		int size1 = baos.toByteArray().length;

		baos = new ByteArrayOutputStream();
		oos = new ObjectOutputStream(baos);

		oos.writeObject(fileInfo);
		oos.close();
		baos.close();
		int size2 = baos.toByteArray().length;

		System.out.println("File    : " + size1);
		System.out.println("FileInfo: " + size2);
	}

}
