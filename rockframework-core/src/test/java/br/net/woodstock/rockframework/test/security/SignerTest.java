package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsageType;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.PKCS7SignerInfo;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.SignerInfo;
import br.net.woodstock.rockframework.security.sign.impl.AsStringSigner;
import br.net.woodstock.rockframework.security.sign.impl.Base64Signer;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.sign.impl.HexSigner;
import br.net.woodstock.rockframework.security.sign.impl.KeyPairSigner;
import br.net.woodstock.rockframework.security.timestamp.impl.STFTimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.IOUtils;

public class SignerTest extends TestCase {

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

	public void test3() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/2011-10.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		CertificateBuilder builder1 = new CertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder1 = builder1.build();
		X509Certificate certificate1 = (X509Certificate) holder1.getCertificate();
		PrivateKey privateKey1 = holder1.getPrivateKey();
		SignerInfo signerInfo1 = new SignerInfo(certificate1, privateKey1);

		CertificateBuilder builder2 = new CertificateBuilder("Lourival Sabino 2");
		builder2.withIssuer("Woodstock Tecnologia 2");
		builder2.withV3Extensions(true);
		builder2.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder2 = builder2.build();
		X509Certificate certificate2 = (X509Certificate) holder2.getCertificate();
		PrivateKey privateKey2 = holder2.getPrivateKey();
		SignerInfo signerInfo2 = new SignerInfo(certificate2, privateKey2);

		PKCS7SignerInfo signerInfo = new PKCS7SignerInfo(new SignerInfo[] { signerInfo1, signerInfo2 });
		//signerInfo.setTimeStampClient(new STFTimeStampClient("201.49.148.134", 318));
		signerInfo.setTimeStampClient(new URLTimeStampClient("http://tsa.safelayer.com:8093"));

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
		X509Certificate certificate1 = (X509Certificate) holder1.getCertificate();
		PrivateKey privateKey1 = holder1.getPrivateKey();
		SignerInfo signerInfo1 = new SignerInfo(certificate1, privateKey1);

		PKCS7SignerInfo signerInfo = new PKCS7SignerInfo(new SignerInfo[] { signerInfo1 });
		signerInfo.setTimeStampClient(new STFTimeStampClient("201.49.148.134", 318));

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf.p7s");
		fileOutputStream.write(signed);
		fileOutputStream.close();
	}

}
