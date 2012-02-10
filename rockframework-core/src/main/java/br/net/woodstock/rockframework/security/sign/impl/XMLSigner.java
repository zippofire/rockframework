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
package br.net.woodstock.rockframework.security.sign.impl;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.net.woodstock.rockframework.io.ByteArrayWriter;
import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.sign.DocumentSigner;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.SignatureRequest;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.xml.dom.XmlWriter;

public class XMLSigner implements DocumentSigner {

	private static final String		SIGNATURE_FACTORY	= "DOM";

	private static final String		REFERENCE_URI		= "";

	private static final String		SIGNATURE_ELEMENT	= "Signature";

	private DocumentBuilderFactory	documentBuilderFactory;

	private XMLSignatureFactory		xmlSignatureFactory;

	private SignedInfo				signedInfo;

	private KeyInfo					keyInfo;

	private SignatureRequest		request;

	public XMLSigner(final SignatureRequest request) {
		super();
		Assert.notNull(request, "request");

		this.request = request;

		this.xmlSignatureFactory = XMLSignatureFactory.getInstance(XMLSigner.SIGNATURE_FACTORY);
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public byte[] sign(final byte[] data) {
		Assert.notEmpty(data, "data");
		try {
			Store store = this.request.getStore();
			Alias[] aliases = this.request.getAliases();

			byte[] currentData = data;

			for (Alias alias : aliases) {
				PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) store.get(alias, StoreEntryType.PRIVATE_KEY);
				PrivateKey privateKey = privateKeyEntry.getValue();
				Certificate[] chain = privateKeyEntry.getChain();
				X509Certificate certificate = (X509Certificate) chain[0];
				PublicKey publicKey = certificate.getPublicKey();

				String digestType = this.getDigestMethodName(certificate.getSigAlgName());
				DigestMethod digestMethod = this.xmlSignatureFactory.newDigestMethod(digestType, null);
				List<Transform> transforms = Collections.singletonList(this.xmlSignatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
				Reference reference = this.xmlSignatureFactory.newReference(XMLSigner.REFERENCE_URI, digestMethod, transforms, null, null);

				CanonicalizationMethod canonicalizationMethod = this.xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (C14NMethodParameterSpec) null);
				SignatureMethod signatureMethod = this.xmlSignatureFactory.newSignatureMethod(this.getSignatureDigestName(certificate.getSigAlgName()), null);

				this.signedInfo = this.xmlSignatureFactory.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(reference));

				KeyInfoFactory keyInfoFactory = this.xmlSignatureFactory.getKeyInfoFactory();
				KeyValue keyValue = keyInfoFactory.newKeyValue(publicKey);
				this.keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue));

				ByteArrayInputStream inputStream = new ByteArrayInputStream(currentData);

				Document document = this.documentBuilderFactory.newDocumentBuilder().parse(inputStream);
				DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
				XMLSignature signature = this.xmlSignatureFactory.newXMLSignature(this.signedInfo, this.keyInfo);

				signature.sign(signContext);

				Document outputDocument = this.documentBuilderFactory.newDocumentBuilder().newDocument();
				DOMResult domResult = new DOMResult(outputDocument);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.transform(new DOMSource(document), domResult);

				ByteArrayWriter writer = new ByteArrayWriter();
				XmlWriter.getInstance().write(outputDocument, writer, Charset.defaultCharset());

				currentData = writer.toByteArray();
			}
			return currentData;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(signature, "signature");
		try {
			Store store = this.request.getStore();
			Alias[] aliases = this.request.getAliases();

			boolean valid = true;

			for (Alias alias : aliases) {
				PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) store.get(alias, StoreEntryType.PRIVATE_KEY);
				Certificate[] chain = privateKeyEntry.getChain();
				X509Certificate certificate = (X509Certificate) chain[0];
				PublicKey publicKey = certificate.getPublicKey();

				ByteArrayInputStream inputStream = new ByteArrayInputStream(signature);
				Document document = this.documentBuilderFactory.newDocumentBuilder().parse(inputStream);

				NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, XMLSigner.SIGNATURE_ELEMENT);
				if ((nodeList != null) && (nodeList.getLength() > 0)) {
					Node node = nodeList.item(0);
					DOMValidateContext domValidateContext = new DOMValidateContext(KeySelector.singletonKeySelector(publicKey), node);
					XMLSignature xmlSignature = this.xmlSignatureFactory.unmarshalXMLSignature(domValidateContext);
					valid = xmlSignature.getSignatureValue().validate(domValidateContext);
					if (!valid) {
						break;
					}
				} else {
					valid = false;
					break;
				}
			}
			return valid;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	public Signature[] getSignatures(final byte[] data) {
		throw new UnsupportedOperationException();
	}

	private String getDigestMethodName(final String name) {
		SignatureType signatureType = SignatureType.getSignType(name);
		DigestType digestType = signatureType.getDigestType();
		return digestType.getDigestMethod();
	}

	private String getSignatureDigestName(final String name) {
		SignatureType signatureType = SignatureType.getSignType(name);
		return signatureType.getSignatureMethod();
	}

}
