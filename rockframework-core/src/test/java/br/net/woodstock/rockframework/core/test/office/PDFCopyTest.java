package br.net.woodstock.rockframework.core.test.office;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.utils.IOUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFCopyTest extends TestCase {

	public void test1() throws Exception {
		InputStream inputStream = new FileInputStream("/tmp/sign2.pdf");
		byte[] bytes = IOUtils.toByteArray(inputStream);

		byte[] semAssinatura = this.tirarAssinatura(bytes);
		byte[] comResumo = this.gerarResumoAssinatura(bytes, semAssinatura);

		FileOutputStream outputStream = new FileOutputStream("/tmp/sign3.pdf");
		outputStream.write(comResumo);

		inputStream.close();
		outputStream.close();
	}

	public byte[] tirarAssinatura(final byte[] bytes) throws Exception {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfCopy copy = new PdfCopy(document, outputStream);

		document.open();

		PdfReader readerSign = new PdfReader(bytes);
		int pageCount = readerSign.getNumberOfPages();
		for (int i = 1; i <= pageCount; i++) {
			PdfImportedPage page = copy.getImportedPage(readerSign, i);
			copy.addPage(page);
		}

		document.close();
		copy.close();

		return outputStream.toByteArray();
	}

	public byte[] gerarResumoAssinatura(final byte[] original, final byte[] semAssinatura) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfReader readerSign = new PdfReader(original);
		PdfReader readerUnsign = new PdfReader(semAssinatura);

		AcroFields fields = readerSign.getAcroFields();
		List<String> signatures = fields.getSignatureNames();

		if (signatures.size() > 0) {
			int pages = readerUnsign.getNumberOfPages();
			PdfStamper pdfStamper = new PdfStamper(readerUnsign, outputStream);
			pdfStamper.insertPage(pages + 1, readerUnsign.getPageSize(1));

			PdfContentByte content = pdfStamper.getUnderContent(pages + 1);
			PdfDocument document = content.getPdfDocument();

			for (String signature : signatures) {
				PdfPKCS7 pk = fields.verifySignature(signature);
				X509Certificate certificate = pk.getSigningCertificate();
				String location = pk.getLocation();
				String reason = pk.getReason();
				Date date = pk.getSignDate().getTime();

				Paragraph paragraph = new Paragraph();
				paragraph.setAlignment(Element.ALIGN_LEFT);
				Chunk chunk = new Chunk(certificate.getSubjectDN().toString());
				chunk.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)));
				paragraph.add(chunk);
				paragraph.add(new Phrase("\n"));
				document.add(paragraph);

				paragraph = new Paragraph();
				paragraph.setAlignment(Element.ALIGN_LEFT);
				chunk = new Chunk(location);
				chunk.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)));
				paragraph.add(chunk);
				paragraph.add(new Phrase("\n"));
				document.add(paragraph);

				paragraph = new Paragraph();
				paragraph.setAlignment(Element.ALIGN_LEFT);
				chunk = new Chunk(reason);
				chunk.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)));
				paragraph.add(chunk);
				paragraph.add(new Phrase("\n"));
				document.add(paragraph);

				paragraph = new Paragraph();
				paragraph.setAlignment(Element.ALIGN_LEFT);
				chunk = new Chunk(date.toString());
				chunk.setFont(new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)));
				paragraph.add(chunk);
				paragraph.add(new Phrase("\n"));
				document.add(paragraph);
			}
			document.close();
			pdfStamper.close();
		}

		readerSign.close();
		readerUnsign.close();

		return outputStream.toByteArray();
	}

	public void xtest2() throws Exception {
		Document document = new Document();
		FileOutputStream outputStream = new FileOutputStream("/tmp/sign4.pdf");
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		document.open();
		document.newPage();

		document.add(new Paragraph("Teste da Silva Sauro"));

		document.close();
		writer.close();
	}
}
