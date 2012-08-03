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
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampProcessor;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

public class OCSPCertificateVerifier implements CertificateVerifier {

	private static final String	CONTENT_TYPE_PROPERTY				= "Content-Type";

	private static final String	CONTENT_TYPE_VALUE					= "application/ocsp-request";

	private static final String	CONTENT_TRANSFER_ENCODING_PROPERTY	= "Content-Transfer-Encoding";

	public static final String	CONTENT_TRANSFER_ENCODING_BINARY	= "binary";

	private URL					url;

	public OCSPCertificateVerifier() {
		super();
	}

	public OCSPCertificateVerifier(final URL url) {
		super();
		Assert.notNull(url, "url");
		this.url = url;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		if (chain.length < 2) {
			CoreLog.getInstance().getLog().info("Certificate chain must be greater than 1(certificate and issuer certificate)");
			return false;
		}
		try {
			X509Certificate x509Certificate = (X509Certificate) chain[0];
			X509Certificate x509Issuer = (X509Certificate) chain[1];
			URL url = null;

			if (this.url == null) {
				URL[] urls = OCSPCertificateVerifier.getOCSPUrl(x509Certificate);
				if (ConditionUtils.isNotEmpty(urls)) {
					url = urls[0];
				}
			} else {
				url = this.url;
			}

			if (url == null) {
				CoreLog.getInstance().getLog().info("No url found for validation");
				return false;
			}

			OCSPReq req = this.buildRequest(x509Certificate, x509Issuer);
			OCSPResp resp = this.sendRequest(req, url);
			if (resp.getStatus() == OCSPResponseStatus.SUCCESSFUL) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	protected OCSPReq buildRequest(final X509Certificate certificate, X509Certificate issuer) throws CertificateEncodingException, IOException, OperatorCreationException, OCSPException {
		OCSPReqBuilder builder = new OCSPReqBuilder();
		DigestCalculatorProvider provider = new BcDigestCalculatorProvider();
		X509CertificateHolder holder = new X509CertificateHolder(issuer.getEncoded());
		CertificateID certificateID = new CertificateID(provider.get(CertificateID.HASH_SHA1), holder, certificate.getSerialNumber());
		BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
		Vector<Object> oids = new Vector<Object>();
		Vector<Object> values = new Vector<Object>();

		oids.add(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
		values.add(new X509Extension(false, new DEROctetString(nonce.toByteArray())));

		builder.addRequest(certificateID);
		builder.setRequestExtensions(new X509Extensions(oids, values));

		return builder.build();
	}

	protected OCSPResp sendRequest(final OCSPReq req, final URL url) throws IOException {
		URLConnection connection = url.openConnection();

		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		this.setConnectionProperties(connection);

		byte[] requestBytes = req.getEncoded();

		OutputStream outputStream = connection.getOutputStream();

		this.writeBytes(outputStream, requestBytes);

		outputStream.close();

		InputStream inputStream = connection.getInputStream();

		String encoding = connection.getContentEncoding();

		byte[] bytes = this.readBytes(inputStream, encoding);
		OCSPResp resp = new OCSPResp(bytes);
		return resp;
	}

	protected void setConnectionProperties(final URLConnection connection) {
		connection.setRequestProperty(OCSPCertificateVerifier.CONTENT_TYPE_PROPERTY, OCSPCertificateVerifier.CONTENT_TYPE_VALUE);
		connection.setRequestProperty(OCSPCertificateVerifier.CONTENT_TRANSFER_ENCODING_PROPERTY, OCSPCertificateVerifier.CONTENT_TRANSFER_ENCODING_BINARY);
	}

	protected void writeBytes(final OutputStream outputStream, final byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	protected byte[] readBytes(final InputStream inputStream, final String encoding) throws IOException {
		byte[] bytes = IOUtils.toByteArray(inputStream);
		if (URLTimeStampProcessor.CONTENT_TRANSFER_ENCODING_BASE64.equals(encoding)) {
			bytes = Base64Utils.fromBase64(bytes);
		}
		return bytes;
	}

	public static URL[] getOCSPUrl(final Certificate certificate) throws IOException {
		X509Certificate x509Certificate = (X509Certificate) certificate;
		byte[] bytes = x509Certificate.getExtensionValue(X509Extension.authorityInfoAccess.getId());

		if (bytes == null) {
			return new URL[0];
		}

		Set<URL> urls = new HashSet<URL>();
		ASN1InputStream inputStream = new ASN1InputStream(bytes);
		DEROctetString octetString = (DEROctetString) inputStream.readObject();
		ASN1Sequence sequence = (ASN1Sequence) new ASN1InputStream(octetString.getOctets()).readObject();
		AuthorityInformationAccess informationAccess = new AuthorityInformationAccess(sequence);
		AccessDescription[] accessDescriptions = informationAccess.getAccessDescriptions();
		for (AccessDescription description : accessDescriptions) {
			GeneralName generalName = description.getAccessLocation();
			DERTaggedObject taggedObject = (DERTaggedObject) generalName.getDERObject();
			DERIA5String ia5String = DERIA5String.getInstance(taggedObject.getObject());
			String urlStr = ia5String.getString();
			URL url = new URL(urlStr);
			urls.add(url);
		}
		return urls.toArray(new URL[urls.size()]);
	}
}
