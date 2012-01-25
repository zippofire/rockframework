package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.store.impl.XMLStore;

public class KeystoreTest extends TestCase {

	public void test1() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.JKS);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO.jks"), "storepasswd");
		for (StoreEntry entry : store.aliases()) {
			System.out.println(entry.getType() + " - " + entry.getAlias() + " - " + entry.getValue());
		}

		StoreEntry entry = store.get(new StoreEntry("lourival sabino", "lourival", null, StoreEntryType.CERTIFICATE));
		System.out.println(entry.getValue());

		entry = store.get(new StoreEntry("lourival sabino", "lourival", null, StoreEntryType.PRIVATE_KEY));
		System.out.println(entry.getValue());
	}

	public void xtest6() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		CertificateHolder holder = builder.build();
		X509Certificate certificate = (X509Certificate) holder.getCertificate();
		PrivateKey privateKey = holder.getPrivateKey();
		PublicKey publicKey = certificate.getPublicKey();

		Store store = new XMLStore();
		store.add(new StoreEntry("cert", null, certificate, StoreEntryType.CERTIFICATE));
		store.add(new StoreEntry("priv", null, privateKey, StoreEntryType.PRIVATE_KEY));
		store.add(new StoreEntry("pub", null, publicKey, StoreEntryType.PUBLIC_KEY));

		store.write(new FileOutputStream("/tmp/cert.xml"), null);
	}

	public void xtest6x1() throws Exception {
		Store store = new XMLStore();
		store.read(new FileInputStream("/tmp/cert.xml"), null);

		store.write(System.out, null);
		for (StoreEntry entry : store.aliases()) {
			System.out.println(entry.getType() + " - " + entry.getAlias() + " - " + entry.getValue());
		}
	}

}
