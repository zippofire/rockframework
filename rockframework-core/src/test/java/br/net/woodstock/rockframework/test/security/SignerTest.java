package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.PKCS7SignatureRequest;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.sign.impl.AsStringSigner;
import br.net.woodstock.rockframework.security.sign.impl.Base64Signer;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.sign.impl.HexSigner;
import br.net.woodstock.rockframework.security.sign.impl.KeyPairSigner;
import br.net.woodstock.rockframework.security.sign.impl.PDFSigner;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.IOUtils;

public class SignerTest extends TestCase {

	public static final String[]		FREE_TSA		= new String[] { "http://tsa.safelayer.com:8093", "https://tsa.aloaha.com/tsa.asp", "http://dse200.ncipher.com/TSS/HttpTspServer", "http://ca.signfiles.com/TSAServer.aspx" };

	public static final TimeStampClient	TSA_CLIENT_STF	= new STFTimeStampClient("201.49.148.134", 318);

	// public static final TimeStampClient TSA_CLIENT_STF = new STFTimeStampClient("200.143.0.158", 318);

	static {
		//System.setProperty("http.proxyHost", "10.28.1.12");
		//System.setProperty("http.proxyPort", "8080");
		//System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		//System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtest1() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
		KeyPair keyPair = generator.generateKeyPair();

		AsStringSigner signer = new AsStringSigner(new Base64Signer(new KeyPairSigner(keyPair, SignatureType.SHA1_RSA)));

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

		AsStringSigner signer = new AsStringSigner(new HexSigner(new KeyPairSigner(keyPair, SignatureType.SHA1_RSA)));

		FileInputStream inputStream = new FileInputStream("/home/lourival/.m2/repository/br/net/woodstock/rockframework/rockframework-core/1.2.2/rockframework-core-1.2.2.jar");

		byte[] bytes = IOUtils.toByteArray(inputStream);

		String sign = signer.signAsString(bytes);

		System.out.println(sign);

		boolean ok = signer.verifyAsString(bytes, sign);

		System.out.println(ok);

		inputStream.close();

	}

	public void xtest3() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder1 = builder1.build();

		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(holder1);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
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

		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder1 = builder1.build();

		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(holder1);
		signerInfo.setTimeStampClient(timeStampClient);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf.p7s");
		fileOutputStream.write(signed);
		fileOutputStream.close();
	}

	public void xtest5() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign1.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder.withIssuer("Woodstock Tecnologia 1");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder = builder.build();

		// TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		TimeStampClient timeStampClient = null;
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(holder);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign2.pdf");
		fileOutputStream.write(signed);
		fileOutputStream.close();

		Signature[] signatures = signer.getSignatures(signed);
		for (Signature s : signatures) {
			System.out.println(s.getLocation());
			for (Signatory ss : s.getSignatories()) {
				System.out.println("\t" + ss.getIssuer());
				System.out.println("\t" + ss.getSubject());
			}
		}
	}

	public void xtest6() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder = builder.build();

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(holder);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(IOUtils.toByteArray(fileInputStream));
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf");
		fileOutputStream.write(signed);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest6x1() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino 2");
		builder.withIssuer("TSE");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder = builder.build();

		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(holder);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(IOUtils.toByteArray(fileInputStream));
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign2.pdf");
		fileOutputStream.write(signed);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest7() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.JKS);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO.jks"), "storepasswd");

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/teste.pdf");

		// TimeStampClient timeStampClient = TSA_CLIENT_STF;
		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(new Alias[] { new PasswordAlias("lourival sabino", "lourival") }, store);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(IOUtils.toByteArray(fileInputStream));
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/teste-demo.pdf");
		fileOutputStream.write(signed);

		fileInputStream.close();
		fileOutputStream.close();
	}

	public void xtest8() throws Exception {
		PDFSigner signer = new PDFSigner(null);
		FileInputStream inputStream = new FileInputStream("/tmp/sign2.pdf");
		Signature[] signatures = signer.getSignatures(IOUtils.toByteArray(inputStream));
		for (Signature signature : signatures) {
			System.out.println(signature.getLocation());
			for (Signatory signatory : signature.getSignatories()) {
				System.out.println("\tSubject: " + signatory.getSubject());
				System.out.println("\tIssuer : " + signatory.getIssuer());
			}
			TimeStamp timeStamp = signature.getTimeStamp();
			if (timeStamp != null) {
				System.out.println("Salvando o timeStamp");
				FileOutputStream outputStream = new FileOutputStream("/tmp/teste-demo.pdf.p7m");
				outputStream.write(timeStamp.getEncoded());
				outputStream.close();
			}
			System.out.println("Salvando a assinatura");
			FileOutputStream outputStream = new FileOutputStream("/tmp/teste-demo.pdf.p7s");
			outputStream.write(signature.getEncoded());
			outputStream.close();
		}
		inputStream.close();
	}
	
	public void test9() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.JKS);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO.jks"), "storepasswd");

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/teste.pdf");

		// TimeStampClient timeStampClient = TSA_CLIENT_STF;
		TimeStampClient timeStampClient = new URLTimeStampClient("http://localhost:18080/carimbador-web/carimbador");
		PKCS7SignatureRequest signerInfo = new PKCS7SignatureRequest(new Alias[] { new PasswordAlias("lourival sabino", "lourival") }, store);
		signerInfo.setContactInfo("ConcactInfo");
		signerInfo.setLocation("Location");
		signerInfo.setName("Lourival Sabino");
		signerInfo.setReason("Reason");
		signerInfo.setTimeStampClient(timeStampClient);

		PDFSigner signer = new PDFSigner(signerInfo);

		byte[] signed = signer.sign(IOUtils.toByteArray(fileInputStream));
		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/teste-demo-local.pdf");
		fileOutputStream.write(signed);

		fileInputStream.close();
		fileOutputStream.close();
	}

}
