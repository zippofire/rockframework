package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.sign.PKCS7SignatureParameters;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.Signatory;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.HexUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

public class PKCS7SignerTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.30.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "5000");
	}

	public void xtestCreateCert() throws Exception {
		BouncyCastleCertificateBuilder builder1 = new BouncyCastleCertificateBuilder("Lourival Sabino");
		builder1.withIssuer("Woodstock Tecnologia");
		builder1.withV3Extensions(true);
		builder1.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);
		PrivateKeyHolder holder1 = builder1.build();
		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.add(new PrivateKeyEntry(new PasswordAlias("lourival", "lourival"), holder1.getPrivateKey(), holder1.getChain()));
		store.write(new FileOutputStream("/tmp/lourival.pfx"), "lourival");
	}

	public void xtest1() throws Exception {
		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.read(new FileInputStream("/tmp/lourival.pfx"), "lourival");

		byte[] content = "Lourival Sabino da Silva Júnior".getBytes();

		TimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		PKCS7SignatureParameters signerInfo = new PKCS7SignatureParameters(new PasswordAlias("lourival", "lourival"), store);
		signerInfo.setTimeStampClient(timeStampClient);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(content);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/name.txt.p7s");
		fileOutputStream.write(signed);
		fileOutputStream.close();
	}

	public void xtest2() throws Exception {
		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.read(new FileInputStream("/tmp/lourival.pfx"), "lourival");

		byte[] content = IOUtils.toByteArray(new FileInputStream("/tmp/name.txt.p7s"));

		PKCS7SignatureParameters signerInfo = new PKCS7SignatureParameters(new PasswordAlias("lourival", "lourival"), store);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		Signature[] signatures = signer.getSignatures(content);

		for (Signature signature : signatures) {
			System.out.println(signature.getDate());
			for (Signatory signatory : signature.getSignatories()) {
				System.out.println("\t" + signatory.getSubject());
				System.out.println("\t" + signatory.getIssuer());
				System.out.println("\t" + signatory.getCertificate());
			}
		}
	}

	public void test3() throws Exception {
		PKCS7Signer signer = new BouncyCastlePKCS7Signer(null);

		for (String s : new String[] { "/tmp/sign.pdf.p7s", "/tmp/carimboDeTempo.p7s", "/tmp/carimboDeTempo2.p7s" }) {
			System.out.println(s);
			FileInputStream fileOutputStream = new FileInputStream(s);
			byte[] data = IOUtils.toByteArray(fileOutputStream);
			Signature[] signatures = signer.getSignatures(data);
			for (Signature signature : signatures) {
				System.out.println(signature.getDate());
				System.out.println(signature.getValid());
				for (Signatory signatory : signature.getSignatories()) {
					System.out.println("\tSignatories");
					System.out.println("\t\t" + signatory.getSubject());
					System.out.println("\t\t" + signatory.getIssuer());
					// System.out.println("\t\t" + signatory.getCertificate());
				}
				if (signature.getTimeStamp() != null) {
					System.out.println("\tTimestamp");
					System.out.println("\t\t" + ((X509Certificate) signature.getTimeStamp().getCertificates()[0]).getSubjectDN());
					System.out.println("\t\t" + signature.getTimeStamp().getDate());
					System.out.println("\t\t" + HexUtils.toHexString(signature.getTimeStamp().getEncoded()));
					System.out.println("\t\t" + HexUtils.toHexString(signature.getTimeStamp().getHash()));
					System.out.println("\t\t" + signature.getTimeStamp().getNonce());
					System.out.println("\t\t" + signature.getTimeStamp().getSerialNumber());
				}
			}
		}

		signer.sign(null);
	}
}
