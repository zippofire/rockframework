package br.net.woodstock.rockframework.security.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.sign.SignatureRequest;
import br.net.woodstock.rockframework.security.sign.impl.XMLSigner;
import br.net.woodstock.rockframework.utils.IOUtils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class XMLSignerTest extends TestCase {

	public void test1() throws Exception {
		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder1 = builder1.build();

		XmlDocument document = new XmlDocument("urn:teste", "teste");
		XmlElement root = document.getRoot();
		XmlElement element = root.addElement("urn:nome", "nome");
		element.setData("Lourival Sabino");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		document.write(outputStream);

		byte[] bytes = outputStream.toByteArray();
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(new SignatureRequest(holder1));
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		System.out.println(signer.verify(bytes, signed));
	}

	public void xtest2() throws Exception {
		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino 1");
		builder1.withIssuer("Woodstock Tecnologia 1");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder1 = builder1.build();

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/sign-test.fodt");

		byte[] bytes = IOUtils.toByteArray(fileInputStream);
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(new SignatureRequest(holder1));
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		FileOutputStream fileOutputStream = new FileOutputStream("/home/lourival/Documentos/sign-test-2.fodt");
		fileOutputStream.write(signed);
	}
}
