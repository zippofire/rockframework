package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoICPBrasilType;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoPessoaFisicaICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.CertificadoPessoaJuridicaICPBrasil;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.ICPBrasilCertificateVerifier;
import br.net.woodstock.rockframework.security.cert.ext.icpbrasil.PessoaFisicaCertificateBuilderRequest;
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

public class CNJTest extends TestCase {

	// Se a rede utilizar proxy descomentar e ajustar as seguintes linhas
	static {
		// System.setProperty("http.proxyHost", "10.30.1.10");
		// System.setProperty("http.proxyPort", "8080");
		// System.setProperty("http.proxyUser", "lourival.junior");
		// System.setProperty("http.proxyPassword", "******"); // FIXME
		// System.setProperty("sun.net.client.defaultConnectTimeout", "15000");
		// System.setProperty("sun.net.client.defaultReadTimeout", "15000");
	}

	// Teste que cria o certificado para uma 'unidade certificadora'
	public void xtestCriarCA() throws Exception {
		// Inicia a criacao do certificado
		CertificateBuilderRequest request = new CertificateBuilderRequest("Woodstock Tecnologia CA"); // Assunto no construtor
		request.withCa(true); // Sera de CA
		request.withComment("Woodstock Tecnologia CA"); // Comentario Netscape
		request.withEmail("lourival.sabino.junior@gmail.com"); // RFC822

		// Contem a chave privada e o certificado
		PrivateKeyHolder holder = BouncyCastleCertificateBuilder.getInstance().build(request);

		// Cria o local para armazenar o certificado
		Store store = new JCAStore(KeyStoreType.PKCS12);

		// Adiciona a chave privada o certificado digital, alias 'woodstock' e senha 'woodstock'
		store.add(new PrivateKeyEntry(new PasswordAlias("woodstock", "woodstock"), holder.getPrivateKey(), holder.getChain()));

		// Grava no arquivo
		FileOutputStream outputStream = new FileOutputStream("/home/lourival/tmp/cert/woodstock.pfx");
		store.write(outputStream, "woodstock"); // Senha do keystore 'woodstock'
		outputStream.close();
	}

	// Teste que cria um certificado de pessoa fisica
	public void xtestCriarPF() throws Exception {
		PessoaFisicaCertificateBuilderRequest request = new PessoaFisicaCertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_ENCIPHERMENT);
		request.withExtendedKeyUsage(ExtendedKeyUsageType.CLIENT_AUTH, ExtendedKeyUsageType.EMAIL_PROTECTION);
		// Dados do ICP Brasil
		request.withCei("111111111111");
		request.withCpf("22222222222");
		request.withDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("24/05/1979"));
		request.withEmissorRG("SSP/DF");
		request.withPis("33333333333");
		request.withRegistroOAB("123456DF");
		request.withRg("44444");
		request.withTituloEleitor("555555555555");

		// Define a data de validade do certificado para 'ontem' e para daqui 1 ano
		DateBuilder builder = new DateBuilder();
		request.withNotBefore(builder.removeDays(1).getDate());
		request.withNotAfter(builder.addYears(1).getDate());

		// Recupera a chave privada e certificado criado no test anterior para
		// usar como autoridade certificadora desse certificado
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/woodstock.pfx");
		Store caStore = new JCAStore(KeyStoreType.PKCS12);
		caStore.read(inputStream, "woodstock");
		PrivateKeyEntry entry = (PrivateKeyEntry) caStore.get(new PasswordAlias("woodstock", "woodstock"), StoreEntryType.PRIVATE_KEY);
		request.withIssuerKeyHolder(new PrivateKeyHolder(entry.getValue(), entry.getChain()));

		PrivateKeyHolder holder = BouncyCastleCertificateBuilder.getInstance().build(request);

		// Grava o PKCS12 do certificado gerado
		// A senha do arquivo sera 'lourival'
		FileOutputStream outputStream = new FileOutputStream("/home/lourival/tmp/cert/lourival.pfx");
		Store store = new JCAStore(KeyStoreType.PKCS12);
		store.add(new PrivateKeyEntry(new PasswordAlias("lourival", "lourival"), holder.getPrivateKey(), holder.getChain()));
		store.write(outputStream, "lourival");
		outputStream.close();

		// Grava o certificado gerado em um arquivo, somente para visualizacao
		outputStream = new FileOutputStream("/home/lourival/tmp/cert/lourival.cer");
		outputStream.write(holder.getChain()[0].getEncoded());
		outputStream.close();
	}

	// Apenas exibe os dados do certificado digital
	public void xtestExibirCertificado() throws Exception {
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/lourival.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
		CertificadoICPBrasil certificadoICPBrasil = CertificadoICPBrasil.getInstance(certificate);
		System.out.println(certificadoICPBrasil.getICPBrasilType());
		if (certificadoICPBrasil.getICPBrasilType() == CertificadoICPBrasilType.PESSOA_FISICA) {
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
		} else if (certificadoICPBrasil.getICPBrasilType() == CertificadoICPBrasilType.PESSOA_FISICA) {
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

	// Testa um certificado real
	public void xtestVerifyOK() throws Exception {
		// O certificado que sera validado
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/adelci.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		// A hierarquia do certificado, SERASA>RECEITA>ICP-Brasil
		// SERASA
		inputStream = new FileInputStream("/home/lourival/tmp/cert/serasa.cer");
		Certificate issuer1 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
		// RECEITA
		inputStream = new FileInputStream("/home/lourival/tmp/cert/receita.cer");
		Certificate issuer2 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);
		// ICP-BRASIL
		inputStream = new FileInputStream("/home/lourival/tmp/cert/icp-brasil.cer");
		Certificate issuer3 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		CertificateVerifier cv1 = new DateCertificateVerifier(); // Valida a data de validade
		CertificateVerifier cv4 = new SelfSignedCertificateVerifier(); // Certificado auto assinado
		CertificateVerifier cv2 = new CRLCertificateVerifier(); // Lista de certificados revogados(dentro do certificado)
		CertificateVerifier cv3 = new PKIXCertificateVerifier(); // Hierarquia do certificado
		CertificateVerifier cv5 = new OCSPCertificateVerifier(); // Validacao usando OCSP
		CertificateVerifier cv6 = new ICPBrasilCertificateVerifier(); // Verifica se tem os OID do ICP-Brasil
		CertificateVerifier cv7 = new HierarchyCertificateVerifier(issuer3); // Verifica se o certificado possui o ICP-Brasil em sua hieraquia(issuer3)

		CertificateVerifier certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3, cv4, cv5, cv6, cv7 }); // Une todas as validacoes

		// Retorna true caso todas as validacoes executem corretamente
		boolean status = certificateVerifier.verify(new Certificate[] { certificate, issuer1, issuer2, issuer3 });
		System.out.println(status);
	}

	// Testa um certificado gerado pela 'CA' de testes
	public void testVerifyLocalCA() throws Exception {
		// O certificado que sera validado
		FileInputStream inputStream = new FileInputStream("/home/lourival/tmp/cert/lourival.cer");
		Certificate certificate = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		// O certificado so tem o emissor na hierarquia
		inputStream = new FileInputStream("/home/lourival/tmp/cert/woodstock.cer");
		Certificate issuer1 = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		// Adicionei o ICP-Brasil para forcar o erro na hierarquia
		inputStream = new FileInputStream("/home/lourival/tmp/cert/icp-brasil.cer");
		Certificate issuerX = SecurityUtils.getCertificateFromFile(inputStream, CertificateType.X509);

		CertificateVerifier cv1 = new DateCertificateVerifier(); // Passa OK, ainda esta no periodo de validade
		CertificateVerifier cv4 = new SelfSignedCertificateVerifier(); // Passa OK, nao e auto assinado
		CertificateVerifier cv2 = new CRLCertificateVerifier(); // Erro, nao possui a URL da CRL no certificado
		CertificateVerifier cv3 = new PKIXCertificateVerifier(); // Passa OK, a hieraquia do certificado e valida
		CertificateVerifier cv5 = new OCSPCertificateVerifier(); // Erro, nao possui a URL do OCSP no certificado
		CertificateVerifier cv6 = new ICPBrasilCertificateVerifier(); // Passa OK, possui os OID do ICP-Brasil
		CertificateVerifier cv7 = new HierarchyCertificateVerifier(issuerX); // Erro, o certificado do ICP-Brasil

		CertificateVerifierChain certificateVerifier = new CertificateVerifierChain(new CertificateVerifier[] { cv1, cv2, cv3, cv4, cv5, cv6, cv7 });

		certificateVerifier.setDebug(true);

		boolean status = certificateVerifier.verify(new Certificate[] { certificate, issuer1, issuerX });
		System.out.println(status);

		status = certificateVerifier.verify(new Certificate[] { certificate, issuer1 });
		System.out.println(status);

		// Nesse teste vai dar erro no PKIXCertificateVerifier
		status = certificateVerifier.verify(new Certificate[] { certificate, issuerX });
		System.out.println(status);
	}

}
