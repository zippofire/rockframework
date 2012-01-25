package br.net.woodstock.rockframework.test.security;

import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtest1() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		System.out.println(certificate);
	}
}
