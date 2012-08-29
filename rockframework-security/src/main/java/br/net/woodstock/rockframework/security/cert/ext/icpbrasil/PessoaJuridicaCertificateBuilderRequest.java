/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.sign.SignatureType;

public class PessoaJuridicaCertificateBuilderRequest extends ICPBrasilCertificateBuilderRequest {

	private static final long	serialVersionUID	= 3115100870385480942L;

	private String				responsavel;

	private String				cnpj;

	private DadoPessoa			dadoResponsavel;

	private String				cei;

	private String				nomeEmpresarial;

	public PessoaJuridicaCertificateBuilderRequest(final String subject) {
		super(subject);
	}

	public PessoaJuridicaCertificateBuilderRequest(final String subject, final String issuer) {
		super(subject, issuer);
	}

	// Get
	public String getResponsavel() {
		return this.responsavel;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public DadoPessoa getDadoResponsavel() {
		return this.dadoResponsavel;
	}

	public String getCei() {
		return this.cei;
	}

	public String getNomeEmpresarial() {
		return this.nomeEmpresarial;
	}

	@Override
	public Map<String, String> getOtherNames() {
		Map<String, String> map = super.getOtherNames();

		String responsavel = ICPBrasilHelper.getValue(this.getResponsavel());
		String cnpj = ICPBrasilHelper.getNumericValue(this.getCnpj(), 14);
		String dadosResponsavel = DadoPessoa.toOtherNameString(this.getDadoResponsavel());
		String cei = ICPBrasilHelper.getNumericValue(this.getCei(), 12);
		String nomeEmpresarial = ICPBrasilHelper.getValue(this.getNomeEmpresarial());

		map.put(ICPBrasilHelper.OID_PJ_NOME_RESPONSAVEL, responsavel);
		map.put(ICPBrasilHelper.OID_PJ_NUMERO_CNPJ, cnpj);
		map.put(ICPBrasilHelper.OID_PJ_DADOS_RESPONSAVEL, dadosResponsavel);
		map.put(ICPBrasilHelper.OID_PJ_NUMERO_CEI, cei);
		map.put(ICPBrasilHelper.OID_PJ_NOME_EMPRESARIAL, nomeEmpresarial);

		return map;
	}

	// Set
	public PessoaJuridicaCertificateBuilderRequest withResponsavel(final String responsavel) {
		this.responsavel = responsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withCnpj(final String cnpj) {
		this.cnpj = cnpj;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withDadoResponsavel(final DadoPessoa dadoResponsavel) {
		this.dadoResponsavel = dadoResponsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withCei(final String cei) {
		this.cei = cei;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withNomeEmpresarial(final String nomeEmpresarial) {
		this.nomeEmpresarial = nomeEmpresarial;
		return this;
	}

	// Override
	@Override
	public PessoaJuridicaCertificateBuilderRequest withFormato(final FormatoICPBrasilType formato) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withFormato(formato);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withEmail(final String email) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withEmail(email);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withKeyPair(final KeyPair keyPair) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withKeyPair(keyPair);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withSignType(final SignatureType signType) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withSignType(signType);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withIssuer(final String issuer) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withIssuer(issuer);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withIssuerKeyHolder(final PrivateKeyHolder issuerKeyHolder) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withIssuerKeyHolder(issuerKeyHolder);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withSerialNumber(final BigInteger serialNumber) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withSerialNumber(serialNumber);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withNotBefore(final Date notBefore) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withNotBefore(notBefore);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withNotAfter(final Date notAfter) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withNotAfter(notAfter);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withComment(final String comment) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withComment(comment);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withCrlDistPoint(final String crlDistPoint) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withCrlDistPoint(crlDistPoint);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withOcspURL(final String ocspURL) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withOcspURL(ocspURL);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withPolicyURL(final String policyURL) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withPolicyURL(policyURL);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withCa(final boolean ca) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withCa(ca);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withKeySize(final int keySize) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withKeySize(keySize);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withKeyUsage(final KeyUsageType... array) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withKeyUsage(array);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withExtendedKeyUsage(final ExtendedKeyUsageType... array) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withExtendedKeyUsage(array);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withVersion(final CertificateVersionType version) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withVersion(version);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withOtherName(final String oid, final String value) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withOtherName(oid, value);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withCertificatePolicies(final String oid, final String value) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withCertificatePolicies(oid, value);
	}

}
