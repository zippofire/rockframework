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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import net.woodstock.rockframework.logging.SysLogger;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlDocument extends DocumentWrapper {

	private static final long				serialVersionUID	= -3892243357826950608L;

	public static final String				DEFAULT_NS_PREFIX	= XMLConstants.DEFAULT_NS_PREFIX;

	public static final String				XML_NS_PREFIX		= XMLConstants.XML_NS_PREFIX;

	public static final String				XML_NS_URI			= XMLConstants.XML_NS_URI;

	private static DocumentBuilderFactory	factory;

	private static DocumentBuilder			builder;

	private XmlElement						root;

	protected XmlDocument() {
		super();
	}

	public XmlDocument(final String name) {
		super();
		Document doc = XmlDocument.builder.newDocument();
		Element e = doc.createElement(name);
		doc.appendChild(e);
		this.setDocument(doc);
		this.root = XmlElement.toXmlElement(e);
	}

	public XmlDocument(final Element root) {
		super();
		Document doc = XmlDocument.builder.newDocument();
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

	public static XmlDocument read(final File file) throws SAXException, IOException {
		return XmlDocument.read(new FileReader(file));
	}

	public static XmlDocument read(final InputStream input) throws SAXException, IOException {
		return XmlDocument.read(new InputStreamReader(input));
	}

	public static XmlDocument read(final Reader reader) throws SAXException, IOException {
		InputSource source = new InputSource(reader);
		Document document = XmlDocument.builder.parse(source);
		return new XmlDocument(document);
	}

	public static XmlDocument read(final URL url) throws SAXException, IOException {
		InputStream input = url.openStream();
		Document document = XmlDocument.builder.parse(input);
		input.close();
		return new XmlDocument(document);
	}

	public void write(final File file) throws IOException {
		this.write(new FileWriter(file));
	}

	public void write(final OutputStream out) throws IOException {
		this.write(new OutputStreamWriter(out));
	}

	public void write(final Writer writer) throws IOException {
		XmlWriter.getInstance().write(this, writer);
	}

	public boolean validateSchema(final File source, final String ns) throws SAXException, IOException {
		return this.validateSchema(new StreamSource(source), ns);
	}

	public boolean validateSchema(final URL source, final String ns) throws SAXException, IOException {
		return this.validateSchema(source.openStream(), ns);
	}

	public boolean validateSchema(final InputStream source, final String ns) throws SAXException, IOException {
		return this.validateSchema(new StreamSource(source), ns);
	}

	public boolean validateSchema(final Reader source, final String ns) throws SAXException, IOException {
		return this.validateSchema(new StreamSource(source), ns);
	}

	public boolean validateSchema(final Source source, final String ns) throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance(ns);
		Schema schema = factory.newSchema(source);
		Validator validator = schema.newValidator();
		try {
			validator.validate(new DOMSource(this.getDocument()));
		} catch (SAXException e) {
			this.getLogger().warn(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public void validateSchemaWithError(final File source, final String ns) throws SAXException, IOException {
		this.validateSchema(new StreamSource(source), ns);
	}

	public void validateSchemaWithError(final URL source, final String ns) throws SAXException, IOException {
		this.validateSchema(source.openStream(), ns);
	}

	public void validateSchemaWithError(final InputStream source, final String ns) throws SAXException, IOException {
		this.validateSchema(new StreamSource(source), ns);
	}

	public void validateSchemaWithError(final Reader source, final String ns) throws SAXException, IOException {
		this.validateSchema(new StreamSource(source), ns);
	}

	public void validateSchemaWithError(final Source source, final String ns) throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance(ns);
		Schema schema = factory.newSchema(source);
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(this.getDocument()));
	}

	public static Document toDocument(final XmlDocument d) {
		return d.getDocument();
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
			throw new RuntimeException(e);
		}
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	static DocumentBuilder getDocumentBuilder() {
		return XmlDocument.builder;
	}

	static {
		try {
			XmlDocument.factory = DocumentBuilderFactory.newInstance();
			XmlDocument.builder = XmlDocument.factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
