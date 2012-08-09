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
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class PessoaFisicaCertificateBuilderRequest extends CertificateBuilderRequest {

	private static final long	serialVersionUID	= 2206868473296076668L;

	private DadoPessoa			dadoTitular;

	private String				tituloEleitor;

	private String				cei;

	private String				ric;

	private String				registroSINCOR;

	private String				registroOAB;

	public PessoaFisicaCertificateBuilderRequest(final String subject) {
		super(subject);
	}

	public PessoaFisicaCertificateBuilderRequest(final String subject, final String issuer) {
		super(subject, issuer);
	}

	// Get
	public DadoPessoa getDadoTitular() {
		return this.dadoTitular;
	}

	public String getTituloEleitor() {
		return this.tituloEleitor;
	}

	public String getCei() {
		return this.cei;
	}

	public String getRic() {
		return this.ric;
	}

	public String getRegistroSINCOR() {
		return this.registroSINCOR;
	}

	public String getRegistroOAB() {
		return this.registroOAB;
	}

	// Override
	@Override
	public Map<String, String> getOtherNames() {
		Map<String, String> map = super.getOtherNames();

		String dadosTitular = DadoPessoa.toOtherNameString(this.getDadoTitular());
		String tituloEleitor = ICPBrasilHelper.getNumericValue(this.getTituloEleitor(), 12);
		String cei = ICPBrasilHelper.getNumericValue(this.getCei(), 12);
		String ric = ICPBrasilHelper.getTextValue(this.getRic(), 11);

		map.put(ICPBrasilHelper.OID_PF_DADOS_TITULAR, dadosTitular);
		map.put(ICPBrasilHelper.OID_PF_TITULO_ELEITOR, tituloEleitor);
		map.put(ICPBrasilHelper.OID_PF_NUMERO_CEI, cei);
		map.put(ICPBrasilHelper.OID_PF_NUMERO_RIC, ric);

		if (ConditionUtils.isNotEmpty(this.getRegistroSINCOR())) {
			map.put(ICPBrasilHelper.OID_PF_REGISTRO_SINCOR, this.getRegistroSINCOR());
		}

		if (ConditionUtils.isNotEmpty(this.getRegistroOAB())) {
			map.put(ICPBrasilHelper.OID_PF_REGISTRO_OAB, this.getRegistroOAB());
		}

		return map;
	}

	// Set
	public PessoaFisicaCertificateBuilderRequest withDadoTitular(final DadoPessoa dadoTitular) {
		this.dadoTitular = dadoTitular;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withTituloEleitor(final String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withCei(final String cei) {
		this.cei = cei;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withRic(final String ric) {
		this.ric = ric;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withRegistroSINCOR(final String registroSINCOR) {
		this.registroSINCOR = registroSINCOR;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withRegistroOAB(final String registroOAB) {
		this.registroOAB = registroOAB;
		return this;
	}

	// Over
	@Override
	public PessoaFisicaCertificateBuilderRequest withEmail(final String email) {
		return (PessoaFisicaCertificateBuilderRequest) super.withEmail(email);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withKeyPair(final KeyPair keyPair) {
		return (PessoaFisicaCertificateBuilderRequest) super.withKeyPair(keyPair);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withSignType(final SignatureType signType) {
		return (PessoaFisicaCertificateBuilderRequest) super.withSignType(signType);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withIssuer(final String issuer) {
		return (PessoaFisicaCertificateBuilderRequest) super.withIssuer(issuer);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withIssuerKeyHolder(final PrivateKeyHolder issuerKeyHolder) {
		return (PessoaFisicaCertificateBuilderRequest) super.withIssuerKeyHolder(issuerKeyHolder);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withSerialNumber(final BigInteger serialNumber) {
		return (PessoaFisicaCertificateBuilderRequest) super.withSerialNumber(serialNumber);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withNotBefore(final Date notBefore) {
		return (PessoaFisicaCertificateBuilderRequest) super.withNotBefore(notBefore);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withNotAfter(final Date notAfter) {
		return (PessoaFisicaCertificateBuilderRequest) super.withNotAfter(notAfter);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withKeyUsage(final KeyUsageType... array) {
		return (PessoaFisicaCertificateBuilderRequest) super.withKeyUsage(array);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withExtendedKeyUsage(final ExtendedKeyUsageType... array) {
		return (PessoaFisicaCertificateBuilderRequest) super.withExtendedKeyUsage(array);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withVersion(final CertificateVersionType version) {
		return (PessoaFisicaCertificateBuilderRequest) super.withVersion(version);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withOtherName(final String oid, final String value) {
		return (PessoaFisicaCertificateBuilderRequest) super.withOtherName(oid, value);
	}

}
