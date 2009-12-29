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
import java.io.Writer;

import net.woodstock.rockframework.logging.SysLogger;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

abstract class XmlWriter {

	private static final String		APACHE_XML_SERIALIZER	= "org.apache.xml.serialize.XMLSerializer";

	private static final String		SUN_XML_SERIALIZER		= "com.sun.org.apache.xml.internal.serialize.XMLSerializer";

	protected static final String	XML_ENCODING			= "UTF-8";

	private static XmlWriter		instance				= XmlWriter.getAvailable();

	protected static Log getLogger() {
		return SysLogger.getLogger();
	}

	public abstract void write(Document document, Writer writer) throws IOException;

	public static XmlWriter getInstance() {
		return XmlWriter.instance;
	}

	private static XmlWriter getAvailable() {
		try {
			Class.forName(XmlWriter.APACHE_XML_SERIALIZER);
			XmlWriter xmlWriter = new ApacheXmlWriter();
			XmlWriter.getLogger().info("Using Apache XML serializer(Xerces)");
			return xmlWriter;
		} catch (ClassNotFoundException e) {
			try {
				Class.forName(XmlWriter.SUN_XML_SERIALIZER);
				XmlWriter xmlWriter = new SunXmlWriter();
				XmlWriter.getLogger().info("Using Sun XML serializer");
				return xmlWriter;
			} catch (ClassNotFoundException ee) {
				throw new UnsupportedOperationException("No XML serializer found");
			}
		}
	}

}
