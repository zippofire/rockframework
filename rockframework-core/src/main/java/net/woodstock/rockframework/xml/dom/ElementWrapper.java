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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

abstract class ElementWrapper implements Comparable<Element>, Element, Serializable {

	private static final long	serialVersionUID	= 6943419209399221776L;

	private Element				element;

	public abstract int compareTo(Element o);

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public String getTagName() {
		return this.element.getTagName();
	}

	public String getAttribute(String name) {
		return this.element.getAttribute(name);
	}

	public void setAttribute(String name, String value) {
		this.element.setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		this.element.removeAttribute(name);
	}

	public Attr getAttributeNode(String name) {
		return this.element.getAttributeNode(name);
	}

	public Attr setAttributeNode(Attr newAttr) {
		return this.element.setAttributeNode(newAttr);
	}

	public Attr removeAttributeNode(Attr oldAttr) {
		return this.element.removeAttributeNode(oldAttr);
	}

	public NodeList getElementsByTagName(String name) {
		return this.element.getElementsByTagName(name);
	}

	public String getAttributeNS(String namespaceURI, String localName) {
		return this.element.getAttributeNS(namespaceURI, localName);
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
		this.element.setAttributeNS(namespaceURI, qualifiedName, value);
	}

	public void removeAttributeNS(String namespaceURI, String localName) {
		this.element.removeAttributeNS(namespaceURI, localName);
	}

	public Attr getAttributeNodeNS(String namespaceURI, String localName) {
		return this.element.getAttributeNodeNS(namespaceURI, localName);
	}

	public Attr setAttributeNodeNS(Attr newAttr) {
		return this.element.setAttributeNodeNS(newAttr);
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		return this.element.getElementsByTagNameNS(namespaceURI, localName);
	}

	public boolean hasAttribute(String name) {
		return this.element.hasAttribute(name);
	}

	public boolean hasAttributeNS(String namespaceURI, String localName) {
		return this.element.hasAttributeNS(namespaceURI, localName);
	}

	public TypeInfo getSchemaTypeInfo() {
		return this.element.getSchemaTypeInfo();
	}

	public void setIdAttribute(String name, boolean isId) {
		this.element.setIdAttribute(name, isId);
	}

	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
		this.element.setIdAttributeNS(namespaceURI, localName, isId);
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId) {
		this.element.setIdAttributeNode(idAttr, isId);
	}

	public String getNodeName() {
		return this.element.getNodeName();
	}

	public String getNodeValue() {
		return this.element.getNodeValue();
	}

	public void setNodeValue(String nodeValue) {
		this.element.setNodeValue(nodeValue);
	}

	public short getNodeType() {
		return this.element.getNodeType();
	}

	public Node getParentNode() {
		return this.element.getParentNode();
	}

	public NodeList getChildNodes() {
		return this.element.getChildNodes();
	}

	public Node getFirstChild() {
		return this.element.getFirstChild();
	}

	public Node getLastChild() {
		return this.element.getLastChild();
	}

	public Node getPreviousSibling() {
		return this.element.getPreviousSibling();
	}

	public Node getNextSibling() {
		return this.element.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return this.element.getAttributes();
	}

	public Document getOwnerDocument() {
		return this.element.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) {
		return this.element.insertBefore(newChild, refChild);
	}

	public Node replaceChild(Node newChild, Node oldChild) {
		return this.element.replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) {
		return this.element.removeChild(oldChild);
	}

	public Node appendChild(Node newChild) {
		return this.element.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return this.element.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return this.element.cloneNode(deep);
	}

	public void normalize() {
		this.element.normalize();
	}

	public boolean isSupported(String feature, String version) {
		return this.element.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return this.element.getNamespaceURI();
	}

	public String getPrefix() {
		return this.element.getPrefix();
	}

	public void setPrefix(String prefix) {
		this.element.setPrefix(prefix);
	}

	public String getLocalName() {
		return this.element.getLocalName();
	}

	public boolean hasAttributes() {
		return this.element.hasAttributes();
	}

	public String getBaseURI() {
		return this.element.getBaseURI();
	}

	public short compareDocumentPosition(Node other) {
		return this.element.compareDocumentPosition(other);
	}

	public String getTextContent() {
		return this.element.getTextContent();
	}

	public void setTextContent(String textContent) {
		this.element.setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return this.element.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return this.element.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return this.element.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return this.element.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return this.element.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return this.element.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return this.element.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return this.element.getUserData(key);
	}

}
