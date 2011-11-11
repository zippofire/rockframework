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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlDocument extends DocumentWrapper {

	private static final long	serialVersionUID	= -3892243357826950608L;

	public static final String	DEFAULT_NS_PREFIX	= XMLConstants.DEFAULT_NS_PREFIX;

	public static final String	XML_NS_PREFIX		= XMLConstants.XML_NS_PREFIX;

	public static final String	XML_NS_URI			= XMLConstants.XML_NS_URI;

	private XmlElement			root;

	protected XmlDocument() {
		super();
	}

	public XmlDocument(final String name) {
		super();
		Document doc = XmlHelper.getDocumentBuilder().newDocument();
		Element e = doc.createElement(name);
		doc.appendChild(e);
		this.setDocument(doc);
		this.root = XmlElement.toXmlElement(e);
	}

	public XmlDocument(final Element root) {
		super();
		Document doc = XmlHelper.getDocumentBuilder().newDocument();
		doc.appendChild(root);
		this.setDocument(doc);
		this.root = XmlElement.toXmlElement(root);
	}

	private XmlDocument(final Document d) {
		super();
		NodeList nodes = d.getChildNodes();
		Element root = null;
		int length = nodes.getLength();
		for (int i = 0; i < length; i++) {
			if (nodes.item(i) instanceof Element) {
				root = (Element) nodes.item(i);
			}
		}
		this.setDocument(d);
		this.root = XmlElement.toXmlElement(root);
	}

	public XmlElement getRoot() {
		return this.root;
	}

	public static XmlDocument read(final Reader reader, final Charset charset) throws SAXException, IOException {
		InputSource source = new InputSource(reader);
		source.setEncoding(charset.name());
		Document document = XmlHelper.getDocumentBuilder().parse(source);
		return new XmlDocument(document);
	}

	public void write(final OutputStream out) throws IOException {
		this.write(new OutputStreamWriter(out), Charset.defaultCharset());
	}

	public void write(final Writer writer) throws IOException {
		this.write(writer, Charset.defaultCharset());
	}

	public void write(final OutputStream out, final Charset charset) throws IOException {
		this.write(new OutputStreamWriter(out, charset));
	}

	public void write(final Writer writer, final Charset charset) throws IOException {
		XmlWriter.getInstance().write(this.getDocument(), writer, charset);
	}

	public static XmlDocument toXmlDocument(final Document d) {
		return new XmlDocument(d);
	}

	@Override
	protected Object clone() {
		XmlDocument doc = new XmlDocument();
		doc.importNode(this.root, true);
		return doc;
	}

	@Override
	public int compareTo(final Document o) {
		if (o == null) {
			return 0;
		}
		XmlDocument doc = o instanceof XmlDocument ? (XmlDocument) o : XmlDocument.toXmlDocument(o);
		return this.root.getNodeName().compareTo(doc.root.getNodeName());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof XmlDocument) {
			return this.getDocument().equals(((XmlDocument) obj).getDocument());
		}
		if (obj instanceof Document) {
			return this.getDocument().equals(obj);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getDocument().hashCode();
	}

	@Override
	public String toString() {
		try {
			StringWriter writer = new StringWriter();
			this.write(writer);
			return writer.toString();
		} catch (IOException e) {
			throw new br.net.woodstock.rockframework.io.IOException(e);
		}
	}

	// Static
	public static XmlDocument read(final InputStream input) throws SAXException, IOException {
		return XmlDocument.read(new InputStreamReader(input), Charset.defaultCharset());
	}

	public static XmlDocument read(final Reader reader) throws SAXException, IOException {
		return XmlDocument.read(reader, Charset.defaultCharset());
	}

	public static XmlDocument read(final InputStream input, final Charset charset) throws SAXException, IOException {
		return XmlDocument.read(new InputStreamReader(input), charset);
	}

}
