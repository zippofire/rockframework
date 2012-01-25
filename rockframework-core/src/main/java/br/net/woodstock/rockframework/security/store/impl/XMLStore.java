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
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Map.Entry;

import org.xml.sax.SAXException;

import br.net.woodstock.rockframework.security.cert.CertificateType;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.store.StoreException;
import br.net.woodstock.rockframework.security.util.SecurityUtils;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class XMLStore extends MapStore {

	private static final String	ALIAS_ATTRIBUTE			= "alias";

	private static final String	ALGORITHM_ATTRIBUTE		= "algorithm";

	private static final String	STORE_ELEMENT			= "store";

	private static final String	CERTIFICATES_ELEMENT	= "certificates";

	private static final String	PRIVATE_KEYS_ELEMENT	= "privateKeys";

	private static final String	PUBLIC_KEYS_ELEMENT		= "publicKeys";

	private static final String	CERTIFICATE_ELEMENT		= "certificate";

	private static final String	PRIVATE_KEY_ELEMENT		= "privateKey";

	private static final String	PUBLIC_KEY_ELEMENT		= "publicKey";

	@Override
	public void read(final InputStream inputStream, final String password) throws IOException {
		try {
			XmlDocument document = XmlDocument.read(inputStream);
			XmlElement root = document.getRoot();
			XmlElement certificates = root.getElement(XMLStore.CERTIFICATES_ELEMENT);
			XmlElement privateKeys = root.getElement(XMLStore.PRIVATE_KEYS_ELEMENT);
			XmlElement publicKeys = root.getElement(XMLStore.PUBLIC_KEYS_ELEMENT);

			for (XmlElement e : certificates.getElements()) {
				String alias = e.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
				byte[] data64 = e.getData().getBytes();
				byte[] data = Base64Utils.fromBase64(data64);

				Certificate certificate = SecurityUtils.getCertificateFromFile(data, CertificateType.getCertificateType(algorithm));
				this.getCertificateMap().put(alias, certificate);
			}

			for (XmlElement e : privateKeys.getElements()) {
				String alias = e.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
				byte[] data64 = e.getData().getBytes();
				byte[] data = Base64Utils.fromBase64(data64);

				PrivateKey privateKey = SecurityUtils.getPrivateKeyFromPKCS8File(data, KeyPairType.getKeyPairType(algorithm));
				this.getPrivateKeyMap().put(alias, privateKey);
			}

			for (XmlElement e : publicKeys.getElements()) {
				String alias = e.getAttribute(XMLStore.ALIAS_ATTRIBUTE);
				String algorithm = e.getAttribute(XMLStore.ALGORITHM_ATTRIBUTE);
				byte[] data64 = e.getData().getBytes();
				byte[] data = Base64Utils.fromBase64(data64);

				PublicKey publicKey = SecurityUtils.getPublicKeyFromX509File(data, KeyPairType.getKeyPairType(algorithm));
				this.getPublicKeyMap().put(alias, publicKey);
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
			XmlElement certificates = root.addElement(XMLStore.CERTIFICATES_ELEMENT);
			XmlElement privateKeys = root.addElement(XMLStore.PRIVATE_KEYS_ELEMENT);
			XmlElement publicKeys = root.addElement(XMLStore.PUBLIC_KEYS_ELEMENT);
			for (Entry<String, Certificate> entry : this.getCertificateMap().entrySet()) {
				String alias = entry.getKey();
				Certificate certificate = entry.getValue();
				XmlElement e = certificates.addElement(XMLStore.CERTIFICATE_ELEMENT);
				e.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
				e.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, certificate.getType());
				e.setData(new String(Base64Utils.toBase64(certificate.getEncoded())));
			}
			for (Entry<String, PrivateKey> entry : this.getPrivateKeyMap().entrySet()) {
				String alias = entry.getKey();
				PrivateKey privateKey = entry.getValue();
				XmlElement e = privateKeys.addElement(XMLStore.PRIVATE_KEY_ELEMENT);
				e.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
				e.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, privateKey.getAlgorithm());
				e.setData(new String(Base64Utils.toBase64(privateKey.getEncoded())));
			}
			for (Entry<String, PublicKey> entry : this.getPublicKeyMap().entrySet()) {
				String alias = entry.getKey();
				PublicKey publicKey = entry.getValue();
				XmlElement e = publicKeys.addElement(XMLStore.PUBLIC_KEY_ELEMENT);
				e.setAttribute(XMLStore.ALIAS_ATTRIBUTE, alias);
				e.setAttribute(XMLStore.ALGORITHM_ATTRIBUTE, publicKey.getAlgorithm());
				e.setData(new String(Base64Utils.toBase64(publicKey.getEncoded())));
			}
			document.write(outputStream);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}
}
