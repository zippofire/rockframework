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
package br.net.woodstock.rockframework.security.cert.impl;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.util.DateBuilder;

@SuppressWarnings("deprecation")
public class CertificateBuilder {

	private static final String	BC_PROVIDER		= "BC";

	private static final String	DEFAULT_ISSUER	= "";

	private String				subject;

	private KeyPair				keyPair;

	private SignType			signType;

	private String				issuer;

	private BigInteger			serialNumber;

	private Date				expiresDate;

	private boolean				v3;

	// V3 Extensions
	private Set<KeyUsage>		keyUsage;

	static {
		Provider provider = Security.getProvider(CertificateBuilder.BC_PROVIDER);
		if (provider == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public CertificateBuilder(final String subject) {
		this(subject, CertificateBuilder.DEFAULT_ISSUER);
	}

	public CertificateBuilder(final String subject, final String issuer) {
		super();
		this.subject = subject;
		this.issuer = issuer;
		this.keyUsage = new HashSet<KeyUsage>();
	}

	public CertificateBuilder withKeyPair(final KeyPair keyPair) {
		this.keyPair = keyPair;
		return this;
	}

	public CertificateBuilder withSignType(final SignType signType) {
		this.signType = signType;
		return this;
	}

	public CertificateBuilder withIssuer(final String issuer) {
		this.issuer = issuer;
		return this;
	}

	public CertificateBuilder withSerialNumber(final BigInteger serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public CertificateBuilder withExpiresDate(final Date expiresDate) {
		this.expiresDate = expiresDate;
		return this;
	}

	public CertificateBuilder withKeyUsage(final KeyUsage... array) {
		for (KeyUsage keyUsage : array) {
			this.keyUsage.add(keyUsage);
		}
		return this;
	}

	public CertificateBuilder withV3Extensions(final boolean v3) {
		this.v3 = v3;
		return this;
	}

	public CertificateHolder build() throws GeneralSecurityException {
		long time = System.currentTimeMillis();
		String subject = this.subject;
		KeyPair keyPair = this.keyPair;
		SignType signType = this.signType;
		String issuer = this.issuer;
		BigInteger serialNumber = this.serialNumber;
		Date expiresDate = this.expiresDate;

		X509Certificate certificate = null;
		PrivateKey privateKey = null;

		if (keyPair == null) {
			keyPair = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm()).generateKeyPair();
		}

		if (signType == null) {
			signType = SignType.SHA1_RSA;
		}

		if (issuer == null) {
			issuer = subject;
		}

		if (serialNumber == null) {
			serialNumber = BigInteger.valueOf(time);
		}

		if (expiresDate == null) {
			DateBuilder dateBuilder = new DateBuilder(time);
			dateBuilder.addYears(1);
			expiresDate = dateBuilder.getDate();
		}

		if (this.v3) {
			X509V3CertificateGenerator generator = new X509V3CertificateGenerator();
			generator.setSerialNumber(serialNumber);
			generator.setIssuerDN(new X500Principal(this.toCN(issuer)));
			generator.setNotBefore(new Date(time));
			generator.setNotAfter(expiresDate);
			generator.setSubjectDN(new X500Principal(this.toCN(subject)));
			generator.setPublicKey(keyPair.getPublic());
			generator.setSignatureAlgorithm(signType.getAlgorithm());

			if (this.keyUsage.size() > 0) {
				int usage = 0;
				for (KeyUsage keyUsage : this.keyUsage) {
					usage = usage | keyUsage.getUsage();
				}
				org.bouncycastle.asn1.x509.KeyUsage ku = new org.bouncycastle.asn1.x509.KeyUsage(usage);
				generator.addExtension(X509Extensions.KeyUsage, false, ku);
			}

			GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.rfc822Name, subject));
			generator.addExtension(X509Extensions.SubjectAlternativeName, false, subjectAltName);

			SubjectKeyIdentifierStructure subjectKeyIdentifierStructure = new SubjectKeyIdentifierStructure(keyPair.getPublic());
			generator.addExtension(X509Extensions.SubjectKeyIdentifier, false, subjectKeyIdentifierStructure);

			ExtendedKeyUsage extendedKeyUsage = new ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage);
			generator.addExtension(X509Extensions.ExtendedKeyUsage, false, extendedKeyUsage);

			certificate = generator.generate(keyPair.getPrivate(), CertificateBuilder.BC_PROVIDER);
			privateKey = keyPair.getPrivate();
		} else {

			X509V1CertificateGenerator generator = new X509V1CertificateGenerator();
			generator.setSerialNumber(serialNumber);
			generator.setIssuerDN(new X500Principal(this.toCN(issuer)));
			generator.setNotBefore(new Date(time));
			generator.setNotAfter(expiresDate);
			generator.setSubjectDN(new X500Principal(this.toCN(subject)));
			generator.setPublicKey(keyPair.getPublic());
			generator.setSignatureAlgorithm(signType.getAlgorithm());

			certificate = generator.generate(keyPair.getPrivate(), CertificateBuilder.BC_PROVIDER);
			privateKey = keyPair.getPrivate();
		}

		return new CertificateHolder(certificate, privateKey);
	}

	private String toCN(final String value) {
		StringBuilder builder = new StringBuilder();
		builder.append("CN=");
		builder.append(value);
		return builder.toString();
	}

}
