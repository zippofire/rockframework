package net.woodstock.rockframework.test.office;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.office.util.PDFUtils;
import net.woodstock.rockframework.utils.IOUtils;

public class TestPDF extends TestCase {

	public void xtest1() throws Exception {
		InputStream input = new FileInputStream("C:/Documentos/j931_01.pdf");
		InputStream tmp = PDFUtils.cut(input, 3, 8);

		FileOutputStream output = new FileOutputStream("C:/temp/split.pdf");
		IOUtils.copy(tmp, output);

		input.close();
		tmp.close();
		output.close();
	}

	public void xtest2() throws Exception {
		InputStream input1 = new FileInputStream("C:/Documentos/j931_01.pdf");
		InputStream input2 = new FileInputStream("C:/Documentos/j931_02.pdf");
		InputStream tmp = PDFUtils.merge(new InputStream[] { input1, input2 });

		FileOutputStream output = new FileOutputStream("C:/temp/split.pdf");
		IOUtils.copy(tmp, output);

		input1.close();
		input2.close();
		tmp.close();
		output.close();
	}

}
