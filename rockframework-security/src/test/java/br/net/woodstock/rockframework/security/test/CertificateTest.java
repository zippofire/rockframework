package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.CRLCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.CertificateVerifierChain;
import br.net.woodstock.rockframework.security.cert.impl.DateCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.PKIXCertificateVerifier;
import br.net.woodstock.rockframework.security.util.SecurityUtils;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.30.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtestCreate() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getChain()[0];
		X500Principal principal = certificate.getSubjectX500Principal();
		System.out.println(certificate);
		System.out.println(principal);
		System.out.println(principal.getName(X500Principal.CANONICAL));
	}

	public void testValidateCA() throws Exception {
		FileInputStream inputStream = new FileInputStream("/tmp/cert5.cert");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream.close();

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier(new Certificate[] { certificate }, new Certificate[] { certificate });

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3 });
		boolean ok = certificateVerifier.verify(certificate);
		System.out.println("OK: " + ok);
	}

	public void xtestValidateBC() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder = builder.build();

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier(holder.getChain(), holder.getChain());

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3 });
		boolean ok = certificateVerifier.verify(holder.getChain()[0]);
		System.out.println("OK: " + ok);
	}
}
