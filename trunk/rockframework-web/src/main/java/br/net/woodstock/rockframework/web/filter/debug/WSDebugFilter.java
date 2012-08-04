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
package br.net.woodstock.rockframework.web.filter.debug;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.xml.sax.SAXException;

import br.net.woodstock.rockframework.ExecutionException;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;

public class WSDebugFilter extends DebugFilter {

	private static final String	WSDL_PARAMETER	= "wsdl";

	@Override
	protected boolean isDebugEnabled(final HttpServletRequest request) {
		if (request.getParameter(WSDebugFilter.WSDL_PARAMETER) != null) {
			return false;
		}
		return true;
	}

	@Override
	protected String getRequestText(final byte[] bytes) throws IOException {
		return this.toXML(bytes);
	}

	@Override
	protected String getResponseText(final byte[] bytes) throws IOException {
		return this.toXML(bytes);
	}

	private String toXML(final byte[] bytes) throws IOException {
		try {
			XmlDocument document = XmlDocument.read(new ByteArrayInputStream(bytes));
			String xml = document.toString();
			return xml;
		} catch (SAXException e) {
			throw new ExecutionException(e);
		}
	}

}
