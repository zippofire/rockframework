package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import br.net.woodstock.rockframework.security.cert.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class PessoaFisicaCertificateBuilder implements CertificateBuilder {

	private static final String						OID_DADOS_TITULAR	= "2.16.76.1.3.1";

	private static final String						OID_TITULO_ELEITOR	= "2.16.76.1.3.5";

	private static final String						OID_NUMERO_CEI		= "2.16.76.1.3.6";

	private static final String						OID_REGISTRO_OAB	= "2.16.76.1.4.2.1";

	private static PessoaFisicaCertificateBuilder	instance			= new PessoaFisicaCertificateBuilder();

	private PessoaFisicaCertificateBuilder() {
		super();
	}

	@Override
	public PrivateKeyHolder build(final CertificateBuilderRequest request) {
		if (request instanceof PessoaFisicaCertificateBuilderRequest) {
			PessoaFisicaCertificateBuilderRequest pfcbr = (PessoaFisicaCertificateBuilderRequest) request;
			String dataNascimento = ICPBrasilHelper.getDateValue(pfcbr.getDataNascimento());
			String cpf = ICPBrasilHelper.getNumericValue(pfcbr.getCpf(), 11);
			String pis = ICPBrasilHelper.getNumericValue(pfcbr.getPis(), 11);
			String rg = ICPBrasilHelper.getNumericValue(pfcbr.getRg(), 15);
			String emissorRG = ICPBrasilHelper.getValue(pfcbr.getEmissorRG());
			String tituloEleitor = ICPBrasilHelper.getNumericValue(pfcbr.getTituloEleitor(), 12);
			String cei = ICPBrasilHelper.getNumericValue(pfcbr.getCei(), 12);

			String dadosTitular = dataNascimento + cpf + pis + rg + emissorRG;

			request.withOtherName(PessoaFisicaCertificateBuilder.OID_DADOS_TITULAR, dadosTitular);
			request.withOtherName(PessoaFisicaCertificateBuilder.OID_TITULO_ELEITOR, tituloEleitor);
			request.withOtherName(PessoaFisicaCertificateBuilder.OID_NUMERO_CEI, cei);

			if (ConditionUtils.isNotEmpty(pfcbr.getRegistroOAB())) {
				request.withOtherName(PessoaFisicaCertificateBuilder.OID_REGISTRO_OAB, pfcbr.getRegistroOAB());
			}
		}
		return BouncyCastleCertificateBuilder.getInstance().build(request);
	}

	public static PessoaFisicaCertificateBuilder getInstance() {
		return PessoaFisicaCertificateBuilder.instance;
	}

}
