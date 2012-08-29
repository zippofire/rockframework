package br.net.woodstock.rockframework.security.cert.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.CertificateVersionType;
import br.net.woodstock.rockframework.security.cert.ExtendedKeyUsageType;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.util.DateBuilder;

public class BouncyCastleCertificateBuilderRequest implements Serializable {

	private static final long			serialVersionUID	= 8225561861029220298L;

	private static final int			DEFAULT_KEY_SIZE	= 1024;

	private long						time;

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

	private Set<KeyUsageType>			keyUsage;

	private Set<ExtendedKeyUsageType>	extendedKeyUsage;

	private Map<String, String>			otherNames;

	private Map<String, String>			certificatePolicies;

	public BouncyCastleCertificateBuilderRequest(final CertificateBuilderRequest request) throws NoSuchAlgorithmException {
		super();
		Assert.notNull(request, "request");

		this.time = System.currentTimeMillis();
		this.subject = request.getSubject();
		this.email = request.getEmail();
		this.keyPair = request.getKeyPair();
		this.signType = request.getSignType();
		this.issuerName = request.getIssuerName();
		this.issuerKeyHolder = request.getIssuerKeyHolder();
		this.serialNumber = request.getSerialNumber();
		this.notBefore = request.getNotBefore();
		this.notAfter = request.getNotAfter();
		this.version = request.getVersion();
		this.comment = request.getComment();
		this.crlDistPoint = request.getCrlDistPoint();
		this.ocspURL = request.getOcspURL();
		this.policyURL = request.getPolicyURL();
		this.ca = request.isCa();
		this.keyUsage = request.getKeyUsage();
		this.extendedKeyUsage = request.getExtendedKeyUsage();
		this.otherNames = request.getOtherNames();
		this.certificatePolicies = request.getCertificatePolicies();
		this.ca = request.isCa();

		if (this.keyPair == null) {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyPairType.RSA.getAlgorithm());
			int keySize = request.getKeySize();
			if (!((keySize >= BouncyCastleCertificateBuilderRequest.DEFAULT_KEY_SIZE) && (keySize % BouncyCastleCertificateBuilderRequest.DEFAULT_KEY_SIZE == 0))) {
				keySize = BouncyCastleCertificateBuilderRequest.DEFAULT_KEY_SIZE;
			}
			generator.initialize(keySize);
			this.keyPair = generator.generateKeyPair();
		}

		if (this.signType == null) {
			this.signType = SignatureType.SHA1_RSA;
		}

		if (this.serialNumber == null) {
			this.serialNumber = BigInteger.valueOf(this.time);
		}

		if (this.notBefore == null) {
			DateBuilder dateBuilder = new DateBuilder(this.time);
			dateBuilder.removeDays(1);
			this.notBefore = dateBuilder.getDate();
		}

		if (this.notAfter == null) {
			DateBuilder dateBuilder = new DateBuilder(this.time);
			dateBuilder.addYears(1);
			this.notAfter = dateBuilder.getDate();
		}
	}

	public long getTime() {
		return this.time;
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

	public Set<KeyUsageType> getKeyUsage() {
		return this.keyUsage;
	}

	public Set<ExtendedKeyUsageType> getExtendedKeyUsage() {
		return this.extendedKeyUsage;
	}

	public Map<String, String> getOtherNames() {
		return this.otherNames;
	}

	public Map<String, String> getCertificatePolicies() {
		return this.certificatePolicies;
	}

	// Aux
	public X500Name getIssuerAsX500Name() {
		return BouncyCastleProviderHelper.toX500Name(this.getIssuerName());
	}

	public X500Name getSubjectAsX500Name() {
		return BouncyCastleProviderHelper.toX500Name(this.getSubject());
	}

	public X500Principal getSubjectAsX500Principal() throws IOException {
		return BouncyCastleProviderHelper.toX500Principal(this.getSubject());
	}

	public PublicKey getPublicKey() {
		return this.getKeyPair().getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.getKeyPair().getPrivate();
	}

	public String getSignAlgorithm() {
		return this.getSignType().getAlgorithm();
	}

	public PrivateKey getIssuerPrivateKey() {
		if (this.issuerKeyHolder != null) {
			return this.issuerKeyHolder.getPrivateKey();
		}
		return null;
	}

	public X509Certificate getIssuerCertificate() {
		if (this.issuerKeyHolder != null) {
			return (X509Certificate) this.issuerKeyHolder.getChain()[0];
		}
		return null;
	}

}
