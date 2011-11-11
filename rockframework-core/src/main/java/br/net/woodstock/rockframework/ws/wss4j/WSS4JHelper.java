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
package br.net.woodstock.rockframework.ws.wss4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityEngine;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecEncrypt;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.w3c.dom.Document;

abstract class WSS4JHelper {

	private static final int	IDENTITY_TYPE	= WSConstants.BST_DIRECT_REFERENCE;

	static {
		WSSConfig.init();
	}

	private WSS4JHelper() {
		//
	}

	public static void decryptAndCheckSign(final SOAPMessage message, final WSS4JCredential client, final WSS4JCredential server) throws SOAPException, TransformerException, WSSecurityException {
		WSSecurityEngine engine = new WSSecurityEngine();
		WSSecSignature signer = new WSSecSignature();
		signer.setUserInfo(server.getName(), server.getPassword());

		Crypto crypto = CryptoFactory.getInstance();
		Document document = WSS4JHelper.toDocument(message);

		Map<String, String> map = new HashMap<String, String>();
		map.put(client.getName(), client.getPassword());

		List<WSSecurityEngineResult> list = engine.processSecurityHeader(document, null, new WSS4JPasswordCallbackHandler(map), crypto);

		if (list == null) {
			throw new RuntimeException("Error processing security headers");
		}

		DOMSource source = new DOMSource(document);
		message.getSOAPPart().setContent(source);
	}

	public static void encrypt(final SOAPMessage message, final WSS4JCredential credential) throws SOAPException, TransformerException, WSSecurityException {
		Document document = WSS4JHelper.toDocument(message);
		DOMSource domSource = WSS4JHelper.encrypt(document, credential);
		message.getSOAPPart().setContent(domSource);
	}

	public static void sign(final SOAPMessage message, final WSS4JCredential credential) throws SOAPException, TransformerException, WSSecurityException {
		Document document = WSS4JHelper.toDocument(message);
		DOMSource domSource = WSS4JHelper.sign(document, credential);
		message.getSOAPPart().setContent(domSource);
	}

	public static void timestamp(final SOAPMessage message) throws SOAPException, TransformerException, WSSecurityException {
		Document document = WSS4JHelper.toDocument(message);
		DOMSource domSource = WSS4JHelper.timestamp(document);
		message.getSOAPPart().setContent(domSource);
	}

	public static void usernameToken(final SOAPMessage message, final WSS4JCredential credential) throws SOAPException, TransformerException, WSSecurityException {
		Document document = WSS4JHelper.toDocument(message);
		DOMSource domSource = WSS4JHelper.usernameToken(document, credential);
		message.getSOAPPart().setContent(domSource);
	}

	// Server
	public static DOMSource encrypt(final Document document, final WSS4JCredential credential) throws WSSecurityException {
		WSSecHeader header = new WSSecHeader();
		header.insertSecurityHeader(document);

		WSSecEncrypt crypter = new WSSecEncrypt();
		crypter.setUserInfo(credential.getName(), credential.getPassword());
		crypter.setKeyIdentifierType(WSS4JHelper.IDENTITY_TYPE);

		Crypto crypto = CryptoFactory.getInstance();

		Document encrypted = crypter.build(document, crypto, header);

		DOMSource domSource = new DOMSource(encrypted);
		return domSource;
	}

	// Client
	public static DOMSource sign(final Document document, final WSS4JCredential credential) throws WSSecurityException {
		WSSecHeader header = new WSSecHeader();
		header.insertSecurityHeader(document);

		WSSecSignature signer = new WSSecSignature();
		signer.setUserInfo(credential.getName(), credential.getPassword());
		signer.setKeyIdentifierType(WSS4JHelper.IDENTITY_TYPE);

		Crypto crypto = CryptoFactory.getInstance();

		Document signed = signer.build(document, crypto, header);

		DOMSource domSource = new DOMSource(signed);
		return domSource;
	}

	public static DOMSource timestamp(final Document document) throws WSSecurityException {
		WSSecHeader header = new WSSecHeader();
		header.insertSecurityHeader(document);

		WSSecTimestamp timestamp = new WSSecTimestamp();
		Document stamped = timestamp.build(document, header);
		DOMSource domSource = new DOMSource(stamped);
		return domSource;
	}

	public static DOMSource usernameToken(final Document document, final WSS4JCredential credential) throws WSSecurityException {
		WSSecHeader header = new WSSecHeader();
		header.insertSecurityHeader(document);

		WSSecUsernameToken token = new WSSecUsernameToken();
		token.setUserInfo(credential.getName(), credential.getPassword());

		if (credential instanceof WSS4JTokenCredential) {
			WSS4JTokenCredential tokenCredential = (WSS4JTokenCredential) credential;
			token.setPasswordType(tokenCredential.getTokenType().getType());
		} else {
			token.setPasswordType(PasswordTokenType.PLAIN.getType());
		}

		Document stamped = token.build(document, header);
		DOMSource domSource = new DOMSource(stamped);
		return domSource;
	}

	private static Document toDocument(final SOAPMessage message) throws SOAPException, TransformerException {
		Source src = message.getSOAPPart().getContent();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMResult result = new DOMResult();
		transformer.transform(src, result);
		return (Document) result.getNode();
	}

}
