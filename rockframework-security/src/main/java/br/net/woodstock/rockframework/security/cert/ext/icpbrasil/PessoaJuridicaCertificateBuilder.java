package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import br.net.woodstock.rockframework.security.cert.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;

public class PessoaJuridicaCertificateBuilder implements CertificateBuilder {

	private static final String						OID_NOME_RESPONSAVEL	= "2.16.76.1.3.2";

	private static final String						OID_NUMERO_CNPJ			= "2.16.76.1.3.3";

	private static final String						OID_DADOS_RESPONSAVEL	= "2.16.76.1.3.4";

	private static final String						OID_NUMERO_CEI			= "2.16.76.1.3.7";

	private static PessoaJuridicaCertificateBuilder	instance				= new PessoaJuridicaCertificateBuilder();

	private PessoaJuridicaCertificateBuilder() {
		super();
	}

	@Override
	public PrivateKeyHolder build(final CertificateBuilderRequest request) {
		if (request instanceof PessoaJuridicaCertificateBuilderRequest) {
			PessoaJuridicaCertificateBuilderRequest pfcbr = (PessoaJuridicaCertificateBuilderRequest) request;
			String responsavel = ICPBrasilHelper.getValue(pfcbr.getResponsavel());
			String cnpj = ICPBrasilHelper.getNumericValue(pfcbr.getCnpj(), 14);
			String dataNascimentoResponsavel = ICPBrasilHelper.getDateValue(pfcbr.getDataNascimentoResponsavel());
			String cpfResponsavel = ICPBrasilHelper.getNumericValue(pfcbr.getCpfResponsavel(), 11);
			String pisResponsavel = ICPBrasilHelper.getNumericValue(pfcbr.getPisResponsavel(), 11);
			String rgResponsavel = ICPBrasilHelper.getNumericValue(pfcbr.getRgResponsavel(), 15);
			String emissorRGResponsavel = ICPBrasilHelper.getTextValue(pfcbr.getEmissorRGResponsavel(), 15);

			String cei = ICPBrasilHelper.getNumericValue(pfcbr.getCei(), 12);

			String dadosResponsavel = dataNascimentoResponsavel + cpfResponsavel + pisResponsavel + rgResponsavel + emissorRGResponsavel;

			request.withOtherName(PessoaJuridicaCertificateBuilder.OID_NOME_RESPONSAVEL, responsavel);
			request.withOtherName(PessoaJuridicaCertificateBuilder.OID_NUMERO_CNPJ, cnpj);
			request.withOtherName(PessoaJuridicaCertificateBuilder.OID_DADOS_RESPONSAVEL, dadosResponsavel);
			request.withOtherName(PessoaJuridicaCertificateBuilder.OID_NUMERO_CEI, cei);
		}
		return BouncyCastleCertificateBuilder.getInstance().build(request);
	}

	public static PessoaJuridicaCertificateBuilder getInstance() {
		return PessoaJuridicaCertificateBuilder.instance;
	}

}
