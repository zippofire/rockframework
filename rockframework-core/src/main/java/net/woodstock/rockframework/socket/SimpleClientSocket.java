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
package net.woodstock.rockframework.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public abstract class SimpleClientSocket extends Thread {

	private String	host;

	private int		port;

	private Socket	socket;

	public SimpleClientSocket(final String host, final int port) throws IOException {
		this.host = host;
		this.port = port;
		this.socket = new Socket(this.host, this.port);
	}

	public void reConnect() throws IOException {
		if (!this.socket.isClosed()) {
			this.socket.close();
		}
		this.socket.connect(new InetSocketAddress(this.host, this.port));
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public void run() {
		if (!this.socket.isClosed()) {
			try {
				this.handle(this.socket);
			} catch (Exception e) {
				this.getLogger().warn(e.getMessage(), e);
				try {
					if (!this.socket.isClosed()) {
						this.socket.close();
					}
				} catch (IOException ee) {
					this.getLogger().error(ee.getMessage(), ee);
				}
			}
		}
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	public abstract void handle(Socket s) throws Exception;

}
