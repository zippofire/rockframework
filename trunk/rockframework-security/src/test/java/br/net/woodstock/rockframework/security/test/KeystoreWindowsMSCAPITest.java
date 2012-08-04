package br.net.woodstock.rockframework.security.test;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.ProviderType;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.utils.CollectionUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.HexUtils;
import br.net.woodstock.rockframework.utils.StringUtils;

public class KeystoreWindowsMSCAPITest extends TestCase {

	private KeyStore	keyStore;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println(StringUtils.repeat("=", 120));
		this.keyStore = KeyStore.getInstance(KeyStoreType.WINDOWS_MY.getType(), ProviderType.SUN_MSCAPI.getType());
		this.keyStore.load(null, "Abc123".toCharArray());
	}

	public void testList() throws Exception {
		Collection<String> aliases = CollectionUtils.toCollection(this.keyStore.aliases());
		for (String alias : aliases) {
			System.out.println("Alias      : " + alias);
			System.out.println("Certificate: " + this.keyStore.isCertificateEntry(alias));
			System.out.println("Key        : " + this.keyStore.isKeyEntry(alias));
		}
	}

	public void testCertificate() throws Exception {
		Collection<String> aliases = CollectionUtils.toCollection(this.keyStore.aliases());
		for (String alias : aliases) {
			System.out.println("Alias      : " + alias);
			Certificate[] chain = this.keyStore.getCertificateChain(alias);
			if (ConditionUtils.isNotEmpty(chain)) {
				X509Certificate x509Certificate = (X509Certificate) chain[0];
				System.out.println("Subject   : " + x509Certificate.getSubjectDN());
				System.out.println("Issuer    : " + x509Certificate.getIssuerDN());
				System.out.println("Serial    : " + x509Certificate.getSerialNumber());
				System.out.println("Not After : " + x509Certificate.getNotAfter());
				System.out.println("Encoded   : " + HexUtils.toHexString(x509Certificate.getEncoded()));
				// for (Certificate certificate : chain) {
				// X509Certificate x509Certificate = (X509Certificate) certificate;
				// System.out.println("Subject   : " + x509Certificate.getSubjectDN());
				// System.out.println("Issuer    : " + x509Certificate.getIssuerDN());
				// System.out.println("Serial    : " + x509Certificate.getSerialNumber());
				// System.out.println("Not After : " + x509Certificate.getNotAfter());
				// System.out.println("Encoded   : " + HexUtils.toHexString(x509Certificate.getEncoded()));
				// }
			}

		}
	}

	public void testPrivateKey() throws Exception {
		Collection<String> aliases = CollectionUtils.toCollection(this.keyStore.aliases());
		for (String alias : aliases) {
			PrivateKey privateKey = (PrivateKey) this.keyStore.getKey(alias, "Abc123".toCharArray());
			System.out.println("Class  : " + privateKey.getClass().getCanonicalName());
			System.out.println("Alias  : " + alias);
			System.out.println("Alg    : " + privateKey.getAlgorithm());
			System.out.println("Format : " + privateKey.getFormat());
			System.out.println("Encoded: " + HexUtils.toHexString(privateKey.getEncoded()));
		}
	}
}
