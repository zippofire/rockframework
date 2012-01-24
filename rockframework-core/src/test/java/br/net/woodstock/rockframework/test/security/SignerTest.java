package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsageType;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.SignRequest;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.impl.AsStringSigner;
import br.net.woodstock.rockframework.security.sign.impl.Base64Signer;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.sign.impl.HexSigner;
import br.net.woodstock.rockframework.security.sign.impl.KeyPairSigner;
import br.net.woodstock.rockframework.security.sign.impl.PDFSigner;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.IOUtils;

public class SignerTest extends TestCase {

	public static final String[]		FREE_TSA		= new String[] { "http://tsa.safelayer.com:8093", "https://tsa.aloaha.com/tsa.asp", "http://dse200.ncipher.com/TSS/HttpTspServer", "http://ca.signfiles.com/TSAServer.aspx" };

	public static final TimeStampClient	TSA_CLIENT_STF	= new STFTimeStampClient("201.49.148.134", 318);

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtest1() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
		KeyPair keyPair = generator.generateKeyPair();

		AsStringSigner signer = new AsStringSigner(new Base64Signer(new KeyPairSigner(keyPair, SignType.SHA1_RSA)));

		FileInputStream inputStream = new FileInputStream("/home/lourival/.m2/repository/br/net/woodstock/rockframework/rockframework-core/1.2.2/rockframework-core-1.2.2.jar");

		byte[] bytes = IOUtils.toByteArray(inputStream);

		String sign = signer.signAsString(bytes);

		System.out.println(sign);

		boolean ok = signer.verifyAsString(bytes, sign);

		System.out.println(ok);

		inputStream.close();

	}

	public void xtest2() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
		KeyPair keyPair = generator.generateKeyPair();

		AsStringSigner signer = new AsStringSigner(new HexSigner(new KeyPairSigner(keyPair, SignType.SHA1_RSA)));

		FileInputStream inputStream = new FileInputStream("/home/lourival/.m2/repository/br/net/woodstock/rockframework/rockframework-core/1.2.2/rockframework-core-1.2.2.jar");

		byte[] bytes = IOUtils.toByteArray(inputStream);

		String sign = signer.signAsString(bytes);

		System.out.println(sign);

		boolean ok = signer.verifyAsString(bytes, sign);

		System.out.println(ok);

		inputStream.close();

	}

	public void xtest3() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/2011-10.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		CertificateBuilder builder1 = new CertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();

		CertificateBuilder builder2 = new CertificateBuilder("Lourival Sabino 2");
		builder2.withIssuer("Woodstock Tecnologia 2");
		builder2.withV3Extensions(true);
		builder2.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder2 = builder2.build();

		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder1, holder2 });
		signerInfo.setTimeStampClient(timeStampClient);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.p7b");
		fileOutputStream.write(signed);
		fileOutputStream.close();

		System.out.println(signer.verify(pdf, signed));
	}

	public void xtest4() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		CertificateBuilder builder1 = new CertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();

		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder1 });
		signerInfo.setTimeStampClient(timeStampClient);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf.p7s");
		fileOutputStream.write(signed);
		fileOutputStream.close();
	}

	public void test5() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		CertificateBuilder builder1 = new CertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();

		CertificateBuilder builder2 = new CertificateBuilder("Lourival Sabino 2");
		builder2.withIssuer("Woodstock Tecnologia 2");
		builder2.withV3Extensions(true);
		builder2.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder2 = builder2.build();

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setLocation("Location");
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setReason("Reason");
		signerInfo.setCertificates(new CertificateHolder[] { holder1, holder2 });
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/signed2.pdf");
		fileOutputStream.write(signed);
		fileOutputStream.close();

		Signature[] signatures = signer.getSignatures(signed);
		for (Signature s : signatures) {
			System.out.println(s.getLocation());
			for (Signatory ss : s.getSigners()) {
				System.out.println("\t" + ss.getIssuer());
				System.out.println("\t" + ss.getSubject());
			}
		}
	}
}
