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
package br.net.woodstock.rockframework.security.store.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.crypto.SecretKey;

import org.xml.sax.SAXException;

import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.security.store.StoreException;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.CollectionUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class XMLStore extends MapStore {

	private static final Charset	CHARSET					= Charset.defaultCharset();

	private static final String		ENCODING				= CHARSET.name();

	private static final String		ALIAS_ATTRIBUTE			= "alias";

	private static final String		ALGORITHM_ATTRIBUTE		= "algorithm";

	private static final String		ENCODING_ATTRIBUTE		= "encoding";

	private static final String		STORE_ELEMENT			= "store";

	private static final String		CERTIFICATES_ELEMENT	= "certificates";

	private static final String		CHAIN_ELEMENT			= "chain";

	private static final String		PRIVATE_KEYS_ELEMENT	= "privateKeys";

	private static final String		PUBLIC_KEYS_ELEMENT		= "publicKeys";

	private static final String		SECRET_KEYS_ELEMENT		= "secretKeys";

	private static final String		CERTIFICATE_ELEMENT		= "certificate";

	private static final String		PRIVATE_KEY_ELEMENT		= "privateKey";

	private static final String		PUBLIC_KEY_ELEMENT		= "publicKey";

	private static final String		SECRET_KEY_ELEMENT		= "publicKey";

	@Override
	public void read(final InputStream inputStream, final String password) throws IOException {
		try {
			XmlDocument document = XmlDocument.read(inputStream);
			XmlElement root = document.getRoot();

			String encoding = root.getAttribute(XMLStore.ENCODING_ATTRIBUTE);

			XmlElement certificates = root.getElement(XMLStore.CERTIFICATES_ELEMENT);
			XmlElement privateKeys = root.getElement(XMLStore.PRIVATE_KEYS_ELEMENT);
			XmlElement publicKeys = root.getElement(XMLStore.PUBLIC_KEYS_ELEMENT);
			XmlElement secretKeys = root.getElement(XMLStore.SECRET_KEYS_ELEMENT);

			for (XmlElement certificateElement : certificates.getElements()) {
				String alias = certificateElement.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				Certificate certificate = this.getCertificate(certificateElement, encoding);
				this.getCertificateMap().put(alias, certificate);
			}

			for (XmlElement privateKeyElement : privateKeys.getElements()) {
				String alias = privateKeyElement.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				PrivateKeyHolder holder = this.getPrivateKey(privateKeyElement, encoding);
				this.getPrivateKeyMap().put(alias, holder);
			}

			for (XmlElement publicKeyElement : publicKeys.getElements()) {
				String alias = publicKeyElement.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				PublicKey publicKey = this.getPublicKey(publicKeyElement, encoding);
				this.getPublicKeyMap().put(alias, publicKey);
			}

			for (XmlElement secretKeyElement : secretKeys.getElements()) {
				String alias = secretKeyElement.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				SecretKey secretKey = this.getSecretKey(secretKeyElement, encoding);
				this.getSecretKeyMap().put(alias, secretKey);
			}
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		} catch (SAXException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public void write(final OutputStream outputStream, final String password) throws IOException {
		try {
			XmlDocument document = new XmlDocument(XMLStore.STORE_ELEMENT);

			XmlElement root = document.getRoot();
			root.setAttribute(XMLStore.ENCODING_ATTRIBUTE, XMLStore.ENCODING);

			XmlElement certificates = root.addElement(XMLStore.CERTIFICATES_ELEMENT);
			XmlElement privateKeys = root.addElement(XMLStore.PRIVATE_KEYS_ELEMENT);
			XmlElement publicKeys = root.addElement(XMLStore.PUBLIC_KEYS_ELEMENT);
			XmlElement secretKeys = root.addElement(XMLStore.SECRET_KEYS_ELEMENT);
			for (Entry<String, Certificate> entry : this.getCertificateMap().entrySet()) {
				String alias = entry.getKey();
				Certificate certificate = entry.getValue();
				this.addCertificateElement(certificates, alias, certificate);
			}
			for (Entry<String, PrivateKeyHolder> entry : this.getPrivateKeyMap().entrySet()) {
				String alias = entry.getKey();
				PrivateKeyHolder holder = entry.getValue();
				this.addPrivateKeyElement(privateKeys, alias, holder);
			}
			for (Entry<String, PublicKey> entry : this.getPublicKeyMap().entrySet()) {
				String alias = entry.getKey();
				PublicKey publicKey = entry.getValue();
				this.addPublicKeyElement(publicKeys, alias, publicKey);
			}
			for (Entry<String, SecretKey> entry : this.getSecretKeyMap().entrySet()) {
				String alias = entry.getKey();
				SecretKey secretKey = entry.getValue();
				this.addSecretKeyElement(secretKeys, alias, secretKey);
			}
			document.write(outputStream);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	private Certificate getCertificate(final XmlElement e, final String encoding) throws CertificateException, UnsupportedEncodingException {
		String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
		byte[] data = this.getBase64Data(e, encoding);

		Certificate certificate = SecurityUtils.getCertificateFromFile(data, CertificateType.getCertificateType(algorithm));
		return certificate;
	}

	private PrivateKeyHolder getPrivateKey(final XmlElement e, final String encoding) throws NoSuchAlgorithmException, InvalidKeySpecException, CertificateException, UnsupportedEncodingException {
		String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
		byte[] data = this.getBase64Data(e, encoding);

		PrivateKey privateKey = SecurityUtils.getPrivateKeyFromPKCS8File(data, KeyPairType.getKeyPairType(algorithm));

		List<Certificate> chainList = new ArrayList<Certificate>();
		Certificate[] chain = null;
		XmlElement chainElement = e.getElement(XMLStore.CHAIN_ELEMENT);
		for (XmlElement certificateElement : chainElement.getElements()) {
			Certificate certificate = this.getCertificate(certificateElement, encoding);
			chainList.add(certificate);
		}

		if (chainList.size() > 0) {
			chain = CollectionUtils.toArray(chainList, Certificate.class);
		}

		PrivateKeyHolder holder = new PrivateKeyHolder(privateKey, chain);
		return holder;
	}

	private PublicKey getPublicKey(final XmlElement e, final String encoding) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
		byte[] data = this.getBase64Data(e, encoding);

		PublicKey publicKey = SecurityUtils.getPublicKeyFromX509File(data, KeyPairType.getKeyPairType(algorithm));
		return publicKey;
	}

	private SecretKey getSecretKey(final XmlElement e, final String encoding) throws UnsupportedEncodingException {
		String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
		byte[] data = this.getBase64Data(e, encoding);

		SecretKey secretKey = SecurityUtils.getSecretKeyFromFile(data, KeyType.getKeyType(algorithm));
		return secretKey;
	}

	private void addCertificateElement(final XmlElement parent, final String alias, final Certificate certificate) throws CertificateEncodingException, UnsupportedEncodingException {
		XmlElement certificateElement = parent.addElement(XMLStore.CERTIFICATE_ELEMENT);
		certificateElement.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
		certificateElement.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, certificate.getType());

		this.setBase64Data(certificateElement, certificate.getEncoded());
	}

	private void addPrivateKeyElement(final XmlElement parent, final String alias, final PrivateKeyHolder holder) throws CertificateEncodingException, UnsupportedEncodingException {
		PrivateKey privateKey = holder.getPrivateKey();
		Certificate[] chain = holder.getChain();
		XmlElement privateKeyElement = parent.addElement(XMLStore.PRIVATE_KEY_ELEMENT);
		XmlElement chainElement = privateKeyElement.addElement(XMLStore.CHAIN_ELEMENT);
		privateKeyElement.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
		privateKeyElement.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, privateKey.getAlgorithm());

		this.setBase64Data(privateKeyElement, privateKey.getEncoded());

		if (ConditionUtils.isNotEmpty(chain)) {
			for (Certificate certificate : chain) {
				this.addCertificateElement(chainElement, alias, certificate);
			}
		}
	}

	private void addPublicKeyElement(final XmlElement parent, final String alias, final PublicKey publicKey) throws UnsupportedEncodingException {
		XmlElement publicKeyElement = parent.addElement(XMLStore.PUBLIC_KEY_ELEMENT);
		publicKeyElement.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
		publicKeyElement.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, publicKey.getAlgorithm());

		this.setBase64Data(publicKeyElement, publicKey.getEncoded());
	}

	private void addSecretKeyElement(final XmlElement parent, final String alias, final SecretKey secretKey) throws UnsupportedEncodingException {
		XmlElement publicKeyElement = parent.addElement(XMLStore.SECRET_KEY_ELEMENT);
		publicKeyElement.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
		publicKeyElement.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, secretKey.getAlgorithm());

		this.setBase64Data(publicKeyElement, secretKey.getEncoded());
	}

	private byte[] getBase64Data(final XmlElement e, final String encoding) throws UnsupportedEncodingException {
		byte[] data64 = e.getData().getBytes(encoding);
		byte[] data = Base64Utils.fromBase64(data64);
		return data;
	}

	private void setBase64Data(final XmlElement e, final byte[] data) throws UnsupportedEncodingException {
		byte[] data64 = Base64Utils.toBase64(data);
		String str = new String(data64, XMLStore.ENCODING);
		e.setData(str);
	}
}
