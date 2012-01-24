package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.impl.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.KeyUsageType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.impl.XMLStore;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtest1() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		System.out.println(certificate);
	}

	public void xtest6() throws Exception {
		CertificateBuilder builder = new CertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();
		PublicKey publicKey = certificate.getPublicKey();

		Store store = new XMLStore();
		store.addCertificate(certificate, "cert");
		store.addPrivateKey(privateKey, "priv");
		store.addPublicKey(publicKey, "pub");

		store.write(new FileOutputStream("/tmp/cert.xml"));
	}

	public void xtest6x1() throws Exception {
		Store store = new XMLStore();
		store.read(new FileInputStream("/tmp/cert.xml"));

		store.write(System.out);
		for (Certificate certificate : store.getCertificates()) {
			System.out.println(certificate);
		}
		for (PrivateKey privateKey : store.getPrivateKeys()) {
			System.out.println(privateKey);
		}
		for (PublicKey publicKey : store.getPublicKeys()) {
			System.out.println(publicKey);
		}
	}
}
