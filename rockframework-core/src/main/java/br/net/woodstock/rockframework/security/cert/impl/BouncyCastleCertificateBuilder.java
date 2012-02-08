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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import br.net.woodstock.rockframework.security.cert.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.util.DateBuilder;

public class BouncyCastleCertificateBuilder implements CertificateBuilder {

	private static final String	DEFAULT_ISSUER	= "";

	private String				subject;

	private KeyPair				keyPair;

	private SignType			signType;

	private String				issuer;

	private BigInteger			serialNumber;

	private Date				notBefore;

	private Date				notAfter;

	private boolean				v3;

	// V3 Extensions
	private Set<KeyUsageType>	keyUsage;

	public BouncyCastleCertificateBuilder(final String subject) {
		this(subject, BouncyCastleCertificateBuilder.DEFAULT_ISSUER);
	}

	public BouncyCastleCertificateBuilder(final String subject, final String issuer) {
		super();
		this.subject = subject;
		this.issuer = issuer;
		this.keyUsage = new HashSet<KeyUsageType>();
	}

	public BouncyCastleCertificateBuilder withKeyPair(final KeyPair keyPair) {
		this.keyPair = keyPair;
		return this;
	}

	public BouncyCastleCertificateBuilder withSignType(final SignType signType) {
		this.signType = signType;
		return this;
	}

	public BouncyCastleCertificateBuilder withIssuer(final String issuer) {
		this.issuer = issuer;
		return this;
	}

	public BouncyCastleCertificateBuilder withSerialNumber(final BigInteger serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public BouncyCastleCertificateBuilder withNotBefore(final Date notBefore) {
		this.notBefore = notBefore;
		return this;
	}

	public BouncyCastleCertificateBuilder withNotAfter(final Date notAfter) {
		this.notAfter = notAfter;
		return this;
	}

	public BouncyCastleCertificateBuilder withKeyUsage(final KeyUsageType... array) {
		for (KeyUsageType keyUsage : array) {
			this.keyUsage.add(keyUsage);
		}
		return this;
	}

	public BouncyCastleCertificateBuilder withV3Extensions(final boolean v3) {
		this.v3 = v3;
		return this;
	}

	@Override
	public PrivateKeyHolder build() {
		try {
			long time = System.currentTimeMillis();
			String subject = this.subject;
			KeyPair keyPair = this.keyPair;
			SignType signType = this.signType;
			String issuer = this.issuer;
			BigInteger serialNumber = this.serialNumber;
			Date notBefore = this.notBefore;
			Date notAfter = this.notAfter;

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

			if (notBefore == null) {
				DateBuilder dateBuilder = new DateBuilder(time);
				dateBuilder.removeDays(1);
				notBefore = dateBuilder.getDate();
			}

			if (notAfter == null) {
				DateBuilder dateBuilder = new DateBuilder(time);
				dateBuilder.addYears(1);
				notAfter = dateBuilder.getDate();
			}

			if (this.v3) {
				JcaX509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(this.toX500Name(issuer), serialNumber, notBefore, notAfter, this.toX500Name(subject), keyPair.getPublic());

				JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(signType.getAlgorithm());
				contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
				ContentSigner contentSigner = contentSignerBuilder.build(keyPair.getPrivate());

				if (this.keyUsage.size() > 0) {
					int usage = 0;
					for (KeyUsageType keyUsage : this.keyUsage) {
						usage = usage | this.toKeyUsage(keyUsage);
					}
					org.bouncycastle.asn1.x509.KeyUsage ku = new org.bouncycastle.asn1.x509.KeyUsage(usage);
					builder.addExtension(X509Extension.keyUsage, false, ku);
				}

				GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.rfc822Name, subject));
				builder.addExtension(X509Extension.subjectAlternativeName, false, subjectAltName);

				SubjectKeyIdentifierStructure subjectKeyIdentifierStructure = new SubjectKeyIdentifierStructure(keyPair.getPublic());
				builder.addExtension(X509Extension.subjectKeyIdentifier, false, subjectKeyIdentifierStructure);

				ExtendedKeyUsage extendedKeyUsage = new ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage);
				builder.addExtension(X509Extension.extendedKeyUsage, false, extendedKeyUsage);

				X509CertificateHolder holder = builder.build(contentSigner);

				certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(holder.getEncoded(), CertificateType.X509);
				privateKey = keyPair.getPrivate();
			} else {
				JcaX509v1CertificateBuilder builder = new JcaX509v1CertificateBuilder(this.toX500Name(issuer), serialNumber, notBefore, notAfter, this.toX500Name(subject), keyPair.getPublic());

				JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(signType.getAlgorithm());
				contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
				ContentSigner contentSigner = contentSignerBuilder.build(keyPair.getPrivate());

				X509CertificateHolder holder = builder.build(contentSigner);

				certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(holder.getEncoded(), CertificateType.X509);
				privateKey = keyPair.getPrivate();
			}

			PrivateKeyHolder privateKeyHolder = new PrivateKeyHolder(privateKey, new Certificate[] { certificate });

			return privateKeyHolder;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	private int toKeyUsage(final KeyUsageType keyUsageType) {
		switch (keyUsageType) {
			case CRL_SIGN:
				return KeyUsage.cRLSign;
			case DATA_ENCIPHERMENT:
				return KeyUsage.dataEncipherment;
			case DECIPHER_ONLY:
				return KeyUsage.decipherOnly;
			case DIGITAL_SIGNATURE:
				return KeyUsage.digitalSignature;
			case ENCIPHER_ONLY:
				return KeyUsage.encipherOnly;
			case KEY_AGREEMENT:
				return KeyUsage.keyAgreement;
			case KEY_CERT_SIGN:
				return KeyUsage.keyCertSign;
			case KEY_ENCIPHERMENT:
				return KeyUsage.keyEncipherment;
			case NON_REPUDIATION:
				return KeyUsage.nonRepudiation;
			default:
				return 0;
		}
	}

	private X500Name toX500Name(final String value) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, value);
		return builder.build();
	}

}
