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
package br.net.woodstock.rockframework.security.timestamp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bouncycastle.tsp.TimeStampRequest;

import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.IOUtils;

public class URLTimeStampClient extends BouncyCastleTimeStampClient {

	private static final String	CONTENT_TYPE_PROPERTY				= "Content-Type";

	private static final String	CONTENT_TYPE_VALUE					= "application/timestamp-query";

	private static final String	CONTENT_TRANSFER_ENCODING_PROPERTY	= "Content-Transfer-Encoding";

	public static final String	CONTENT_TRANSFER_ENCODING_BINARY	= "binary";

	public static final String	CONTENT_TRANSFER_ENCODING_BASE64	= "base64";

	private URL					url;

	private String				encoding;

	public URLTimeStampClient(final String url) throws MalformedURLException {
		this(new URL(url), URLTimeStampClient.CONTENT_TRANSFER_ENCODING_BINARY);
	}

	public URLTimeStampClient(final URL url) {
		this(url, URLTimeStampClient.CONTENT_TRANSFER_ENCODING_BINARY);
	}

	public URLTimeStampClient(final String url, final String encoding) throws MalformedURLException {
		this(new URL(url), encoding);
	}

	public URLTimeStampClient(final URL url, final String encoding) {
		super();
		this.url = url;
		this.encoding = encoding;
	}

	@Override
	protected byte[] sendRequest(final TimeStampRequest request) throws IOException {
		URLConnection connection = null;
		try {
			connection = this.url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			this.setConnectionProperties(connection);

			byte[] requestBytes = request.getEncoded();

			OutputStream outputStream = connection.getOutputStream();

			this.writeBytes(outputStream, requestBytes);

			outputStream.close();

			InputStream inputStream = connection.getInputStream();

			String encoding = connection.getContentEncoding();

			byte[] bytes = this.readBytes(inputStream, encoding);
			return bytes;
		} catch (IOException e) {
			throw e;
		}
	}

	protected void setConnectionProperties(final URLConnection connection) {
		connection.setRequestProperty(URLTimeStampClient.CONTENT_TYPE_PROPERTY, URLTimeStampClient.CONTENT_TYPE_VALUE);
		connection.setRequestProperty(URLTimeStampClient.CONTENT_TRANSFER_ENCODING_PROPERTY, this.encoding);
	}

	protected void writeBytes(final OutputStream outputStream, final byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	protected byte[] readBytes(final InputStream inputStream, final String encoding) throws IOException {
		byte[] bytes = IOUtils.toByteArray(inputStream);
		if (URLTimeStampClient.CONTENT_TRANSFER_ENCODING_BASE64.equals(encoding)) {
			bytes = Base64Utils.fromBase64(bytes);
		}
		return bytes;
	}

}