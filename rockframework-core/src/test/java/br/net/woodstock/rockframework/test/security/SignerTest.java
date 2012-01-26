package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
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
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.IOUtils;

public class SignerTest extends TestCase {

	public static final String[]		FREE_TSA		= new String[] { "http://tsa.safelayer.com:8093", "https://tsa.aloaha.com/tsa.asp", "http://dse200.ncipher.com/TSS/HttpTspServer", "http://ca.signfiles.com/TSAServer.aspx" };

	//public static final TimeStampClient	TSA_CLIENT_STF	= new STFTimeStampClient("201.49.148.134", 318);

	public static final TimeStampClient	TSA_CLIENT_STF	= new STFTimeStampClient("200.143.0.158", 318);
	
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

		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();

		BouncyCastleCertificateBuilder builder2 = new BouncyCastleCertificateBuilder("Lourival Sabino 2");
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

		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
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

	public void xtest5() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/tmp/sign1.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder1 });
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
			for (Signatory ss : s.getSigners()) {
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
		CertificateHolder holder = builder.build();

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder });
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
		CertificateHolder holder = builder.build();

		FileInputStream fileInputStream = new FileInputStream("/tmp/sign.pdf");

		TimeStampClient timeStampClient = TSA_CLIENT_STF;
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder });
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

	public void test7() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.JKS);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO.jks"), "storepasswd");
		StoreEntry entryCert = store.get(new StoreEntry("lourival sabino", "lourival", null, StoreEntryType.CERTIFICATE));
		StoreEntry entryKey = store.get(new StoreEntry("lourival sabino", "lourival", null, StoreEntryType.PRIVATE_KEY));

		CertificateHolder holder = new CertificateHolder((Certificate) entryCert.getValue(), (PrivateKey) entryKey.getValue());

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");

		//TimeStampClient timeStampClient = TSA_CLIENT_STF;
		TimeStampClient timeStampClient =  new URLTimeStampClient("http://tsa.safelayer.com:8093");
		SignRequest signerInfo = new SignRequest();
		signerInfo.setCertificates(new CertificateHolder[] { holder });
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

	public void test8() throws Exception {
		PDFSigner signer = new PDFSigner(null);
		FileInputStream inputStream = new FileInputStream("/tmp/sign2.pdf");
		Signature[] signatures = signer.getSignatures(IOUtils.toByteArray(inputStream));
		for (Signature signature : signatures) {
			System.out.println(signature.getLocation());
			for (Signatory signatory : signature.getSigners()) {
				System.out.println("\t" + signatory.getSubject());
				System.out.println("\t" + signatory.getIssuer());
			}
			TimeStamp timeStamp = signature.getTimeStamp();
			if (timeStamp != null) {
				System.out.println("Salvando o timeStamp");
				FileOutputStream outputStream = new FileOutputStream("/tmp/sign2.pdf.p7m");
				outputStream.write(timeStamp.getEncoded());
				outputStream.close();
			}
			System.out.println("Salvando a assinatura");
			FileOutputStream outputStream = new FileOutputStream("/tmp/sign2.pdf.p7s");
			outputStream.write(signature.getEncoded());
			outputStream.close();
		}
		inputStream.close();
	}

}
