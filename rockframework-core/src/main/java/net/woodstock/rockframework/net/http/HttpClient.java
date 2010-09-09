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
package net.woodstock.rockframework.net.http;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.xml.dom.XmlDocument;
import net.woodstock.rockframework.xml.dom.XmlElement;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.xml.sax.SAXException;

public class HttpClient implements Serializable {

	private static final long									serialVersionUID	= -4741892532905721566L;

	public static final String									ERROR_ELEMENT		= "error";

	public static final String									ERRORCODE_ATTRIBUTE	= "code";

	private transient org.apache.commons.httpclient.HttpClient	client;

	public HttpClient() {
		this.client = new org.apache.commons.httpclient.HttpClient();
	}

	public String openText(final String url, final Map<String, Object> params) throws IOException {
		GetMethod method = this.createGetMethod(url, params);
		return method.getResponseBodyAsString();
	}

	public XmlDocument openXml(final String url, final Map<String, Object> params) throws IOException {
		GetMethod method = this.createGetMethod(url, params);
		int status = this.client.executeMethod(method);
		if (status == HttpStatus.SC_OK) {
			try {
				return XmlDocument.read(method.getResponseBodyAsStream());
			} catch (SAXException e) {
				throw new RuntimeException(e);
			}
		}
		XmlDocument doc = new XmlDocument(HttpClient.ERROR_ELEMENT);
		XmlElement root = doc.getRoot();
		root.setAttribute(HttpClient.ERRORCODE_ATTRIBUTE, new Integer(status));
		root.setTextContent(method.getResponseBodyAsString());
		return doc;
	}

	private GetMethod createGetMethod(final String url, final Map<String, Object> params) {
		GetMethod method = new GetMethod(url);
		if ((params != null) && (params.size() > 0)) {
			HttpMethodParams hps = new HttpMethodParams();
			for (Entry<String, Object> p : params.entrySet()) {
				String key = p.getKey();
				Object value = p.getValue();
				String valueStr = value == null ? "" : value.toString();
				hps.setParameter(key, valueStr);
			}
			method.setParams(hps);
		}
		return method;
	}

}
