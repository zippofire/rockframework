package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.office.pdf.PDFManager;
import br.net.woodstock.rockframework.office.pdf.PDFSignature;
import br.net.woodstock.rockframework.office.pdf.PDFSignatureRequestData;
import br.net.woodstock.rockframework.office.pdf.PDFSigner;
import br.net.woodstock.rockframework.office.pdf.impl.ITextManager;
import br.net.woodstock.rockframework.office.pdf.impl.ITextSTFSocketTSClient;
import br.net.woodstock.rockframework.office.pdf.impl.URLITextTSClient;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsage;
import br.net.woodstock.rockframework.utils.IOUtils;

import com.itextpdf.text.pdf.TSAClient;

public class CertificateTest extends TestCase {

	private static final String[]	FREE_TSA		= new String[] { "http://tsa.safelayer.com:8093", "https://tsa.aloaha.com/tsa.asp", "http://dse200.ncipher.com/TSS/HttpTspServer" };

	private static final TSAClient	TSA_CLIENT_STF	= new ITextSTFSocketTSClient("201.49.148.134", 318);

	private static final TSAClient	TSA_CLIENT_FREE;

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		try {
			TSA_CLIENT_FREE = new URLITextTSClient(FREE_TSA[0]);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public void xtest1() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("TSE");
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

	public void test2() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTsaClient(CertificateTest.TSA_CLIENT_FREE);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest2x1() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");

		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTsaClient(CertificateTest.TSA_CLIENT_STF);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
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
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/09023708800ebe95.pdf");

		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate);
		data.setReason("Testando");
		data.setLocation("Brasilia-DF");
		data.setContactInfo("lourival.sabino.junior@gmail.com");
		data.setTsaClient(CertificateTest.TSA_CLIENT_STF);

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

}
