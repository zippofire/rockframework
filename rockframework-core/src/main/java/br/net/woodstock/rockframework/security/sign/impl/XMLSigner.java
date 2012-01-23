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
import java.security.GeneralSecurityException;
import java.security.KeyPair;
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
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.sign.DocumentSigner;
import br.net.woodstock.rockframework.security.sign.SignerException;
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

	private KeyPair					keyPair;

	public XMLSigner(final KeyPair keyPair, final DigestType digestType) throws GeneralSecurityException {
		super();
		Assert.notNull(keyPair, "keyPair");
		Assert.notNull(digestType, "digestType");

		this.xmlSignatureFactory = XMLSignatureFactory.getInstance(XMLSigner.SIGNATURE_FACTORY);
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();

		this.keyPair = keyPair;

		DigestMethod digestMethod = this.xmlSignatureFactory.newDigestMethod(this.getDigestMethodName(digestType), null);
		List<Transform> transforms = Collections.singletonList(this.xmlSignatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
		Reference reference = this.xmlSignatureFactory.newReference(XMLSigner.REFERENCE_URI, digestMethod, transforms, null, null);

		CanonicalizationMethod canonicalizationMethod = this.xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (C14NMethodParameterSpec) null);
		SignatureMethod signatureMethod = this.xmlSignatureFactory.newSignatureMethod(this.getSignatureMethodName(this.keyPair), null);

		this.signedInfo = this.xmlSignatureFactory.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(reference));

		KeyInfoFactory keyInfoFactory = this.xmlSignatureFactory.getKeyInfoFactory();
		KeyValue keyValue = keyInfoFactory.newKeyValue(this.keyPair.getPublic());
		this.keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue));
	}

	@Override
	public byte[] sign(final byte[] data) {
		Assert.notEmpty(data, "data");
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
			Document document = this.documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			DOMSignContext signContext = new DOMSignContext(this.keyPair.getPrivate(), document.getDocumentElement());
			XMLSignature signature = this.xmlSignatureFactory.newXMLSignature(this.signedInfo, this.keyInfo);

			signature.sign(signContext);

			Document outputDocument = this.documentBuilderFactory.newDocumentBuilder().newDocument();
			DOMResult domResult = new DOMResult(outputDocument);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), domResult);

			ByteArrayWriter writer = new ByteArrayWriter();
			XmlWriter.getInstance().write(outputDocument, writer, Charset.defaultCharset());

			return writer.toByteArray();
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		Assert.notEmpty(data, "data");
		Assert.notEmpty(signature, "signature");
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(signature);
			Document document = this.documentBuilderFactory.newDocumentBuilder().parse(inputStream);

			NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, XMLSigner.SIGNATURE_ELEMENT);
			if ((nodeList != null) && (nodeList.getLength() > 0)) {
				Node node = nodeList.item(0);
				DOMValidateContext domValidateContext = new DOMValidateContext(KeySelector.singletonKeySelector(this.keyPair.getPrivate()), node);
				XMLSignature xmlSignature = this.xmlSignatureFactory.unmarshalXMLSignature(domValidateContext);
				return xmlSignature.getSignatureValue().validate(domValidateContext);
			}
			return false;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

	private String getDigestMethodName(final DigestType digestType) {
		switch (digestType) {
			case SHA_256:
				return DigestMethod.SHA256;
			case SHA_512:
				return DigestMethod.SHA512;
			default:
				return DigestMethod.SHA1;
		}
	}

	private String getSignatureMethodName(final KeyPair keyPair) {
		KeyPairType keyPairType = KeyPairType.valueOf(keyPair.getPrivate().getAlgorithm());
		switch (keyPairType) {
			case DSA:
				return SignatureMethod.DSA_SHA1;
			default:
				return SignatureMethod.RSA_SHA1;
		}
	}

}
