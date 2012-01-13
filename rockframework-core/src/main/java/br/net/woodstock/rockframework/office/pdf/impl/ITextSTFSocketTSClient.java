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
import java.net.SocketAddress;

public class ITextSTFSocketTSClient extends SocketITextTSClient {

	public ITextSTFSocketTSClient(final SocketAddress address) {
		super(address);
	}

	public ITextSTFSocketTSClient(final String address, final int port) {
		super(address, port);
	}

	@Override
	protected void writeBytes(final OutputStream outputStream, final byte[] bytes) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

		dataOutputStream.writeInt(bytes.length + 1);
		dataOutputStream.writeByte(0);
		dataOutputStream.write(bytes);
		dataOutputStream.flush();
	}

	@Override
	protected byte[] readBytes(final InputStream inputStream) throws IOException {
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		int len = dataInputStream.readInt();
		byte status = dataInputStream.readByte();
		byte[] bytes = new byte[len - 1];

		if (status != 5) {
			throw new IllegalStateException("Illegal Status: " + status);
		}

		dataInputStream.readFully(bytes);
		return bytes;
	}

}
