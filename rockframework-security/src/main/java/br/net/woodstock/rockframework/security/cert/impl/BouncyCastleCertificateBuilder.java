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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Map.Entry;
import java.util.Vector;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERTags;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import br.net.woodstock.rockframework.security.cert.CertificateBuilder;
import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class BouncyCastleCertificateBuilder implements CertificateBuilder {

	private static BouncyCastleCertificateBuilder	instance	= new BouncyCastleCertificateBuilder();

	protected BouncyCastleCertificateBuilder() {
		super();
	}

	@Override
	public PrivateKeyHolder build(final CertificateBuilderRequest request) {
		try {
			BouncyCastleCertificateBuilderRequest bcRequest = new BouncyCastleCertificateBuilderRequest(request);
			PrivateKeyHolder privateKeyHolder = null;

			if (CertificateVersionType.V3.equals(bcRequest.getVersion())) {
				privateKeyHolder = this.buildV3Certificate(bcRequest);
			} else {
				privateKeyHolder = this.buildV1Certificate(bcRequest);
			}

			return privateKeyHolder;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	protected PrivateKeyHolder buildV1Certificate(final BouncyCastleCertificateBuilderRequest request) throws OperatorCreationException, GeneralSecurityException, IOException {
		JcaX509v1CertificateBuilder builder = new JcaX509v1CertificateBuilder(request.getIssuerAsX500Name(), request.getSerialNumber(), request.getNotBefore(), request.getNotAfter(), request.getSubjectAsX500Name(), request.getPublicKey());

		JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(request.getSignAlgorithm());
		contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
		ContentSigner contentSigner = contentSignerBuilder.build(request.getPrivateKey());

		X509CertificateHolder holder = builder.build(contentSigner);

		X509Certificate certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(holder.getEncoded(), CertificateType.X509);
		PrivateKey privateKey = request.getPrivateKey();
		PrivateKeyHolder privateKeyHolder = new PrivateKeyHolder(privateKey, new Certificate[] { certificate });

		return privateKeyHolder;
	}

	protected PrivateKeyHolder buildV3Certificate(final BouncyCastleCertificateBuilderRequest request) throws OperatorCreationException, GeneralSecurityException, IOException {
		JcaX509v3CertificateBuilder builder = null;
		ContentSigner contentSigner = null;

		JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(request.getSignAlgorithm());
		contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);

		if ((request.getIssuerPrivateKey() != null) && (request.getIssuerCertificate() != null)) {
			builder = new JcaX509v3CertificateBuilder(request.getIssuerCertificate(), request.getSerialNumber(), request.getNotBefore(), request.getNotAfter(), request.getSubjectAsX500Principal(), request.getPublicKey());
			builder.addExtension(X509Extension.authorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(request.getIssuerCertificate()));
			contentSigner = contentSignerBuilder.build(request.getIssuerPrivateKey());
		} else {
			builder = new JcaX509v3CertificateBuilder(request.getIssuerAsX500Name(), request.getSerialNumber(), request.getNotBefore(), request.getNotAfter(), request.getSubjectAsX500Name(), request.getPublicKey());
			contentSigner = contentSignerBuilder.build(request.getPrivateKey());
		}

		SubjectKeyIdentifierStructure subjectKeyIdentifierStructure = new SubjectKeyIdentifierStructure(request.getPublicKey());
		builder.addExtension(X509Extension.subjectKeyIdentifier, false, subjectKeyIdentifierStructure);

		this.addV3KeyUsage(builder, request);
		this.addV3ExtendedKeyUsage(builder, request);
		this.addV3OtherNames(builder, request);
		this.addV3Comment(builder, request);
		this.addV3CRLDistPoint(builder, request);
		this.addV3OcspUrl(builder, request);
		this.addV3PolicyUrl(builder, request);
		this.addV3CAExtensions(builder, request);

		// builder.addExtension(MiscObjectIdentifiers.netscapeCertType, false, new
		// NetscapeCertType(NetscapeCertType.objectSigning | NetscapeCertType.smime));

		X509CertificateHolder holder = builder.build(contentSigner);

		X509Certificate certificate = (X509Certificate) SecurityUtils.getCertificateFromFile(holder.getEncoded(), CertificateType.X509);

		if ((request.getIssuerPrivateKey() != null) && (request.getIssuerCertificate() != null)) {
			certificate.verify(request.getIssuerCertificate().getPublicKey());
		}

		PrivateKey privateKey = request.getPrivateKey();
		PrivateKeyHolder privateKeyHolder = new PrivateKeyHolder(privateKey, new Certificate[] { certificate });

		return privateKeyHolder;
	}

	protected void addV3KeyUsage(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (request.isCa()) {
			int usage = 0;
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.CRL_SIGN);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.DATA_ENCIPHERMENT);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.DIGITAL_SIGNATURE);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.KEY_AGREEMENT);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.KEY_CERT_SIGN);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.KEY_ENCIPHERMENT);
			usage |= BouncyCastleCertificateHelper.toKeyUsage(KeyUsageType.NON_REPUDIATION);

			org.bouncycastle.asn1.x509.KeyUsage ku = new org.bouncycastle.asn1.x509.KeyUsage(usage);
			builder.addExtension(X509Extension.keyUsage, true, ku);
		} else {
			if (!request.getKeyUsage().isEmpty()) {
				int usage = 0;
				for (KeyUsageType keyUsage : request.getKeyUsage()) {
					usage = usage | BouncyCastleCertificateHelper.toKeyUsage(keyUsage);
				}
				org.bouncycastle.asn1.x509.KeyUsage ku = new org.bouncycastle.asn1.x509.KeyUsage(usage);
				builder.addExtension(X509Extension.keyUsage, true, ku);
			}
		}
	}

	protected void addV3ExtendedKeyUsage(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
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
			}
		}
	}

	protected void addV3OtherNames(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if ((ConditionUtils.isNotEmpty(request.getEmail())) || (!request.getOtherNames().isEmpty())) {
			ASN1EncodableVector vector = new ASN1EncodableVector();
			if (ConditionUtils.isNotEmpty(request.getEmail())) {
				GeneralName rfc822Name = new GeneralName(GeneralName.rfc822Name, request.getEmail());
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
		}
	}

	protected void addV3CRLDistPoint(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (ConditionUtils.isNotEmpty(request.getCrlDistPoint())) {
			GeneralName gn = new GeneralName(6, new DERIA5String(request.getCrlDistPoint()));

			ASN1EncodableVector vec = new ASN1EncodableVector();
			vec.add(gn);

			GeneralNames generalNames = new GeneralNames(new DERSequence(vec));
			DistributionPointName distributionPointName = new DistributionPointName(0, generalNames);
			CRLDistPoint crlDistPoint = new CRLDistPoint(new DistributionPoint[] { new DistributionPoint(distributionPointName, null, null) });

			builder.addExtension(X509Extension.cRLDistributionPoints, false, crlDistPoint);
			builder.addExtension(MiscObjectIdentifiers.netscapeCApolicyURL, false, new DERIA5String(request.getCrlDistPoint()));
		}
	}

	protected void addV3OcspUrl(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (ConditionUtils.isNotEmpty(request.getOcspURL())) {
			GeneralName ocspLocation = new GeneralName(6, new DERIA5String(request.getOcspURL()));
			builder.addExtension(X509Extension.authorityInfoAccess, false, new AuthorityInformationAccess(X509ObjectIdentifiers.ocspAccessMethod, ocspLocation));
		}
	}

	protected void addV3PolicyUrl(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (ConditionUtils.isNotEmpty(request.getPolicyURL())) {
			builder.addExtension(MiscObjectIdentifiers.netscapeCApolicyURL, false, new DERIA5String(request.getPolicyURL()));
		}
	}

	protected void addV3Comment(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (ConditionUtils.isNotEmpty(request.getComment())) {
			builder.addExtension(MiscObjectIdentifiers.netscapeCertComment, false, new DERIA5String(request.getComment()));
		}
	}

	protected void addV3CAExtensions(final JcaX509v3CertificateBuilder builder, BouncyCastleCertificateBuilderRequest request) {
		if (request.isCa()) {
			builder.addExtension(X509Extension.basicConstraints, true, new BasicConstraints(true));
		}
	}

	public static BouncyCastleCertificateBuilder getInstance() {
		return BouncyCastleCertificateBuilder.instance;
	}

}
