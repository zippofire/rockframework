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

	public ElementWrapper(final Element element) {
		super();
		this.element = element;
	}

	protected Element getElement() {
		return this.element;
	}

	protected void setElement(final Element element) {
		this.element = element;
	}

	@Override
	public String getTagName() {
		return this.element.getTagName();
	}

	@Override
	public String getAttribute(final String name) {
		return this.element.getAttribute(name);
	}

	@Override
	public void setAttribute(final String name, final String value) {
		this.element.setAttribute(name, value);
	}

	@Override
	public void removeAttribute(final String name) {
		this.element.removeAttribute(name);
	}

	@Override
	public Attr getAttributeNode(final String name) {
		return this.element.getAttributeNode(name);
	}

	@Override
	public Attr setAttributeNode(final Attr newAttr) {
		return this.element.setAttributeNode(newAttr);
	}

	@Override
	public Attr removeAttributeNode(final Attr oldAttr) {
		return this.element.removeAttributeNode(oldAttr);
	}

	@Override
	public NodeList getElementsByTagName(final String name) {
		return this.element.getElementsByTagName(name);
	}

	@Override
	public String getAttributeNS(final String namespaceURI, final String localName) {
		return this.element.getAttributeNS(namespaceURI, localName);
	}

	@Override
	public void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value) {
		this.element.setAttributeNS(namespaceURI, qualifiedName, value);
	}

	@Override
	public void removeAttributeNS(final String namespaceURI, final String localName) {
		this.element.removeAttributeNS(namespaceURI, localName);
	}

	@Override
	public Attr getAttributeNodeNS(final String namespaceURI, final String localName) {
		return this.element.getAttributeNodeNS(namespaceURI, localName);
	}

	@Override
	public Attr setAttributeNodeNS(final Attr newAttr) {
		return this.element.setAttributeNodeNS(newAttr);
	}

	@Override
	public NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) {
		return this.element.getElementsByTagNameNS(namespaceURI, localName);
	}

	@Override
	public boolean hasAttribute(final String name) {
		return this.element.hasAttribute(name);
	}

	@Override
	public boolean hasAttributeNS(final String namespaceURI, final String localName) {
		return this.element.hasAttributeNS(namespaceURI, localName);
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		return this.element.getSchemaTypeInfo();
	}

	@Override
	public void setIdAttribute(final String name, final boolean isId) {
		this.element.setIdAttribute(name, isId);
	}

	@Override
	public void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) {
		this.element.setIdAttributeNS(namespaceURI, localName, isId);
	}

	@Override
	public void setIdAttributeNode(final Attr idAttr, final boolean isId) {
		this.element.setIdAttributeNode(idAttr, isId);
	}

	@Override
	public String getNodeName() {
		return this.element.getNodeName();
	}

	@Override
	public String getNodeValue() {
		return this.element.getNodeValue();
	}

	@Override
	public void setNodeValue(final String nodeValue) {
		this.element.setNodeValue(nodeValue);
	}

	@Override
	public short getNodeType() {
		return this.element.getNodeType();
	}

	@Override
	public Node getParentNode() {
		return this.element.getParentNode();
	}

	@Override
	public NodeList getChildNodes() {
		return this.element.getChildNodes();
	}

	@Override
	public Node getFirstChild() {
		return this.element.getFirstChild();
	}

	@Override
	public Node getLastChild() {
		return this.element.getLastChild();
	}

	@Override
	public Node getPreviousSibling() {
		return this.element.getPreviousSibling();
	}

	@Override
	public Node getNextSibling() {
		return this.element.getNextSibling();
	}

	@Override
	public NamedNodeMap getAttributes() {
		return this.element.getAttributes();
	}

	@Override
	public Document getOwnerDocument() {
		return this.element.getOwnerDocument();
	}

	@Override
	public Node insertBefore(final Node newChild, final Node refChild) {
		return this.element.insertBefore(newChild, refChild);
	}

	@Override
	public Node replaceChild(final Node newChild, final Node oldChild) {
		return this.element.replaceChild(newChild, oldChild);
	}

	@Override
	public Node removeChild(final Node oldChild) {
		return this.element.removeChild(oldChild);
	}

	@Override
	public Node appendChild(final Node newChild) {
		return this.element.appendChild(newChild);
	}

	@Override
	public boolean hasChildNodes() {
		return this.element.hasChildNodes();
	}

	@Override
	public Node cloneNode(final boolean deep) {
		return this.element.cloneNode(deep);
	}

	@Override
	public void normalize() {
		this.element.normalize();
	}

	@Override
	public boolean isSupported(final String feature, final String version) {
		return this.element.isSupported(feature, version);
	}

	@Override
	public String getNamespaceURI() {
		return this.element.getNamespaceURI();
	}

	@Override
	public String getPrefix() {
		return this.element.getPrefix();
	}

	@Override
	public void setPrefix(final String prefix) {
		this.element.setPrefix(prefix);
	}

	@Override
	public String getLocalName() {
		return this.element.getLocalName();
	}

	@Override
	public boolean hasAttributes() {
		return this.element.hasAttributes();
	}

	@Override
	public String getBaseURI() {
		return this.element.getBaseURI();
	}

	@Override
	public short compareDocumentPosition(final Node other) {
		return this.element.compareDocumentPosition(other);
	}

	@Override
	public String getTextContent() {
		return this.element.getTextContent();
	}

	@Override
	public void setTextContent(final String textContent) {
		this.element.setTextContent(textContent);
	}

	@Override
	public boolean isSameNode(final Node other) {
		return this.element.isSameNode(other);
	}

	@Override
	public String lookupPrefix(final String namespaceURI) {
		return this.element.lookupPrefix(namespaceURI);
	}

	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		return this.element.isDefaultNamespace(namespaceURI);
	}

	@Override
	public String lookupNamespaceURI(final String prefix) {
		return this.element.lookupNamespaceURI(prefix);
	}

	@Override
	public boolean isEqualNode(final Node arg) {
		return this.element.isEqualNode(arg);
	}

	@Override
	public Object getFeature(final String feature, final String version) {
		return this.element.getFeature(feature, version);
	}

	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		return this.element.setUserData(key, data, handler);
	}

	@Override
	public Object getUserData(final String key) {
		return this.element.getUserData(key);
	}

}
