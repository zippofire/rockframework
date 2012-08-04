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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.bouncycastle.tsp.TimeStampRequest;

import br.net.woodstock.rockframework.utils.IOUtils;

public class SocketTimeStampProcessor extends BouncyCastleTimeStampProcessor {

	private SocketAddress	address;

	public SocketTimeStampProcessor(final SocketAddress address) {
		super();
		this.address = address;
	}

	public SocketTimeStampProcessor(final String address, final int port) {
		super();
		this.address = new InetSocketAddress(address, port);
	}

	@Override
	protected byte[] sendRequest(final TimeStampRequest request) throws IOException {
		Socket socket = null;
		try {
			socket = new Socket();
			socket.connect(this.address);

			byte[] requestBytes = request.getEncoded();

			OutputStream outputStream = socket.getOutputStream();

			this.writeBytes(outputStream, requestBytes);

			socket.shutdownOutput();

			InputStream inputStream = socket.getInputStream();

			byte[] bytes = this.readBytes(inputStream);
			return bytes;
		} catch (IOException e) {
			throw e;
		} finally {
			if (socket != null) {
				if (socket.isConnected()) {
					socket.close();
				}
			}
		}
	}

	protected void writeBytes(final OutputStream outputStream, final byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	protected byte[] readBytes(final InputStream inputStream) throws IOException {
		return IOUtils.toByteArray(inputStream);
	}

}
