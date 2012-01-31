package br.net.woodstock.rockframework.test.security;

import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.28.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void test1() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		Alias alias = new Alias("mycert");
		Store store = builder.build(alias);
		StoreEntry storeEntry = store.get(alias, StoreEntryType.CERTIFICATE);
		X509Certificate certificate = (X509Certificate) storeEntry.getValue();
		System.out.println(certificate);
	}
}
