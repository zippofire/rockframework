package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.IOUtils;

public class TimeStampTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void test1() throws Exception {
		TimeStampClient client = new STFTimeStampClient("201.49.148.134", 318);
		FileInputStream inputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] input = IOUtils.toByteArray(inputStream);
		TimeStamp timeStamp = client.getTimeStamp(input);
		byte[] bytes = timeStamp.getEncoded();
		FileOutputStream outputStream = new FileOutputStream("/tmp/curriculum2.pdf.p7s");
		outputStream.write(bytes);
		outputStream.close();
	}

	public void xtest2() throws Exception {
		TimeStampClient client = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		FileInputStream inputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] input = IOUtils.toByteArray(inputStream);
		TimeStamp timeStamp = client.getTimeStamp(input);
		byte[] bytes = timeStamp.getEncoded();
		FileOutputStream outputStream = new FileOutputStream("/tmp/test2.tsr");
		outputStream.write(bytes);
		outputStream.close();
	}

	public void xtest3() throws Exception {
		TimeStampClient client = new URLTimeStampClient("http://ca.signfiles.com/TSAServer.aspx");
		FileInputStream inputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] input = IOUtils.toByteArray(inputStream);
		TimeStamp timeStamp = client.getTimeStamp(input);
		byte[] bytes = timeStamp.getEncoded();
		FileOutputStream outputStream = new FileOutputStream("/tmp/test3.tsr");
		outputStream.write(bytes);
		outputStream.close();
	}

}