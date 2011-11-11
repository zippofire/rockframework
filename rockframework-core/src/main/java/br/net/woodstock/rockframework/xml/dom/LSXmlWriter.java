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
import java.io.Writer;
import java.nio.charset.Charset;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class LSXmlWriter extends XmlWriter {

	private static final String	FEATURE_NAME		= "LS";

	private static final String	FEATURE_VERSION		= "3.0";

	private static final String	PRETTY_PRINT_OPTION	= "format-pretty-print";

	private static final Object	PRETTY_PRINT_VALUE	= Boolean.TRUE;

	@Override
	public void write(final Node node, final Writer writer, final Charset encoding) throws IOException {
		DOMImplementation domImpl = XmlHelper.getDocumentBuilder().getDOMImplementation();
		DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature(LSXmlWriter.FEATURE_NAME, LSXmlWriter.FEATURE_VERSION);

		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter(LSXmlWriter.PRETTY_PRINT_OPTION, LSXmlWriter.PRETTY_PRINT_VALUE);

		LSOutput output = domImplLS.createLSOutput();
		output.setCharacterStream(writer);
		output.setEncoding(encoding.name());

		serializer.write(node, output);
	}
}
