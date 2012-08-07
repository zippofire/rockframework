package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoPessoaFisicaICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoPessoaJuridicaICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.ICPBrasilCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.PessoaFisicaCertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.TipoICPBrasilType;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.cert.impl.CRLCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.CertificateVerifierChain;
import br.net.woodstock.rockframework.security.cert.impl.DateCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.HierarchyCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.OCSPCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.PKIXCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.impl.SelfSignedCertificateVerifier;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.util.DateBuilder;

public class CertificateTest extends TestCase {

	static {
		System.setProperty("http.proxyHost", "10.30.1.10");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("http.proxyUser", "lourival.junior");
		System.setProperty("http.proxyPassword", "******"); // FIXME
		System.setProperty("sun.net.client.defaultConnectTimeout", "15000");
		System.setProperty("sun.net.client.defaultReadTimeout", "15000");
	}

	public void testCreate() throws Exception {
		PessoaFisicaCertificateBuilderRequest request = new PessoaFisicaCertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeySize(2048);
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

		DateBuilder builder = new DateBuilder();
		request.withNotBefore(builder.removeDays(1).getDate());
		request.withNotAfter(builder.addYears(1).getDate());

		// CA
		FileInputStream inputStream = new FileInputStream("/tmp/woodstock.pfx");
		Store caStore = new JCAStore(KeyStoreType.PKCS12);
		caStore.read(inputStream, "woodstock");
		PrivateKeyEntry entry = (PrivateKeyEntry) caStore.get(new PasswordAlias("woodstock", "woodstock"), StoreEntryType.PRIVATE_KEY);
		request.withIssuerKeyHolder(new PrivateKeyHolder(entry.getValue(), entry.getChain()));

		PrivateKeyHolder holder = BouncyCastleCertificateBuilder.getInstance().build(request);

		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.add(new PrivateKeyEntry(new PasswordAlias("lourival", "lourival"), holder.getPrivateKey(), holder.getChain()));
		store.write(new FileOutputStream("/tmp/lourival.pfx"), "lourival");

		// FileOutputStream outputStream = new FileOutputStream("/tmp/lourival.cer");
		// outputStream.write(holder.getChain()[0].getEncoded());

		// X509Certificate certificate = (X509Certificate) holder.getChain()[0];
		// X500Principal principal = certificate.getSubjectX500Principal();
		// System.out.println(certificate);
		// System.out.println(principal);
		// System.out.println(principal.getName(X500Principal.CANONICAL));
	}

	public void xtestCreateCA() throws Exception {
		CertificateBuilderRequest request = new CertificateBuilderRequest("Woodstock Tecnologia CA");
		request.withCa(true);
		request.withComment("Woodstock Tecnologia CA");
		request.withEmail("ca@woodstock.net.br");
		request.withKeySize(4096);

		PrivateKeyHolder holder = BouncyCastleCertificateBuilder.getInstance().build(request);

		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.add(new PrivateKeyEntry(new PasswordAlias("woodstock", "woodstock"), holder.getPrivateKey(), holder.getChain()));
		store.write(new FileOutputStream("/tmp/woodstock.pfx"), "woodstock");
	}

	public void xtestCheckCert() throws Exception {
		String[] files = new String[] { "/tmp/woodstock.cer", "/home/lourival/Dropbox/cacert/root.crt" };
		for (String file : files) {
			System.out.println("\n\nFile: " + file);
			FileInputStream inputStream = new FileInputStream(file);
			X509Certificate certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
			boolean[] keyUsage = certificate.getKeyUsage();
			if (keyUsage != null) {
				System.out.println("digitalSignature: " + keyUsage[0]);
				System.out.println("nonRepudiation: " + keyUsage[1]);
				System.out.println("keyEncipherment: " + keyUsage[2]);
				System.out.println("dataEncipherment: " + keyUsage[3]);
				System.out.println("keyAgreement: " + keyUsage[4]);
				System.out.println("keyCertSign: " + keyUsage[5]);
				System.out.println("cRLSign: " + keyUsage[6]);
				System.out.println("encipherOnly: " + keyUsage[7]);
				System.out.println("decipherOnly: " + keyUsage[8]);
			}
			System.out.println(certificate.getExtendedKeyUsage());
			System.out.println(certificate.getBasicConstraints());
			System.out.println(certificate.getCriticalExtensionOIDs());
			System.out.println(certificate.getNonCriticalExtensionOIDs());
		}
	}

	public void xtestValidateCA() throws Exception {
		FileInputStream inputStream = new FileInputStream("/tmp/cert5.cert");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream.close();

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		// CertificateVerifier cv3 = new PKIXCertificateVerifier(new Certificate[] { certificate }, new
		// Certificate[] { certificate });

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2 });
		boolean ok = certificateVerifier.verify(new Certificate[] { certificate });
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
		CertificateVerifier cv3 = new PKIXCertificateVerifier();

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3 });
		boolean ok = certificateVerifier.verify(holder.getChain());
		System.out.println("OK: " + ok);
	}

	public void xtestICPBrasil() throws Exception {
		FileInputStream inputStream = new FileInputStream("/tmp/lourival.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
		CertificadoICPBrasil certificadoICPBrasil = CertificadoICPBrasil.getInstance(certificate);
		System.out.println(certificadoICPBrasil.getTipo());
		if (certificadoICPBrasil.getTipo() == TipoICPBrasilType.PESSOA_FISICA) {
			CertificadoPessoaFisicaICPBrasil certPF = (CertificadoPessoaFisicaICPBrasil) certificadoICPBrasil;
			System.out.println("Certificado de PF");
			System.out.println("CEI             : " + certPF.getCei());
			System.out.println("CPF             : " + certPF.getCpf());
			System.out.println("Data Nascimento : " + certPF.getDataNascimento());
			System.out.println("Email           : " + certPF.getEmail());
			System.out.println("Emissor RG      : " + certPF.getEmissorRG());
			System.out.println("PIS             : " + certPF.getPis());
			System.out.println("Registro OAB    : " + certPF.getRegistroOAB());
			System.out.println("RG              : " + certPF.getRg());
			System.out.println("Titulo Eleitor  : " + certPF.getTituloEleitor());
		} else if (certificadoICPBrasil.getTipo() == TipoICPBrasilType.PESSOA_FISICA) {
			CertificadoPessoaJuridicaICPBrasil certPJ = (CertificadoPessoaJuridicaICPBrasil) certificadoICPBrasil;
			System.out.println("Certificado de PF");
			System.out.println("CEI             : " + certPJ.getCei());
			System.out.println("CNPJ            : " + certPJ.getCnpj());
			System.out.println("CPF             : " + certPJ.getCpfResponsavel());
			System.out.println("Data Nascimento : " + certPJ.getDataNascimentoResponsavel());
			System.out.println("Email           : " + certPJ.getEmail());
			System.out.println("Emissor RG      : " + certPJ.getEmissorRGResponsavel());
			System.out.println("PIS             : " + certPJ.getPisResponsavel());
			System.out.println("Responsavel     : " + certPJ.getResponsavel());
			System.out.println("RG              : " + certPJ.getRgResponsavel());
		}
	}

	public void xtestVerifyOK() throws Exception {
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/adelci.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream = new FileInputStream("/home/lourival/tmp/cert/serasa.cer");
		Certificate issuer1 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream = new FileInputStream("/home/lourival/tmp/cert/receita.cer");
		Certificate issuer2 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream = new FileInputStream("/home/lourival/tmp/cert/icp-brasil.cer");
		Certificate issuer3 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv4 = new SelfSignedCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier();
		CertificateVerifier cv5 = new OCSPCertificateVerifier();
		CertificateVerifier cv6 = new ICPBrasilCertificateVerifier();
		CertificateVerifier cv7 = new HierarchyCertificateVerifier(issuer3);

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3, cv4, cv5, cv6, cv7 });
		// boolean status = certificateVerifier.verify(new Certificate[] { certificate, issuer1, issuer2,
		// issuer3 });
		boolean status = certificateVerifier.verify(new Certificate[] { certificate, issuer1, issuer2, issuer3 });
		System.out.println(status);
	}

	public void xtestVerifyLocalCA() throws Exception {
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/lourival.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream = new FileInputStream("/home/lourival/tmp/cert/woodstock.cer");
		Certificate issuer1 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		inputStream = new FileInputStream("/home/lourival/tmp/cert/icp-brasil.cer");
		Certificate issuerX = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv4 = new SelfSignedCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier();
		CertificateVerifier cv5 = new OCSPCertificateVerifier();
		CertificateVerifier cv6 = new ICPBrasilCertificateVerifier();
		CertificateVerifier cv7 = new HierarchyCertificateVerifier(issuerX);

		CertificateVerifierChain certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3, cv4, cv5, cv6, cv7 });

		certificateVerifier.setDebug(true);

		boolean status = certificateVerifier.verify(new Certificate[] { certificate, issuer1, issuerX });
		System.out.println(status);

		status = certificateVerifier.verify(new Certificate[] { certificate, issuer1 });
		System.out.println(status);
	}

	public void xtestVerifySelfSigned() throws Exception {
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/lourival2.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		CertificateVerifier cv1 = new DateCertificateVerifier();
		CertificateVerifier cv4 = new SelfSignedCertificateVerifier();
		CertificateVerifier cv2 = new CRLCertificateVerifier();
		CertificateVerifier cv3 = new PKIXCertificateVerifier();
		CertificateVerifier cv5 = new OCSPCertificateVerifier();
		CertificateVerifier cv6 = new ICPBrasilCertificateVerifier();

		CertificateVerifierChain certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3, cv4, cv5, cv6 });

		certificateVerifier.setDebug(true);

		boolean status = certificateVerifier.verify(new Certificate[] { certificate });
		System.out.println(status);
	}

	public void xtestOIDs() throws Exception {
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/adelci.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
		X509Certificate x509Certificate = (X509Certificate) certificate;
		System.out.println(x509Certificate);
		for (String oid : x509Certificate.getCriticalExtensionOIDs()) {
			System.out.println("CRI  : " + oid);
		}
		for (String oid : x509Certificate.getNonCriticalExtensionOIDs()) {
			System.out.println("N-CRI: " + oid);
		}

		URL[] urls = OCSPCertificateVerifier.getOCSPUrl(certificate);
		for (URL url : urls) {
			System.out.println(url);
		}
	}
}
