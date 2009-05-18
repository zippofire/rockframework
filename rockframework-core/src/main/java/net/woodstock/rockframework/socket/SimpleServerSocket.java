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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public abstract class SimpleServerSocket extends Thread {

	private ServerSocket	server;

	private int				port;

	private boolean			run;

	public SimpleServerSocket(int port) throws IOException {
		this.port = port;
		this.run = true;
		this.server = new ServerSocket(this.port, 0, InetAddress.getLocalHost());
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerSocket getServer() {
		return this.server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		super.run();
		String h = this.server.getInetAddress().getHostName();
		int p = this.server.getLocalPort();
		while (this.run) {
			try {
				this.getLogger().debug("Wait for connections on " + h + ":" + p);
				this.handle(this.server.accept());
			} catch (Exception e) {
				this.getLogger().error(e.getMessage(), e);
				this.run = false;
			}
		}
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	public abstract void handle(Socket s) throws Exception;

}
