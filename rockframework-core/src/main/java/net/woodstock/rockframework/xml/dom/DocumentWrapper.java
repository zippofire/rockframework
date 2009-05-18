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
package net.woodstock.rockframework.xml.dom;

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

	public abstract int compareTo(Document o);

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public DocumentType getDoctype() {
		return this.document.getDoctype();
	}

	public DOMImplementation getImplementation() {
		return this.document.getImplementation();
	}

	public Element getDocumentElement() {
		return this.document.getDocumentElement();
	}

	public Element createElement(String tagName) {
		return this.document.createElement(tagName);
	}

	public DocumentFragment createDocumentFragment() {
		return this.document.createDocumentFragment();
	}

	public Text createTextNode(String data) {
		return this.document.createTextNode(data);
	}

	public Comment createComment(String data) {
		return this.document.createComment(data);
	}

	public CDATASection createCDATASection(String data) {
		return this.document.createCDATASection(data);
	}

	public ProcessingInstruction createProcessingInstruction(String target, String data) {
		return this.document.createProcessingInstruction(target, data);
	}

	public Attr createAttribute(String name) {
		return this.document.createAttribute(name);
	}

	public EntityReference createEntityReference(String name) {
		return this.document.createEntityReference(name);
	}

	public NodeList getElementsByTagName(String tagname) {
		return this.document.getElementsByTagName(tagname);
	}

	public Node importNode(Node importedNode, boolean deep) {
		return this.document.importNode(importedNode, deep);
	}

	public Element createElementNS(String namespaceURI, String qualifiedName) {
		return this.document.createElementNS(namespaceURI, qualifiedName);
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
		return this.document.createAttributeNS(namespaceURI, qualifiedName);
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		return this.document.getElementsByTagNameNS(namespaceURI, localName);
	}

	public Element getElementById(String elementId) {
		return this.document.getElementById(elementId);
	}

	public String getInputEncoding() {
		return this.document.getInputEncoding();
	}

	public String getXmlEncoding() {
		return this.document.getXmlEncoding();
	}

	public boolean getXmlStandalone() {
		return this.document.getXmlStandalone();
	}

	public void setXmlStandalone(boolean xmlStandalone) {
		this.document.setXmlStandalone(xmlStandalone);
	}

	public String getXmlVersion() {
		return this.document.getXmlVersion();
	}

	public void setXmlVersion(String xmlVersion) {
		this.document.setXmlVersion(xmlVersion);
	}

	public boolean getStrictErrorChecking() {
		return this.document.getStrictErrorChecking();
	}

	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.document.setStrictErrorChecking(strictErrorChecking);
	}

	public String getDocumentURI() {
		return this.document.getDocumentURI();
	}

	public void setDocumentURI(String documentURI) {
		this.document.setDocumentURI(documentURI);
	}

	public Node adoptNode(Node source) {
		return this.document.adoptNode(source);
	}

	public DOMConfiguration getDomConfig() {
		return this.document.getDomConfig();
	}

	public void normalizeDocument() {
		this.document.normalizeDocument();
	}

	public Node renameNode(Node n, String namespaceURI, String qualifiedName) {
		return this.document.renameNode(n, namespaceURI, qualifiedName);
	}

	public String getNodeName() {
		return this.document.getNodeName();
	}

	public String getNodeValue() {
		return this.document.getNodeValue();
	}

	public void setNodeValue(String nodeValue) {
		this.document.setNodeValue(nodeValue);
	}

	public short getNodeType() {
		return this.document.getNodeType();
	}

	public Node getParentNode() {
		return this.document.getParentNode();
	}

	public NodeList getChildNodes() {
		return this.document.getChildNodes();
	}

	public Node getFirstChild() {
		return this.document.getFirstChild();
	}

	public Node getLastChild() {
		return this.document.getLastChild();
	}

	public Node getPreviousSibling() {
		return this.document.getPreviousSibling();
	}

	public Node getNextSibling() {
		return this.document.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return this.document.getAttributes();
	}

	public Document getOwnerDocument() {
		return this.document.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) {

		return this.document.insertBefore(newChild, refChild);
	}

	public Node replaceChild(Node newChild, Node oldChild) {
		return this.document.replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) {
		return this.document.removeChild(oldChild);
	}

	public Node appendChild(Node newChild) {
		return this.document.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return this.document.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return this.document.cloneNode(deep);
	}

	public void normalize() {
		this.document.normalize();
	}

	public boolean isSupported(String feature, String version) {
		return this.document.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return this.document.getNamespaceURI();
	}

	public String getPrefix() {
		return this.document.getPrefix();
	}

	public void setPrefix(String prefix) {
		this.document.setPrefix(prefix);
	}

	public String getLocalName() {
		return this.document.getLocalName();
	}

	public boolean hasAttributes() {
		return this.document.hasAttributes();
	}

	public String getBaseURI() {
		return this.document.getBaseURI();
	}

	public short compareDocumentPosition(Node other) {
		return this.document.compareDocumentPosition(other);
	}

	public String getTextContent() {
		return this.document.getTextContent();
	}

	public void setTextContent(String textContent) {
		this.document.setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return this.document.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return this.document.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return this.document.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return this.document.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return this.document.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return this.document.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return this.document.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return this.document.getUserData(key);
	}

}
