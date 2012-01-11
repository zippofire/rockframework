package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import br.net.woodstock.rockframework.office.pdf.PDFTSClientInfo;
import br.net.woodstock.rockframework.office.pdf.impl.ITextManager;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsage;
import br.net.woodstock.rockframework.utils.IOUtils;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
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
		URL url = new URL("http://tsa.safelayer.com:8093");
		URLConnection connection = url.openConnection();
		System.out.println(IOUtils.toString(connection.getInputStream()));
	}

	public void xtest2() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		PDFTSClientInfo tsClientInfo = new PDFTSClientInfo("http://tsa.safelayer.com:8093");
		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate, "Testando", "Brasilia-DF", "lourival.sabino.junior@gmail.com", tsClientInfo);

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

		PDFTSClientInfo tsClientInfo = new PDFTSClientInfo("http://tsa.safelayer.com:8093");
		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate, "Testando 2 Assinaturas", "Brasilia-DF", "lourival.sabino.junior@gmail.com", tsClientInfo);

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

	public void test3x1() throws Exception {
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

	public void test3x2() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		PDFManager manager = new ITextManager();
		FileInputStream fileInputStream = new FileInputStream("/tmp/09023708800ebe95.pdf");

		PDFTSClientInfo tsClientInfo = new PDFTSClientInfo("http://tsa.safelayer.com:8093");
		PDFSignatureRequestData data = new PDFSignatureRequestData(privateKey, certificate, "Testando 2 Assinaturas", "Brasilia-DF", "lourival.sabino.junior@gmail.com", tsClientInfo);

		InputStream inputStream = manager.sign(fileInputStream, data);
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/09023708800ebe95-2.pdf");
		IOUtils.copy(inputStream, fileOutputStream);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void test3x3() throws Exception {
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
