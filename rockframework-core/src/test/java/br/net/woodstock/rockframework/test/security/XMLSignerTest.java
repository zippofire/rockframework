package br.net.woodstock.rockframework.test.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.sign.impl.XMLSigner;
import br.net.woodstock.rockframework.utils.IOUtils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class XMLSignerTest extends TestCase {

	public void test1() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		XmlDocument document = new XmlDocument("urn:teste", "teste");
		XmlElement root = document.getRoot();
		XmlElement element = root.addElement("urn:nome", "nome");
		element.setData("Lourival Sabino");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		document.write(outputStream);

		byte[] bytes = outputStream.toByteArray();
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(keyPair, DigestType.SHA1);
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		System.out.println(signer.verify(bytes, signed));
	}

	public void xtest2() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/sign-test.fodt");

		byte[] bytes = IOUtils.toByteArray(fileInputStream);
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(keyPair, DigestType.SHA1);
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		FileOutputStream fileOutputStream = new FileOutputStream("/home/lourival/Documentos/sign-test-2.fodt");
		fileOutputStream.write(signed);
	}
}
