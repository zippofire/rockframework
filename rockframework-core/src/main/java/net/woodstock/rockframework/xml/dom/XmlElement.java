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
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.woodstock.rockframework.utils.Base64Utils;
import net.woodstock.rockframework.utils.StringUtils;

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

	private static DateFormat	dateFormat;

	public static final String	CLASS_ATTRIBUTE		= "class";

	protected XmlElement() {
		super();
	}

	protected XmlElement(final Element e) {
		super();
		this.setElement(e);
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

	public XmlElement addElement(final String name, final Map<String, String> attributes) {
		Document doc = this.getOwnerDocument();
		Element e = doc.createElement(name);
		Set<String> keys = attributes.keySet();
		for (String k : keys) {
			e.setAttribute(k, attributes.get(k));
		}
		this.appendChild(e);
		return XmlElement.toXmlElement(e);
	}

	public XmlElement addElement(final String name, final boolean data) {
		return this.addElement(name, Boolean.toString(data));
	}

	public XmlElement addElement(final String name, final byte data) {
		return this.addElement(name, Byte.toString(data));
	}

	public XmlElement addElement(final String name, final char data) {
		return this.addElement(name, Character.toString(data));
	}

	public XmlElement addElement(final String name, final Date data) {
		return this.addElement(name, XmlElement.dateFormat.format(data));
	}

	public XmlElement addElement(final String name, final double data) {
		return this.addElement(name, Double.toString(data));
	}

	public XmlElement addElement(final String name, final float data) {
		return this.addElement(name, Float.toString(data));
	}

	public XmlElement addElement(final String name, final int data) {
		return this.addElement(name, Integer.toString(data));
	}

	public XmlElement addElement(final String name, final long data) {
		return this.addElement(name, Long.toString(data));
	}

	public XmlElement addElement(final String name, final short data) {
		return this.addElement(name, Short.toString(data));
	}

	public XmlElement addElement(final String name, final String data) {
		Document doc = this.getOwnerDocument();
		Element e = doc.createElement(name);
		Text t = doc.createTextNode(data);
		e.appendChild(t);
		this.appendChild(e);
		return XmlElement.toXmlElement(e);
	}

	public XmlElement addElement(final String name, final boolean data, final Map<String, String> attributes) {
		return this.addElement(name, Boolean.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final byte data, final Map<String, String> attributes) {
		return this.addElement(name, Byte.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final char data, final Map<String, String> attributes) {
		return this.addElement(name, Character.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final Date data, final Map<String, String> attributes) {
		return this.addElement(name, XmlElement.dateFormat.format(data), attributes);
	}

	public XmlElement addElement(final String name, final double data, final Map<String, String> attributes) {
		return this.addElement(name, Double.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final float data, final Map<String, String> attributes) {
		return this.addElement(name, Float.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final int data, final Map<String, String> attributes) {
		return this.addElement(name, Integer.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final long data, final Map<String, String> attributes) {
		return this.addElement(name, Long.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final short data, final Map<String, String> attributes) {
		return this.addElement(name, Short.toString(data), attributes);
	}

	public XmlElement addElement(final String name, final String data, final Map<String, String> attributes) {
		Document doc = this.getOwnerDocument();
		Element e = doc.createElement(name);
		Set<String> keys = attributes.keySet();
		Text t = doc.createTextNode(data);
		e.appendChild(t);
		for (String k : keys) {
			e.setAttribute(k, attributes.get(k));
		}
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

	public void copy(final Element e) {
		this.copy((Node) e);
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

	public void removeElement(final String name) {
		this.removeElement(this.getElement(name));
	}

	public void removeElement(final Element e) {
		this.removeChild(e);
	}

	public void removeElement(final XmlElement e) {
		this.removeChild(e.getElement());
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
			if (nodes.item(i) instanceof Element) {
				list.add(XmlElement.toXmlElement((Element) nodes.item(i)));
			}
		}
		return list;
	}

	public List<XmlElement> getElements(final String name) {
		NodeList nodes = this.getElementsByTagName(name);
		int length = nodes.getLength();
		List<XmlElement> list = new LinkedList<XmlElement>();
		for (int i = 0; i < length; i++) {
			if (nodes.item(i) instanceof Element) {
				list.add(XmlElement.toXmlElement((Element) nodes.item(i)));
			}
		}
		return list;
	}

	public boolean getBoolean() {
		return this.getBooleanNvl(false);
	}

	public boolean getBooleanNvl(final boolean nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Boolean.parseBoolean(s);
	}

	public boolean getBoolean(final String name) {
		return this.getBooleanNvl(name, false);
	}

	public boolean getBooleanNvl(final String name, final boolean nvl) {
		return Boolean.parseBoolean(this.getStringNvl(name, Boolean.toString(nvl)));
	}

	public byte getByte() {
		return this.getByteNvl((byte) 0);
	}

	public byte getByteNvl(final byte nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Byte.parseByte(s);
	}

	public byte getByte(final String name) {
		return this.getByteNvl(name, (byte) 0);
	}

	public byte getByteNvl(final String name, final byte nvl) {
		return Byte.parseByte(this.getStringNvl(name, Byte.toString(nvl)));
	}

	public char getChar() {
		return this.getCharNvl('\0');
	}

	public char getCharNvl(final char nvl) {
		String s = this.getString();
		if ((s == null) || (s.length() < 1)) {
			return nvl;
		}
		return s.charAt(0);
	}

	public char getChar(final String name) {
		return this.getCharNvl(name, '\0');
	}

	public char getCharNvl(final String name, final char nvl) {
		String s = this.getString(name);
		if ((s == null) || (s.length() < 1)) {
			return nvl;
		}
		return s.charAt(0);
	}

	public Date getDate() throws ParseException {
		return this.getDateNvl((Date) null);
	}

	public Date getDateNvl(final Date nvl) throws ParseException {
		String s = this.getString();
		if (StringUtils.isEmpty(s)) {
			return nvl;
		}
		return XmlElement.dateFormat.parse(s);
	}

	public Date getDate(final String name) throws ParseException {
		return this.getDateNvl(name, null);
	}

	public Date getDateNvl(final String name, final Date nvl) throws ParseException {
		String s = this.getString(name);
		if (StringUtils.isEmpty(s)) {
			return nvl;
		}
		return XmlElement.dateFormat.parse(s);
	}

	public double getDouble() {
		return this.getDoubleNvl(0);
	}

	public double getDoubleNvl(final double nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Double.parseDouble(s);
	}

	public double getDouble(final String name) {
		return this.getDoubleNvl(name, 0);
	}

	public double getDoubleNvl(final String name, final double nvl) {
		return Double.parseDouble(this.getStringNvl(name, Double.toString(nvl)));
	}

	public float getFloat() {
		return this.getFloatNvl(0);
	}

	public float getFloatNvl(final float nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Float.parseFloat(s);
	}

	public float getFloat(final String name) {
		return this.getFloatNvl(name, 0);
	}

	public float getFloatNvl(final String name, final float nvl) {
		return Float.parseFloat(this.getStringNvl(name, Float.toString(nvl)));
	}

	public int getInt() {
		return this.getIntNvl(0);
	}

	public int getIntNvl(final int nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Integer.parseInt(s);
	}

	public int getInt(final String name) {
		return this.getIntNvl(name, 0);
	}

	public int getIntNvl(final String name, final int nvl) {
		return Integer.parseInt(this.getStringNvl(name, Integer.toString(nvl)));
	}

	public long getLong() {
		return this.getLongNvl(0);
	}

	public long getLongNvl(final long nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Long.parseLong(s);
	}

	public long getLong(final String name) {
		return this.getLongNvl(name, 0);
	}

	public long getLongNvl(final String name, final long nvl) {
		return Long.parseLong(this.getStringNvl(name, Long.toString(nvl)));
	}

	public short getShort() {
		return this.getShortNvl((short) 0);
	}

	public short getShortNvl(final short nvl) {
		String s = this.getString();
		if (s == null) {
			return nvl;
		}
		return Short.parseShort(s);
	}

	public short getShort(final String name) {
		return this.getShortNvl(name, (short) 0);
	}

	public short getShortNvl(final String name, final short nvl) {
		return Short.parseShort(this.getStringNvl(name, Short.toString(nvl)));
	}

	public String getString() {
		return this.getStringNvl(null);
	}

	public String getStringNvl(final String nvl) {
		NodeList list = this.getChildNodes();

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if ((node instanceof CDATASection) || (node instanceof CharacterData) || (node instanceof Text)) {
				String nodeValue = node.getNodeValue();
				if (nodeValue != null) {
					builder.append(nodeValue.trim());
				}
			}
		}

		String value = builder.toString();

		if (StringUtils.isEmpty(value)) {
			return nvl;
		}

		return value;
	}

	public String getString(final String name) {
		return this.getStringNvl(name, null);
	}

	public String getStringNvl(final String name, final String nvl) {
		XmlElement e = this.getElement(name);
		return e.getStringNvl(nvl);
	}

	public XmlElement addObject(final String name, final Serializable object) throws IOException {
		byte[] bytes = Base64Utils.serialize(object);

		HashMap<String, String> attributes = new HashMap<String, String>();

		String className = null;

		if (object.getClass().isArray()) {
			className = object.getClass().getComponentType().getCanonicalName();
		} else {
			className = object.getClass().getCanonicalName();
		}

		attributes.put(XmlElement.CLASS_ATTRIBUTE, className);

		return this.addElement(name, new String(bytes), attributes);
	}

	public Object getObject() throws IOException, ClassNotFoundException {
		if (StringUtils.isEmpty(this.getString())) {
			return null;
		}
		Object o = Base64Utils.unserialize(this.getString().getBytes());
		return o;
	}

	public Object getObject(final String name) throws IOException, ClassNotFoundException {
		if (StringUtils.isEmpty(this.getString(name))) {
			return null;
		}
		Object o = Base64Utils.unserialize(this.getString(name).getBytes());
		return o;
	}

	public void setAttribute(final String name, final boolean data) {
		super.setAttribute(name, Boolean.toString(data));
	}

	public void setAttribute(final String name, final byte data) {
		super.setAttribute(name, Byte.toString(data));
	}

	public void setAttribute(final String name, final char data) {
		super.setAttribute(name, Character.toString(data));
	}

	public void setAttribute(final String name, final Date data) {
		super.setAttribute(name, XmlElement.dateFormat.format(data));
	}

	public void setAttribute(final String name, final double data) {
		super.setAttribute(name, Double.toString(data));
	}

	public void setAttribute(final String name, final float data) {
		super.setAttribute(name, Float.toString(data));
	}

	public void setAttribute(final String name, final int data) {
		super.setAttribute(name, Integer.toString(data));
	}

	public void setAttribute(final String name, final long data) {
		super.setAttribute(name, Long.toString(data));
	}

	public void setAttribute(final String name, final short data) {
		super.setAttribute(name, Short.toString(data));
	}

	public void write(final File file) throws IOException {
		this.write(new FileWriter(file));
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

	public static Element toElement(final XmlElement e) {
		return e.getElement();
	}

	public static XmlElement toXmlElement(final Element e) {
		return new XmlElement(e);
	}

	public static List<Element> toElementList(final List<XmlElement> list) {
		List<Element> x = new LinkedList<Element>();
		for (XmlElement e : list) {
			x.add(XmlElement.toElement(e));
		}
		return x;
	}

	public static List<XmlElement> toXmlElementList(final List<Element> list) {
		List<XmlElement> x = new LinkedList<XmlElement>();
		for (Element e : list) {
			x.add(XmlElement.toXmlElement(e));
		}
		return x;
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
			throw new RuntimeException(e);
		}
	}

	static {
		XmlElement.dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
	}
}
