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
import br.net.woodstock.rockframework.security.cert.impl.KeyUsage;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.SignerInfo;
import br.net.woodstock.rockframework.security.sign.impl.AsStringSigner;
import br.net.woodstock.rockframework.security.sign.impl.Base64Signer;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.sign.impl.HexSigner;
import br.net.woodstock.rockframework.security.sign.impl.KeyPairSigner;
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
		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/curriculum.pdf");
		byte[] pdf = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();

		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsage.DIGITAL_SIGNATURE, KeyUsage.NON_REPUDIATION, KeyUsage.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();

		SignerInfo signerInfo = new SignerInfo(certificate, privateKey);
		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.p7b");
		fileOutputStream.write(signed);
		fileOutputStream.close();

		System.out.println(signer.verify(pdf, signed));
	}

}
