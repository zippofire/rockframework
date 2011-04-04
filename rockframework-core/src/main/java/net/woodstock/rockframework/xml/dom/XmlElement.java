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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlElement extends ElementWrapper {

	private static final long	serialVersionUID	= 8642703359069757721L;

	protected XmlElement(final Element e) {
		super(e);
	}

	// Document
	public XmlDocument getOwnerXmlDocument() {
		return XmlDocument.toXmlDocument(this.getOwnerDocument());
	}

	public CDATASection addCDATASection(final String data) {
		CDATASection cdata = this.getOwnerDocument().createCDATASection(data);
		this.appendChild(cdata);
		return cdata;
	}

	public Comment addComment(final String data) {
		Comment c = this.getOwnerDocument().createComment(data);
		this.appendChild(c);
		return c;
	}

	public XmlElement addElement(final Element e) {
		Element tmp = e;
		if (tmp instanceof XmlElement) {
			tmp = ((XmlElement) tmp).getElement();
		}
		Document doc = this.getOwnerDocument();

		Element ee = (Element) doc.importNode(tmp, true);
		this.appendChild(ee);
		return XmlElement.toXmlElement(ee);
	}

	public XmlElement addElement(final String name) {
		Document doc = this.getOwnerDocument();
		Element e = doc.createElement(name);
		this.appendChild(e);
		return XmlElement.toXmlElement(e);
	}

	public void copy(final Document d) {
		NodeList list = d.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n instanceof Element) {
				this.copy(n);
				break;
			}
		}
	}

	public void copy(final Node n) {
		NodeList list = n.getChildNodes();
		Document doc = this.getOwnerDocument();
		for (int i = 0; i < list.getLength(); i++) {
			Node nn = doc.importNode(list.item(i), true);
			this.appendChild(nn);
		}
		NamedNodeMap map = n.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Attr a = (Attr) doc.importNode(map.item(i), true);
			this.setAttributeNode(a);
		}
	}

	public void removeChild(final String name) {
		Element e = this.getElement(name);
		if (e != null) {
			this.removeChild(e);
		}
	}

	public void removeChild(final Element e) {
		if (e instanceof XmlElement) {
			this.removeChild(((XmlElement) e).getElement());
		} else {
			this.removeChild(e);
		}
	}

	public boolean hasElement(final String name) {
		return this.getElement(name) != null;
	}

	public XmlElement getElement(final String name) {
		NodeList nodes = this.getElementsByTagName(name);
		int length = nodes.getLength();
		for (int i = 0; i < length; i++) {
			if ((nodes.item(i) instanceof Element) && (nodes.item(i).getNodeName().equals(name))) {
				return XmlElement.toXmlElement((Element) nodes.item(i));
			}
		}
		return null;
	}

	public List<XmlElement> getElements() {
		NodeList nodes = this.getChildNodes();
		int length = nodes.getLength();
		List<XmlElement> list = new LinkedList<XmlElement>();
		for (int i = 0; i < length; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				list.add(XmlElement.toXmlElement(element));
			}
		}
		return list;
	}

	public List<XmlElement> getElements(final String name) {
		NodeList nodes = this.getChildNodes();
		int length = nodes.getLength();
		List<XmlElement> list = new LinkedList<XmlElement>();
		for (int i = 0; i < length; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				if (element.getTagName().equalsIgnoreCase(name)) {
					list.add(XmlElement.toXmlElement(element));
				}
			}
		}
		return list;
	}

	public void setData(final Object data) {
		if (data == null) {
			return;
		}
		Document doc = this.getOwnerDocument();
		String strData = String.valueOf(data);
		Text t = doc.createTextNode(strData);
		this.appendChild(t);
	}

	public String getData() {
		NodeList list = this.getChildNodes();

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if ((node instanceof CDATASection) || (node instanceof CharacterData) || (node instanceof Text)) {
				String nodeValue = node.getNodeValue();
				if (nodeValue != null) {
					nodeValue = nodeValue.trim();
					builder.append(nodeValue);
				}
			}
		}

		String value = builder.toString();

		return value;
	}

	@Override
	public String getAttribute(final String name) {
		String attribute = super.getAttribute(name);
		return attribute;
	}

	public void setAttribute(final String name, final Object value) {
		// String strValue = WRITE_CHARSET_TRANSFORM.transform(String.valueOf(value));
		String strValue = String.valueOf(value);
		super.setAttribute(name, String.valueOf(strValue));
	}

	public void write(final OutputStream out) throws IOException {
		this.write(new OutputStreamWriter(out));
	}

	public void write(final Writer writer) throws IOException {
		Document doc = XmlDocument.getDocumentBuilder().newDocument();
		Node node = doc.importNode(this.getElement(), true);
		doc.appendChild(node);
		XmlDocument.toXmlDocument(doc).write(writer);
	}

	public static XmlElement toXmlElement(final Element e) {
		return new XmlElement(e);
	}

	@Override
	public int compareTo(final Element o) {
		if (o == null) {
			return 0;
		}
		return this.getNodeName().compareTo(o.getNodeName());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof XmlElement) {
			return this.getElement().equals(((XmlElement) obj).getElement());
		}
		if (obj instanceof Element) {
			return this.getElement().equals(obj);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getElement().hashCode();
	}

	@Override
	public String toString() {
		try {
			StringWriter writer = new StringWriter();
			this.write(writer);
			return writer.toString();
		} catch (IOException e) {
			throw new net.woodstock.rockframework.io.IOException(e);
		}
	}
}
