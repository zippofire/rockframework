package br.net.woodstock.rockframework.test.security;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.office.pdf.PDFManager;
import br.net.woodstock.rockframework.office.pdf.PDFSignature;
import br.net.woodstock.rockframework.office.pdf.PDFSigner;
import br.net.woodstock.rockframework.office.pdf.impl.ITextManager;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsage;
import br.net.woodstock.rockframework.security.sign.impl.PDFSignData;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.HexUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class CertificateTest extends TestCase {

	private static final String[]			FREE_TSA		= new String[] { "http://tsa.safelayer.com:8093", "https://tsa.aloaha.com/tsa.asp", "http://dse200.ncipher.com/TSS/HttpTspServer", "http://ca.signfiles.com/TSAServer.aspx" };

	private static final TimeStampClient	TSA_CLIENT_STF	= new STFTimeStampClient("201.49.148.134", 318);

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtest1() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		System.out.println(certificate);
	}

	public void xtest1x1() throws Exception {
		URL url = new URL(CertificateTest.FREE_TSA[0]);
		URLConnection connection = url.openConnection();
		System.out.println(IOUtils.toString(connection.getInputStream()));
	}

	public void xtest2() throws Exception {
		CoreLog.getInstance().getLog().info("Test 2");

		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		PDFSignData data = new PDFSignData(certificate, privateKey);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTimeStampClient(CertificateTest.TSA_CLIENT_STF);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest2x1() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");

		PDFSignData data = new PDFSignData(certificate, privateKey);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTimeStampClient(CertificateTest.TSA_CLIENT_STF);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest2x2() throws Exception {
		CoreLog.getInstance().getLog().info("Test 2x2");

		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		PDFSignData data = new PDFSignData(certificate, privateKey);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");

		for (int i = 0; i < CertificateTest.FREE_TSA.length; i++) {
			try {
				String url = CertificateTest.FREE_TSA[i];

				System.out.println(url);

				data.setTimeStampClient(new URLTimeStampClient(url));

				InputStream inputStream = manager.sign(new ByteArrayInputStream(pdf), data);
				FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign" + i + ".pdf");
				IOUtils.copy(inputStream, fileOutputStream);

				fileOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void xtest3() throws Exception {
		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");
		Collection<PDFSignature> signatures = manager.getSignatures(fileInputStream);

		for (PDFSignature signature : signatures) {
			System.out.println(signature.getLocation());
			System.out.println(signature.getReason());
			System.out.println(signature.getDate());
			for (PDFSigner signer : signature.getSigners()) {
				System.out.println("\t" + signer.getSubject());
				System.out.println("\t" + signer.getIssuer());
			}
		}

		fileInputStream.close();
	}

	public void xtest3x1() throws Exception {
		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/09023708800ebe95.pdf");
		Collection<PDFSignature> signatures = manager.getSignatures(fileInputStream);

		for (PDFSignature signature : signatures) {
			System.out.println(signature.getLocation());
			System.out.println(signature.getReason());
			System.out.println(signature.getDate());
			for (PDFSigner signer : signature.getSigners()) {
				System.out.println("\t" + signer.getSubject());
				System.out.println("\t" + signer.getIssuer());
			}
		}

		fileInputStream.close();
	}

	public void xtest3x2() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/09023708800ebe95.pdf");

		PDFSignData data = new PDFSignData(certificate, privateKey);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTimeStampClient(CertificateTest.TSA_CLIENT_STF);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/09023708800ebe95-2.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest3x3() throws Exception {
		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/09023708800ebe95-2.pdf");
		Collection<PDFSignature> signatures = manager.getSignatures(fileInputStream);

		for (PDFSignature signature : signatures) {
			System.out.println(signature.getLocation());
			System.out.println(signature.getReason());
			System.out.println(signature.getDate());
			for (PDFSigner signer : signature.getSigners()) {
				System.out.println("\t" + signer.getSubject());
				System.out.println("\t" + signer.getIssuer());
			}
		}

		fileInputStream.close();
	}

	public void xtest4() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");
		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		TimeStamp timeStamp = timeStampClient.getTimeStamp(IOUtils.toByteArray(fileInputStream));
		fileInputStream.close();

		System.out.println("Date   : " + timeStamp.getDate());
		System.out.println("Hash   : " + HexUtils.toHexString(timeStamp.getHash()));
		System.out.println("Content: " + new String(timeStamp.getContent()));
		System.out.println("Content: " + HexUtils.toHexString(timeStamp.getContent()));
		System.out.println("Nonce  : " + timeStamp.getNonce());
		System.out.println("SN     : " + timeStamp.getSerialNumber());
	}

	public void xtest5() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/teste.pdf");

		PDFSignData data = new PDFSignData(certificate, privateKey);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTimeStampClient(CertificateTest.TSA_CLIENT_STF);

		InputStream inputStream = manager.sign(fileInputStream, data);
		byte[] pdfSigned = IOUtils.toByteArray(inputStream);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		fileOutputStream.write(pdfSigned);

		fileInputStream.close();
		fileOutputStream.close();

	}

	public void xtest5x1() throws Exception {
		CoreLog.getInstance().getLog().info("Test 2");

		FileInputStream inputStream = new FileInputStream("/tmp/sign.pdf");

		PdfReader pdfReader = new PdfReader(inputStream);
		FileOutputStream outputStream = new FileOutputStream("/tmp/sign-m.pdf");

		PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);

		PdfContentByte contentByte = pdfStamper.getUnderContent(1);
		
		contentByte.setColorFill(Color.BLACK);
		contentByte.setColorStroke(Color.BLACK);
		contentByte.setLineWidth(0.3f);
		contentByte.rectangle(10, 670, 400, 100);
		contentByte.fillStroke();
		contentByte.stroke();
		contentByte.closePathFillStroke();

		contentByte.beginText();
		contentByte.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN, Charset.defaultCharset().name(), true), 12f);
		contentByte.setColorFill(Color.WHITE);
		contentByte.showTextAligned(PdfContentByte.ALIGN_LEFT, "Novo Texto do documento", 60, 760, 0);
		contentByte.endText();

		pdfStamper.close();

	}
}
