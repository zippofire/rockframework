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

	public void setElement(final Element element) {
		this.element = element;
	}

	public String getTagName() {
		return this.element.getTagName();
	}

	public String getAttribute(final String name) {
		return this.element.getAttribute(name);
	}

	public void setAttribute(final String name, final String value) {
		this.element.setAttribute(name, value);
	}

	public void removeAttribute(final String name) {
		this.element.removeAttribute(name);
	}

	public Attr getAttributeNode(final String name) {
		return this.element.getAttributeNode(name);
	}

	public Attr setAttributeNode(final Attr newAttr) {
		return this.element.setAttributeNode(newAttr);
	}

	public Attr removeAttributeNode(final Attr oldAttr) {
		return this.element.removeAttributeNode(oldAttr);
	}

	public NodeList getElementsByTagName(final String name) {
		return this.element.getElementsByTagName(name);
	}

	public String getAttributeNS(final String namespaceURI, final String localName) {
		return this.element.getAttributeNS(namespaceURI, localName);
	}

	public void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value) {
		this.element.setAttributeNS(namespaceURI, qualifiedName, value);
	}

	public void removeAttributeNS(final String namespaceURI, final String localName) {
		this.element.removeAttributeNS(namespaceURI, localName);
	}

	public Attr getAttributeNodeNS(final String namespaceURI, final String localName) {
		return this.element.getAttributeNodeNS(namespaceURI, localName);
	}

	public Attr setAttributeNodeNS(final Attr newAttr) {
		return this.element.setAttributeNodeNS(newAttr);
	}

	public NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) {
		return this.element.getElementsByTagNameNS(namespaceURI, localName);
	}

	public boolean hasAttribute(final String name) {
		return this.element.hasAttribute(name);
	}

	public boolean hasAttributeNS(final String namespaceURI, final String localName) {
		return this.element.hasAttributeNS(namespaceURI, localName);
	}

	public TypeInfo getSchemaTypeInfo() {
		return this.element.getSchemaTypeInfo();
	}

	public void setIdAttribute(final String name, final boolean isId) {
		this.element.setIdAttribute(name, isId);
	}

	public void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) {
		this.element.setIdAttributeNS(namespaceURI, localName, isId);
	}

	public void setIdAttributeNode(final Attr idAttr, final boolean isId) {
		this.element.setIdAttributeNode(idAttr, isId);
	}

	public String getNodeName() {
		return this.element.getNodeName();
	}

	public String getNodeValue() {
		return this.element.getNodeValue();
	}

	public void setNodeValue(final String nodeValue) {
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

	public Node insertBefore(final Node newChild, final Node refChild) {
		return this.element.insertBefore(newChild, refChild);
	}

	public Node replaceChild(final Node newChild, final Node oldChild) {
		return this.element.replaceChild(newChild, oldChild);
	}

	public Node removeChild(final Node oldChild) {
		return this.element.removeChild(oldChild);
	}

	public Node appendChild(final Node newChild) {
		return this.element.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return this.element.hasChildNodes();
	}

	public Node cloneNode(final boolean deep) {
		return this.element.cloneNode(deep);
	}

	public void normalize() {
		this.element.normalize();
	}

	public boolean isSupported(final String feature, final String version) {
		return this.element.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return this.element.getNamespaceURI();
	}

	public String getPrefix() {
		return this.element.getPrefix();
	}

	public void setPrefix(final String prefix) {
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

	public short compareDocumentPosition(final Node other) {
		return this.element.compareDocumentPosition(other);
	}

	public String getTextContent() {
		return this.element.getTextContent();
	}

	public void setTextContent(final String textContent) {
		this.element.setTextContent(textContent);
	}

	public boolean isSameNode(final Node other) {
		return this.element.isSameNode(other);
	}

	public String lookupPrefix(final String namespaceURI) {
		return this.element.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(final String namespaceURI) {
		return this.element.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(final String prefix) {
		return this.element.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(final Node arg) {
		return this.element.isEqualNode(arg);
	}

	public Object getFeature(final String feature, final String version) {
		return this.element.getFeature(feature, version);
	}

	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		return this.element.setUserData(key, data, handler);
	}

	public Object getUserData(final String key) {
		return this.element.getUserData(key);
	}

}
