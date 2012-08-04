package br.net.woodstock.rockframework.security.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.sign.SignatureParameters;
import br.net.woodstock.rockframework.security.sign.impl.XMLSigner;
import br.net.woodstock.rockframework.utils.IOUtils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class XMLSignerTest extends TestCase {

	public void test1() throws Exception {
		CertificateBuilderRequest request = new CertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder1 = BouncyCastleCertificateBuilder.getInstance().build(request);

		XmlDocument document = new XmlDocument("teste");
		XmlElement root = document.getRoot();
		XmlElement element = root.addElement("nome");
		element.setData("Lourival Sabino");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		document.write(outputStream);

		byte[] bytes = outputStream.toByteArray();
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(new SignatureParameters(holder1));
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		System.out.println(signer.verify(bytes, signed));
	}

	public void xtest2() throws Exception {
		CertificateBuilderRequest request = new CertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder1 = BouncyCastleCertificateBuilder.getInstance().build(request);

		FileInputStream fileInputStream = new FileInputStream("/home/lourival/Documentos/sign-test.fodt");

		byte[] bytes = IOUtils.toByteArray(fileInputStream);
		System.out.println(new String(bytes));

		XMLSigner signer = new XMLSigner(new SignatureParameters(holder1));
		byte[] signed = signer.sign(bytes);

		System.out.println(new String(signed));

		FileOutputStream fileOutputStream = new FileOutputStream("/home/lourival/Documentos/sign-test-2.fodt");
		fileOutputStream.write(signed);
	}
}
