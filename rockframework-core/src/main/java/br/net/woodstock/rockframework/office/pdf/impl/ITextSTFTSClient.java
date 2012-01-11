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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;

import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;

import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.TSAClient;

public class ITextSTFTSClient implements TSAClient {

	private byte[]	timestamp;

	private int		size;

	public ITextSTFTSClient() {
		super();
	}

	@Override
	public byte[] getTimeStampToken(final PdfPKCS7 caller, final byte[] imprint) throws Exception {
		SocketAddress address = new InetSocketAddress("201.49.148.134", 318);
		Socket socket = new Socket();
		socket.connect(address);

		Digester digester = new BasicDigester(DigestType.valueOf(caller.getHashAlgorithm()));
		byte[] digest = digester.digest(imprint);

		TimeStampRequestGenerator generator = new TimeStampRequestGenerator();
		generator.setCertReq(true);

		TimeStampRequest request = generator.generate(caller.getDigestAlgorithmOid(), digest, null);
		byte[] requestBytes = request.getEncoded();

		OutputStream outputStream = socket.getOutputStream();

		outputStream.write(requestBytes);
		outputStream.flush();
		socket.shutdownOutput();

		InputStream inputStream = socket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		int len = dataInputStream.readInt();
		byte status = dataInputStream.readByte();
		byte[] buffer = new byte[len - 1];

		// if (status != 5) {
		// throw new RuntimeException("Status: " + status);
		// }

		dataInputStream.readFully(buffer);
		socket.shutdownInput();

		ASN1InputStream asn1InputStream = new ASN1InputStream(buffer);

		this.timestamp = asn1InputStream.readObject().getEncoded();
		this.size = this.timestamp.length;

		socket.close();

		return this.timestamp;
	}

	@Override
	public int getTokenSizeEstimate() {
		return this.size;
	}

}
