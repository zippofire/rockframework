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

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.sign.SignatureType;

public class PessoaJuridicaCertificateBuilderRequest extends CertificateBuilderRequest {

	private static final long	serialVersionUID		= 3115100870385480942L;

	private static final String	OID_NOME_RESPONSAVEL	= "2.16.76.1.3.2";

	private static final String	OID_NUMERO_CNPJ			= "2.16.76.1.3.3";

	private static final String	OID_DADOS_RESPONSAVEL	= "2.16.76.1.3.4";

	private static final String	OID_NUMERO_CEI			= "2.16.76.1.3.7";

	private String				responsavel;

	private String				cnpj;

	private Date				dataNascimentoResponsavel;

	private String				cpfResponsavel;

	private String				pisResponsavel;

	private String				rgResponsavel;

	private String				emissorRGResponsavel;

	private String				cei;

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

	public Date getDataNascimentoResponsavel() {
		return this.dataNascimentoResponsavel;
	}

	public String getCpfResponsavel() {
		return this.cpfResponsavel;
	}

	public String getPisResponsavel() {
		return this.pisResponsavel;
	}

	public String getRgResponsavel() {
		return this.rgResponsavel;
	}

	public String getEmissorRGResponsavel() {
		return this.emissorRGResponsavel;
	}

	public String getCei() {
		return this.cei;
	}

	@Override
	public Map<String, String> getOtherNames() {
		Map<String, String> map = super.getOtherNames();

		String responsavel = ICPBrasilHelper.getValue(this.getResponsavel());
		String cnpj = ICPBrasilHelper.getNumericValue(this.getCnpj(), 14);
		String dataNascimentoResponsavel = ICPBrasilHelper.getDateValue(this.getDataNascimentoResponsavel());
		String cpfResponsavel = ICPBrasilHelper.getNumericValue(this.getCpfResponsavel(), 11);
		String pisResponsavel = ICPBrasilHelper.getNumericValue(this.getPisResponsavel(), 11);
		String rgResponsavel = ICPBrasilHelper.getNumericValue(this.getRgResponsavel(), 15);
		String emissorRGResponsavel = ICPBrasilHelper.getTextValue(this.getEmissorRGResponsavel(), 15);

		String cei = ICPBrasilHelper.getNumericValue(this.getCei(), 12);

		String dadosResponsavel = dataNascimentoResponsavel + cpfResponsavel + pisResponsavel + rgResponsavel + emissorRGResponsavel;

		map.put(PessoaJuridicaCertificateBuilderRequest.OID_NOME_RESPONSAVEL, responsavel);
		map.put(PessoaJuridicaCertificateBuilderRequest.OID_NUMERO_CNPJ, cnpj);
		map.put(PessoaJuridicaCertificateBuilderRequest.OID_DADOS_RESPONSAVEL, dadosResponsavel);
		map.put(PessoaJuridicaCertificateBuilderRequest.OID_NUMERO_CEI, cei);

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

	public PessoaJuridicaCertificateBuilderRequest withDataNascimentoResponsavel(final Date dataNascimentoResponsavel) {
		this.dataNascimentoResponsavel = dataNascimentoResponsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withCpfResponsavel(final String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withPisResponsavel(final String pisResponsavel) {
		this.pisResponsavel = pisResponsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withRgResponsavel(final String rgResponsavel) {
		this.rgResponsavel = rgResponsavel;
		return this;
	}

	public PessoaJuridicaCertificateBuilderRequest withCei(final String cei) {
		this.cei = cei;
		return this;
	}

	// Over
	@Override
	public PessoaJuridicaCertificateBuilderRequest withEmail(String email) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withEmail(email);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withKeyPair(KeyPair keyPair) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withKeyPair(keyPair);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withSignType(SignatureType signType) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withSignType(signType);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withIssuer(String issuer) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withIssuer(issuer);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withIssuerKeyHolder(PrivateKeyHolder issuerKeyHolder) {
		return (PessoaFisicaCertificateBuilderRequest) super.withIssuerKeyHolder(issuerKeyHolder);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withSerialNumber(BigInteger serialNumber) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withSerialNumber(serialNumber);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withNotBefore(Date notBefore) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withNotBefore(notBefore);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withNotAfter(Date notAfter) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withNotAfter(notAfter);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withKeyUsage(KeyUsageType... array) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withKeyUsage(array);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withExtendedKeyUsage(ExtendedKeyUsageType... array) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withExtendedKeyUsage(array);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withVersion(CertificateVersionType version) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withVersion(version);
	}

	@Override
	public PessoaJuridicaCertificateBuilderRequest withOtherName(String oid, String value) {
		return (PessoaJuridicaCertificateBuilderRequest) super.withOtherName(oid, value);
	}

}
