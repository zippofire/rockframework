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
package br.net.woodstock.rockframework.security.cert;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.net.woodstock.rockframework.security.sign.SignatureType;

public class CertificateBuilderRequest implements Serializable, Cloneable {

	private static final long			serialVersionUID	= -3581355246568074311L;

	private static final String			DEFAULT_ISSUER		= "Woodstock Tecnologia";

	private String						subject;

	private String						email;

	private KeyPair						keyPair;

	private SignatureType				signType;

	private String						issuerName;

	private PrivateKeyHolder			issuerKeyHolder;

	private BigInteger					serialNumber;

	private Date						notBefore;

	private Date						notAfter;

	private CertificateVersionType		version;

	private String						comment;

	private String						crlDistPoint;

	private String						ocspURL;

	private String						policyURL;

	private boolean						ca;

	private int							keySize;

	private Set<KeyUsageType>			keyUsage;

	private Set<ExtendedKeyUsageType>	extendedKeyUsage;

	private Map<String, String>			otherNames;

	public CertificateBuilderRequest(final String subject) {
		this(subject, CertificateBuilderRequest.DEFAULT_ISSUER);
	}

	public CertificateBuilderRequest(final String subject, final String issuer) {
		super();
		this.subject = subject;
		this.issuerName = issuer;
		this.keyUsage = new HashSet<KeyUsageType>();
		this.extendedKeyUsage = new HashSet<ExtendedKeyUsageType>();
		this.otherNames = new HashMap<String, String>();
		this.version = CertificateVersionType.V3;
	}

	// Get
	public CertificateBuilderRequest withEmail(final String email) {
		this.email = email;
		return this;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getEmail() {
		return this.email;
	}

	public KeyPair getKeyPair() {
		return this.keyPair;
	}

	public SignatureType getSignType() {
		return this.signType;
	}

	public String getIssuerName() {
		return this.issuerName;
	}

	public PrivateKeyHolder getIssuerKeyHolder() {
		return this.issuerKeyHolder;
	}

	public BigInteger getSerialNumber() {
		return this.serialNumber;
	}

	public Date getNotBefore() {
		return this.notBefore;
	}

	public Date getNotAfter() {
		return this.notAfter;
	}

	public CertificateVersionType getVersion() {
		return this.version;
	}

	public String getComment() {
		return this.comment;
	}

	public String getCrlDistPoint() {
		return this.crlDistPoint;
	}

	public String getOcspURL() {
		return this.ocspURL;
	}

	public String getPolicyURL() {
		return this.policyURL;
	}

	public boolean isCa() {
		return this.ca;
	}

	public int getKeySize() {
		return this.keySize;
	}

	public Set<KeyUsageType> getKeyUsage() {
		return this.keyUsage;
	}

	public Set<ExtendedKeyUsageType> getExtendedKeyUsage() {
		return this.extendedKeyUsage;
	}

	public Map<String, String> getOtherNames() {
		return this.otherNames;
	}

	// Set
	public CertificateBuilderRequest withKeyPair(final KeyPair keyPair) {
		this.keyPair = keyPair;
		return this;
	}

	public CertificateBuilderRequest withSignType(final SignatureType signType) {
		this.signType = signType;
		return this;
	}

	public CertificateBuilderRequest withIssuer(final String issuer) {
		this.issuerName = issuer;
		return this;
	}

	public CertificateBuilderRequest withIssuerKeyHolder(final PrivateKeyHolder issuerKeyHolder) {
		this.issuerKeyHolder = issuerKeyHolder;
		return this;
	}

	public CertificateBuilderRequest withSerialNumber(final BigInteger serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public CertificateBuilderRequest withNotBefore(final Date notBefore) {
		this.notBefore = notBefore;
		return this;
	}

	public CertificateBuilderRequest withNotAfter(final Date notAfter) {
		this.notAfter = notAfter;
		return this;
	}

	public CertificateBuilderRequest withComment(final String comment) {
		this.comment = comment;
		return this;
	}

	public CertificateBuilderRequest withCrlDistPoint(final String crlDistPoint) {
		this.crlDistPoint = crlDistPoint;
		return this;
	}

	public CertificateBuilderRequest withOcspURL(final String ocspURL) {
		this.ocspURL = ocspURL;
		return this;
	}

	public CertificateBuilderRequest withPolicyURL(final String policyURL) {
		this.policyURL = policyURL;
		return this;
	}

	public CertificateBuilderRequest withCa(final boolean ca) {
		this.ca = ca;
		return this;
	}

	public CertificateBuilderRequest withKeySize(final int keySize) {
		this.keySize = keySize;
		return this;
	}

	public CertificateBuilderRequest withKeyUsage(final KeyUsageType... array) {
		for (KeyUsageType keyUsage : array) {
			this.keyUsage.add(keyUsage);
		}
		return this;
	}

	public CertificateBuilderRequest withExtendedKeyUsage(final ExtendedKeyUsageType... array) {
		for (ExtendedKeyUsageType keyUsage : array) {
			this.extendedKeyUsage.add(keyUsage);
		}
		return this;
	}

	public CertificateBuilderRequest withVersion(final CertificateVersionType version) {
		this.version = version;
		return this;
	}

	public CertificateBuilderRequest withOtherName(final String oid, final String value) {
		this.otherNames.put(oid, value);
		return this;
	}

	// Object
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
