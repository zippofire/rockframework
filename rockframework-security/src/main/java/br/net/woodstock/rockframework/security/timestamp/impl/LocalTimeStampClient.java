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

import org.bouncycastle.tsp.TimeStampRequest;

import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;

public class LocalTimeStampClient extends BouncyCastleTimeStampClient {

	private TimeStampServer	server;

	public LocalTimeStampClient(final TimeStampServer server) {
		super();
		this.server = server;
	}

	@Override
	protected byte[] sendRequest(final TimeStampRequest request) throws IOException {
		byte[] requestBytes = request.getEncoded();
		byte[] responseBytes = this.server.getTimeStamp(requestBytes);
		return responseBytes;
	}

}
