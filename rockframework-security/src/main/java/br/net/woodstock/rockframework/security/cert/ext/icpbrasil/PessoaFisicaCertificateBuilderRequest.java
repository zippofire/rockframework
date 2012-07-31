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

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.sign.SignatureType;

public class PessoaFisicaCertificateBuilderRequest extends CertificateBuilderRequest {

	private static final long	serialVersionUID	= 2206868473296076668L;

	private Date				dataNascimento;

	private String				cpf;

	private String				pis;

	private String				rg;

	private String				emissorRG;

	private String				tituloEleitor;

	private String				cei;

	private String				registroOAB;

	public PessoaFisicaCertificateBuilderRequest(final String subject) {
		super(subject);
	}

	public PessoaFisicaCertificateBuilderRequest(final String subject, final String issuer) {
		super(subject, issuer);
	}

	// Get
	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public String getCpf() {
		return this.cpf;
	}

	public String getPis() {
		return this.pis;
	}

	public String getRg() {
		return this.rg;
	}

	public String getEmissorRG() {
		return this.emissorRG;
	}

	public String getTituloEleitor() {
		return this.tituloEleitor;
	}

	public String getCei() {
		return this.cei;
	}

	public String getRegistroOAB() {
		return this.registroOAB;
	}

	// Set
	public PessoaFisicaCertificateBuilderRequest withDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withCpf(final String cpf) {
		this.cpf = cpf;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withPis(final String pis) {
		this.pis = pis;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withRg(final String rg) {
		this.rg = rg;
		return this;
	}

	public PessoaFisicaCertificateBuilderRequest withEmissorRG(final String emissorRG) {
		this.emissorRG = emissorRG;
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

	public PessoaFisicaCertificateBuilderRequest withRegistroOAB(final String registroOAB) {
		this.registroOAB = registroOAB;
		return this;
	}

	// Over
	@Override
	public PessoaFisicaCertificateBuilderRequest withEmail(String email) {
		return (PessoaFisicaCertificateBuilderRequest) super.withEmail(email);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withKeyPair(KeyPair keyPair) {
		return (PessoaFisicaCertificateBuilderRequest) super.withKeyPair(keyPair);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withSignType(SignatureType signType) {
		return (PessoaFisicaCertificateBuilderRequest) super.withSignType(signType);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withIssuer(String issuer) {
		return (PessoaFisicaCertificateBuilderRequest) super.withIssuer(issuer);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withIssuerKeyHolder(PrivateKeyHolder issuerKeyHolder) {
		return (PessoaFisicaCertificateBuilderRequest) super.withIssuerKeyHolder(issuerKeyHolder);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withSerialNumber(BigInteger serialNumber) {
		return (PessoaFisicaCertificateBuilderRequest) super.withSerialNumber(serialNumber);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withNotBefore(Date notBefore) {
		return (PessoaFisicaCertificateBuilderRequest) super.withNotBefore(notBefore);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withNotAfter(Date notAfter) {
		return (PessoaFisicaCertificateBuilderRequest) super.withNotAfter(notAfter);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withKeyUsage(KeyUsageType... array) {
		return (PessoaFisicaCertificateBuilderRequest) super.withKeyUsage(array);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withExtendedKeyUsage(ExtendedKeyUsageType... array) {
		return (PessoaFisicaCertificateBuilderRequest) super.withExtendedKeyUsage(array);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withVersion(CertificateVersionType version) {
		return (PessoaFisicaCertificateBuilderRequest) super.withVersion(version);
	}

	@Override
	public PessoaFisicaCertificateBuilderRequest withOtherName(String oid, String value) {
		return (PessoaFisicaCertificateBuilderRequest) super.withOtherName(oid, value);
	}

}
