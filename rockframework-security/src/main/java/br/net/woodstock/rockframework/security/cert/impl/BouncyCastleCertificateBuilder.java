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
import java.util.Map.Entry;
import java.util.Vector;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import br.net.woodstock.rockframework.security.cert.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.util.DateBuilder;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class BouncyCastleCertificateBuilder implements CertificateBuilder {

	private static BouncyCastleCertificateBuilder	instance	= new BouncyCastleCertificateBuilder();

	private BouncyCastleCertificateBuilder() {
		super();
	}

	@Override
	public PrivateKeyHolder build(final CertificateBuilderRequest request) {
		try {
			long time = System.currentTimeMillis();
			String subject = request.getSubject();
			String email = request.getEmail();
			KeyPair keyPair = request.getKeyPair();
			SignatureType signType = request.getSignType();
			String issuer = request.getIssuerName();
			BigInteger serialNumber = request.getSerialNumber();
			Date notBefore = request.getNotBefore();
			Date notAfter = request.getNotAfter();

			X509Certificate certificate = null;
			PrivateKey privateKey = null;

			if (keyPair == null) {
				keyPair = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm()).generateKeyPair();
			}

			if (signType == null) {
				signType = SignatureType.SHA1_RSA;
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

			if (CertificateVersionType.V3.equals(request.getVersion())) {
				JcaX509v3CertificateBuilder builder = null;
				if (request.getIssuerCertificate() != null) {
					builder = new JcaX509v3CertificateBuilder((X509Certificate) request.getIssuerCertificate(), serialNumber, notBefore, notAfter, BouncyCastleProviderHelper.toX500Principal(subject), keyPair.getPublic());
				} else {
					builder = new JcaX509v3CertificateBuilder(BouncyCastleProviderHelper.toX500Name(issuer), serialNumber, notBefore, notAfter, BouncyCastleProviderHelper.toX500Name(subject), keyPair.getPublic());
				}

				JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(signType.getAlgorithm());
				contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
				ContentSigner contentSigner = contentSignerBuilder.build(keyPair.getPrivate());

				if (!request.getKeyUsage().isEmpty()) {
					int usage = 0;
					for (KeyUsageType keyUsage : request.getKeyUsage()) {
						usage = usage | BouncyCastleCertificateHelper.toKeyUsage(keyUsage);
					}
					org.bouncycastle.asn1.x509.KeyUsage ku = new org.bouncycastle.asn1.x509.KeyUsage(usage);
					builder.addExtension(X509Extension.keyUsage, false, ku);
				}

				if (!request.getExtendedKeyUsage().isEmpty()) {
					Vector<DERObject> vector = new Vector<DERObject>();
					for (ExtendedKeyUsageType keyUsageType : request.getExtendedKeyUsage()) {
						KeyPurposeId keyPurposeId = BouncyCastleCertificateHelper.toExtendedKeyUsage(keyUsageType);
						if (keyPurposeId != null) {
							vector.add(keyPurposeId);
						}
					}
					if (vector.size() > 0) {
						org.bouncycastle.asn1.x509.ExtendedKeyUsage extendedKeyUsage = new org.bouncycastle.asn1.x509.ExtendedKeyUsage(vector);
						builder.addExtension(X509Extension.extendedKeyUsage, true, extendedKeyUsage);
					} else {
						org.bouncycastle.asn1.x509.ExtendedKeyUsage extendedKeyUsage = new org.bouncycastle.asn1.x509.ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage);
						builder.addExtension(X509Extension.extendedKeyUsage, false, extendedKeyUsage);
					}
				} else {
					org.bouncycastle.asn1.x509.ExtendedKeyUsage extendedKeyUsage = new org.bouncycastle.asn1.x509.ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage);
					builder.addExtension(X509Extension.extendedKeyUsage, false, extendedKeyUsage);
				}

				ASN1EncodableVector vector = new ASN1EncodableVector();

				if (ConditionUtils.isNotEmpty(email)) {
					GeneralName rfc822Name = new GeneralName(GeneralName.rfc822Name, email);
					vector.add(rfc822Name);
				}

				if (!request.getOtherNames().isEmpty()) {
					for (Entry<String, String> entry : request.getOtherNames().entrySet()) {
						String oid = entry.getKey();
						String value = entry.getValue();
						ASN1ObjectIdentifier identifier = new ASN1ObjectIdentifier(oid);
						DEROctetString octetString = new DEROctetString(value.getBytes());
						DERTaggedObject taggedObject = new DERTaggedObject(DERTags.OCTET_STRING, octetString);
						DERSequence sequence = new DERSequence(new ASN1Encodable[] { identifier, taggedObject });
						GeneralName name = new GeneralName(GeneralName.otherName, sequence);
						vector.add(name);
					}
				}

				if (vector.size() > 0) {
					GeneralNames subjectAltName = new GeneralNames(new DERSequence(vector));
					builder.addExtension(X509Extension.subjectAlternativeName, false, subjectAltName);
				}

				SubjectKeyIdentifierStructure subjectKeyIdentifierStructure = new SubjectKeyIdentifierStructure(keyPair.getPublic());
				builder.addExtension(X509Extension.subjectKeyIdentifier, false, subjectKeyIdentifierStructure);

				X509CertificateHolder holder = builder.build(contentSigner);

				certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(holder.getEncoded(), CertificateType.X509);
				privateKey = keyPair.getPrivate();
			} else {
				JcaX509v1CertificateBuilder builder = new JcaX509v1CertificateBuilder(BouncyCastleProviderHelper.toX500Name(issuer), serialNumber, notBefore, notAfter, BouncyCastleProviderHelper.toX500Name(subject), keyPair.getPublic());

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

	public static BouncyCastleCertificateBuilder getInstance() {
		return BouncyCastleCertificateBuilder.instance;
	}

}
