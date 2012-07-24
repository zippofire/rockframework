package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.security.auth.x500.X500Principal;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.PessoaFisicaCertificateBuilder;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.PessoaFisicaCertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.CRLCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.CertificateVerifierChain;
import br.net.woodstock.rockframework.security.cert.impl.DateCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.PKIXCertificateVerifier;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.util.SecurityUtils;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.30.1.12");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("sun.net.client.defaultConnectTimeout", "15000");
		System.setProperty("sun.net.client.defaultReadTimeout", "15000");
	}

	public void testCreate() throws Exception {
		PessoaFisicaCertificateBuilderRequest request = new PessoaFisicaCertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_ENCIPHERMENT);
		request.withExtendedKeyUsage(ExtendedKeyUsageType.CLIENT_AUTH, ExtendedKeyUsageType.EMAIL_PROTECTION);
		// ICP Brasil
		request.withCei("111111111111");
		request.withCpf("22222222222");
		request.withDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("24/05/1979"));
		request.withEmissorRG("SSP/DF");
		request.withPis("33333333333");
		request.withRegistroOAB("123456DF");
		request.withRg("44444");
		request.withTituloEleitor("555555555555");

		PrivateKeyHolder holder = PessoaFisicaCertificateBuilder.getInstance().build(request);

		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.add(new PrivateKeyEntry(new PasswordAlias("lourival", "lourival"), holder.getPrivateKey(), holder.getChain()));
		store.write(new FileOutputStream("/tmp/lourival.pfx"), "lourival");

		FileOutputStream outputStream = new FileOutputStream("/tmp/lourival.cer");
		outputStream.write(holder.getChain()[0].getEncoded());

		X509Certificate certificate = (X509Certificate) holder.getChain()[0];
		X500Principal principal = certificate.getSubjectX500Principal();
		System.out.println(certificate);
		System.out.println(principal);
		System.out.println(principal.getName(X500Principal.CANONICAL));
	}

	public void xtestValidateCA() throws Exception {
		FileInputStream inputStream = new FileInputStream("/tmp/cert5.cert");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream.close();

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		// CertificateVerifier cv3 = new PKIXCertificateVerifier(new Certificate[] { certificate }, new Certificate[] { certificate });

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2 });
		boolean ok = certificateVerifier.verify(certificate);
		System.out.println("OK: " + ok);
	}

	public void xtestValidateBC() throws Exception {
		CertificateBuilderRequest request = new CertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder = BouncyCastleCertificateBuilder.getInstance().build(request);

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier(holder.getChain(), holder.getChain());

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3 });
		boolean ok = certificateVerifier.verify(holder.getChain()[0]);
		System.out.println("OK: " + ok);
	}
}
