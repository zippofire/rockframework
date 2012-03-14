package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.store.impl.XMLStore;
import br.net.woodstock.rockframework.security.store.utils.StoreUtils;
import br.net.woodstock.rockframework.utils.CollectionUtils;

public class KeystoreTest extends TestCase {

	public void xtest1() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.JKS);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO.jks"), "storepasswd");
		for (Alias alias : store.aliases()) {
			System.out.println(alias.getName());
		}

		PrivateKeyEntry entry = (PrivateKeyEntry) store.get(new PasswordAlias("lourival sabino", "lourival"), StoreEntryType.PRIVATE_KEY);
		System.out.println("Private Key: " + entry.getValue());
		System.out.println("Certificate: " + entry.getChain()[0]);
	}

	public void xtest2x0() throws Exception {
		KeyStore keyStore = KeyStore.getInstance(KeyStoreType.PKCS12.getType());
		keyStore.load(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO2.pfx"), "storepasswd".toCharArray());

		Collection<String> aliases = CollectionUtils.toCollection(keyStore.aliases());
		for (String alias : aliases) {
			System.out.println(alias);

			X509Certificate certificate = (X509Certificate) keyStore.getCertificate("lourival sabino");
			System.out.println("Principal: " + certificate.getSubjectDN());

			Certificate[] chain = keyStore.getCertificateChain(alias);
			for (Certificate c : chain) {
				X509Certificate xc = (X509Certificate) c;
				System.out.println("Chain: " + xc.getSubjectDN());
			}
		}
	}

	public void xtest2x1() throws Exception {
		JCAStore store = new JCAStore(KeyStoreType.PKCS12);
		store.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO2.pfx"), "storepasswd");
		for (Alias alias : store.aliases()) {
			System.out.println("Alias: '" + alias.getName() + "'");
		}

		PrivateKeyEntry entry = (PrivateKeyEntry) store.get(new PasswordAlias("lourival sabino", "lourival"), StoreEntryType.PRIVATE_KEY);
		System.out.println("Private Key: " + entry.getValue());
		System.out.println("Certificate: " + entry.getChain()[0]);
	}

	public void xtest6() throws Exception {
		BouncyCastleCertificateBuilder builder = new BouncyCastleCertificateBuilder("Lourival Sabino da Silva Junior");
		builder.withIssuer("Woodstock Tecnologia");
		builder.withV3Extensions(true);
		builder.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder = builder.build();

		Store store = new XMLStore();
		store.add(new PrivateKeyEntry(new PasswordAlias("mykey", "mypasswd"), holder.getPrivateKey(), holder.getChain()));

		store.write(new FileOutputStream("/tmp/cert.xml"), null);
	}

	public void xtest6x1() throws Exception {
		Store store = new XMLStore();
		store.read(new FileInputStream("/tmp/cert.xml"), null);

		store.write(System.out, null);
	}

	public void test7() throws Exception {
		JCAStore store1 = new JCAStore(KeyStoreType.PKCS12);
		store1.read(new FileInputStream("/home/lourival/Downloads/LOURIVALSABINO2.pfx"), "storepasswd");
		for (Alias alias : store1.aliases()) {
			System.out.println(alias.getName());
		}

		JCAStore store2 = new JCAStore(KeyStoreType.JKS);
		StoreUtils.copy(store1, store2, new Alias[] { new PasswordAlias("lourival sabino", "lourival") });

		PrivateKeyEntry entry = (PrivateKeyEntry) store2.get(new PasswordAlias("lourival sabino", "lourival"), StoreEntryType.PRIVATE_KEY);
		System.out.println("Private Key: " + entry.getValue());
		System.out.println("Certificate: " + entry.getChain()[0]);

		store2.write(new FileOutputStream("/tmp/store.jks"), "store");
	}

}
