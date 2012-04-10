package br.net.woodstock.rockframework.core.test.office;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PDFCopyTest extends TestCase {

	public void xtest1() throws Exception {
		InputStream inputStream = new FileInputStream("/tmp/sign2.pdf");
		Document document = new Document();
		FileOutputStream outputStream = new FileOutputStream("/tmp/sign3.pdf");
		PdfCopy writer = new PdfCopy(document, outputStream);

		document.open();

		PdfReader reader = new PdfReader(inputStream);
		int pageCount = reader.getNumberOfPages();
		for (int i = 1; i <= pageCount; i++) {
			PdfImportedPage page = writer.getImportedPage(reader, i);
			writer.addPage(page);
		}
		reader.close();

		document.close();
		writer.close();
	}

}
