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
package br.net.woodstock.rockframework.xml.dom;

import java.io.Serializable;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

abstract class DocumentWrapper implements Cloneable, Comparable<Document>, Document, Serializable {

	private static final long	serialVersionUID	= 2580717334916435297L;

	private Document			document;

	@Override
	public abstract int compareTo(Document o);

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(final Document document) {
		this.document = document;
	}

	@Override
	public DocumentType getDoctype() {
		return this.document.getDoctype();
	}

	@Override
	public DOMImplementation getImplementation() {
		return this.document.getImplementation();
	}

	@Override
	public Element getDocumentElement() {
		return this.document.getDocumentElement();
	}

	@Override
	public Element createElement(final String tagName) {
		return this.document.createElement(tagName);
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		return this.document.createDocumentFragment();
	}

	@Override
	public Text createTextNode(final String data) {
		return this.document.createTextNode(data);
	}

	@Override
	public Comment createComment(final String data) {
		return this.document.createComment(data);
	}

	@Override
	public CDATASection createCDATASection(final String data) {
		return this.document.createCDATASection(data);
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(final String target, final String data) {
		return this.document.createProcessingInstruction(target, data);
	}

	@Override
	public Attr createAttribute(final String name) {
		return this.document.createAttribute(name);
	}

	@Override
	public EntityReference createEntityReference(final String name) {
		return this.document.createEntityReference(name);
	}

	@Override
	public NodeList getElementsByTagName(final String tagname) {
		return this.document.getElementsByTagName(tagname);
	}

	@Override
	public Node importNode(final Node importedNode, final boolean deep) {
		return this.document.importNode(importedNode, deep);
	}

	@Override
	public Element createElementNS(final String namespaceURI, final String qualifiedName) {
		return this.document.createElementNS(namespaceURI, qualifiedName);
	}

	@Override
	public Attr createAttributeNS(final String namespaceURI, final String qualifiedName) {
		return this.document.createAttributeNS(namespaceURI, qualifiedName);
	}

	@Override
	public NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) {
		return this.document.getElementsByTagNameNS(namespaceURI, localName);
	}

	@Override
	public Element getElementById(final String elementId) {
		return this.document.getElementById(elementId);
	}

	@Override
	public String getInputEncoding() {
		return this.document.getInputEncoding();
	}

	@Override
	public String getXmlEncoding() {
		return this.document.getXmlEncoding();
	}

	@Override
	public boolean getXmlStandalone() {
		return this.document.getXmlStandalone();
	}

	@Override
	public void setXmlStandalone(final boolean xmlStandalone) {
		this.document.setXmlStandalone(xmlStandalone);
	}

	@Override
	public String getXmlVersion() {
		return this.document.getXmlVersion();
	}

	@Override
	public void setXmlVersion(final String xmlVersion) {
		this.document.setXmlVersion(xmlVersion);
	}

	@Override
	public boolean getStrictErrorChecking() {
		return this.document.getStrictErrorChecking();
	}

	@Override
	public void setStrictErrorChecking(final boolean strictErrorChecking) {
		this.document.setStrictErrorChecking(strictErrorChecking);
	}

	@Override
	public String getDocumentURI() {
		return this.document.getDocumentURI();
	}

	@Override
	public void setDocumentURI(final String documentURI) {
		this.document.setDocumentURI(documentURI);
	}

	@Override
	public Node adoptNode(final Node source) {
		return this.document.adoptNode(source);
	}

	@Override
	public DOMConfiguration getDomConfig() {
		return this.document.getDomConfig();
	}

	@Override
	public void normalizeDocument() {
		this.document.normalizeDocument();
	}

	@Override
	public Node renameNode(final Node n, final String namespaceURI, final String qualifiedName) {
		return this.document.renameNode(n, namespaceURI, qualifiedName);
	}

	@Override
	public String getNodeName() {
		return this.document.getNodeName();
	}

	@Override
	public String getNodeValue() {
		return this.document.getNodeValue();
	}

	@Override
	public void setNodeValue(final String nodeValue) {
		this.document.setNodeValue(nodeValue);
	}

	@Override
	public short getNodeType() {
		return this.document.getNodeType();
	}

	@Override
	public Node getParentNode() {
		return this.document.getParentNode();
	}

	@Override
	public NodeList getChildNodes() {
		return this.document.getChildNodes();
	}

	@Override
	public Node getFirstChild() {
		return this.document.getFirstChild();
	}

	@Override
	public Node getLastChild() {
		return this.document.getLastChild();
	}

	@Override
	public Node getPreviousSibling() {
		return this.document.getPreviousSibling();
	}

	@Override
	public Node getNextSibling() {
		return this.document.getNextSibling();
	}

	@Override
	public NamedNodeMap getAttributes() {
		return this.document.getAttributes();
	}

	@Override
	public Document getOwnerDocument() {
		return this.document.getOwnerDocument();
	}

	@Override
	public Node insertBefore(final Node newChild, final Node refChild) {
		return this.document.insertBefore(newChild, refChild);
	}

	@Override
	public Node replaceChild(final Node newChild, final Node oldChild) {
		return this.document.replaceChild(newChild, oldChild);
	}

	@Override
	public Node removeChild(final Node oldChild) {
		return this.document.removeChild(oldChild);
	}

	@Override
	public Node appendChild(final Node newChild) {
		return this.document.appendChild(newChild);
	}

	@Override
	public boolean hasChildNodes() {
		return this.document.hasChildNodes();
	}

	@Override
	public Node cloneNode(final boolean deep) {
		return this.document.cloneNode(deep);
	}

	@Override
	public void normalize() {
		this.document.normalize();
	}

	@Override
	public boolean isSupported(final String feature, final String version) {
		return this.document.isSupported(feature, version);
	}

	@Override
	public String getNamespaceURI() {
		return this.document.getNamespaceURI();
	}

	@Override
	public String getPrefix() {
		return this.document.getPrefix();
	}

	@Override
	public void setPrefix(final String prefix) {
		this.document.setPrefix(prefix);
	}

	@Override
	public String getLocalName() {
		return this.document.getLocalName();
	}

	@Override
	public boolean hasAttributes() {
		return this.document.hasAttributes();
	}

	@Override
	public String getBaseURI() {
		return this.document.getBaseURI();
	}

	@Override
	public short compareDocumentPosition(final Node other) {
		return this.document.compareDocumentPosition(other);
	}

	@Override
	public String getTextContent() {
		return this.document.getTextContent();
	}

	@Override
	public void setTextContent(final String textContent) {
		this.document.setTextContent(textContent);
	}

	@Override
	public boolean isSameNode(final Node other) {
		return this.document.isSameNode(other);
	}

	@Override
	public String lookupPrefix(final String namespaceURI) {
		return this.document.lookupPrefix(namespaceURI);
	}

	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		return this.document.isDefaultNamespace(namespaceURI);
	}

	@Override
	public String lookupNamespaceURI(final String prefix) {
		return this.document.lookupNamespaceURI(prefix);
	}

	@Override
	public boolean isEqualNode(final Node arg) {
		return this.document.isEqualNode(arg);
	}

	@Override
	public Object getFeature(final String feature, final String version) {
		return this.document.getFeature(feature, version);
	}

	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		return this.document.setUserData(key, data, handler);
	}

	@Override
	public Object getUserData(final String key) {
		return this.document.getUserData(key);
	}

}
