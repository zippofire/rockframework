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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.woodstock.rockframework.logging.SysLogger;
import net.woodstock.rockframework.utils.NetUtils;

import org.apache.commons.logging.Log;

public abstract class SimpleServerSocket extends Thread {

	private ServerSocket	server;

	private boolean			run;

	public SimpleServerSocket(final int port) throws IOException {
		this(InetAddress.getLocalHost(), port);
	}

	public SimpleServerSocket(final String address, final int port) throws IOException {
		this(NetUtils.toAddress(address), port);
	}

	public SimpleServerSocket(final InetAddress address, final int port) throws IOException {
		super();
		this.server = new ServerSocket(port, 0, address);
		this.run = true;
	}

	public InetAddress getAddress() {
		return this.server.getInetAddress();
	}

	public int getPort() {
		return this.server.getLocalPort();
	}

	public ServerSocket getServer() {
		return this.server;
	}

	public boolean isRun() {
		return this.run;
	}

	public void setRun(final boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		String address = this.server.getInetAddress().getHostAddress();
		int port = this.server.getLocalPort();

		while (this.run) {
			try {
				this.getLog().info("Wait for connections on " + address + ":" + port);
				this.initialHandle(this.server.accept());
			} catch (Exception e) {
				e.printStackTrace();
				this.getLog().error(e.getMessage(), e);
			}
		}
	}

	protected Log getLog() {
		return SysLogger.getLog();
	}

	protected void initialHandle(final Socket s) throws Exception {
		InetSocketAddress isa = ((InetSocketAddress) s.getRemoteSocketAddress());
		String address = isa.getAddress().getHostAddress();
		int port = isa.getPort();

		this.getLog().info("Connection accepted from host " + address + ":" + port);
		this.handle(s);
	}

	protected abstract void handle(Socket s) throws Exception;

}
