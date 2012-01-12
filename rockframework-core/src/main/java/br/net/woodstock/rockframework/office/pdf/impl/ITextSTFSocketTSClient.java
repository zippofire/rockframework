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
package br.net.woodstock.rockframework.office.pdf.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.bouncycastle.tsp.TimeStampRequest;

public class ITextSTFSocketTSClient extends LoggableITextTSAClient {

	private SocketAddress	address;

	public ITextSTFSocketTSClient(final SocketAddress address) {
		super();
		this.address = address;
	}

	public ITextSTFSocketTSClient(final String address, final int port) {
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
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

			dataOutputStream.writeInt(requestBytes.length + 1);
			dataOutputStream.writeByte(0);
			dataOutputStream.write(requestBytes);
			dataOutputStream.flush();
			socket.shutdownOutput();

			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			int len = dataInputStream.readInt();
			byte status = dataInputStream.readByte();
			byte[] bytes = new byte[len - 1];

			if (status != 5) {
				throw new IllegalStateException("Illegal Status: " + status);
			}

			dataInputStream.readFully(bytes);
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

}
