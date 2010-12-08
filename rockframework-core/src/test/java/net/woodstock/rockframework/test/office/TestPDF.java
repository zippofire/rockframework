package net.woodstock.rockframework.test.office;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.office.util.PDFUtils;
import net.woodstock.rockframework.utils.IOUtils;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

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

	public void test3() throws Exception {
		InputStream inputStream = new FileInputStream("C:/temp/split.pdf");
		OutputStream outputStream = new FileOutputStream("C:/temp/split-barcode.pdf");
		PdfReader pdfReader = new PdfReader(inputStream);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);
		PdfContentByte contentByte = pdfStamper.getUnderContent(1); // Numero da pagina, a 1 eh um
		BarcodeEAN barcode = new BarcodeEAN();
		barcode.setCodeType(Barcode.EAN13);
		barcode.setCode("9780201615883");
		Image image = barcode.createImageWithBarcode(contentByte, null, null);

		image.setAbsolutePosition(400, 800);
		contentByte.addImage(image);

		pdfStamper.close();

	}

}
