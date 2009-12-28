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
package net.woodstock.rockframework.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.util.Collection;
import java.util.LinkedHashSet;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpClient implements Serializable {

	private static final long	serialVersionUID	= -4041833956040868201L;

	private static final int	ERROR_CODE			= 421;

	private String				host;

	private String				username;

	private String				password;

	private transient FTPClient	client;

	public FtpClient(final String host, final String username, final String password) throws IOException {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
		this.client = new FTPClient();
		this.connect();
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	public void active() {
		this.client.enterLocalActiveMode();
	}

	public void passive() {
		this.client.enterLocalPassiveMode();
	}

	public void ascii() throws IOException {
		this.client.setFileType(FTP.ASCII_FILE_TYPE);
	}

	public void binary() throws IOException {
		this.client.setFileType(FTP.BINARY_FILE_TYPE);
	}

	public boolean cdup() throws IOException {
		if (this.client.isConnected()) {
			return this.client.cdup() != FtpClient.ERROR_CODE;
		}
		return false;
	}

	public boolean cd(final String path) throws IOException {
		if (this.client.isConnected()) {
			return this.client.cwd(path) != FtpClient.ERROR_CODE;
		}
		return false;
	}

	public void connect() throws IOException {
		this.disconnect();
		this.client.connect(this.host);
		if (!this.client.login(this.username, this.password)) {
			throw new SocketException(this.client.getReplyString());
		}
		this.getLogger().debug(this.client.getReplyString().trim());
	}

	public void disconnect() throws IOException {
		if (this.client.isConnected()) {
			this.client.disconnect();
		}
	}

	public File get(final String file) throws IOException {
		return this.get(file, file);
	}

	public File get(final String src, final String dst) throws IOException {
		if (this.client.isConnected()) {
			File f = new File(dst);
			FileOutputStream output = new FileOutputStream(f);
			this.client.retrieveFile(src, output);
			output.close();
			this.getLogger().debug(this.client.getReplyString().trim());
			return f;
		}
		return null;
	}

	public String getMessage() {
		return this.client.getReplyString();
	}

	public String getServerAddress() {
		return this.client.getRemoteAddress().getHostAddress();
	}

	public String getServerName() {
		return this.client.getRemoteAddress().getHostName();
	}

	public int getServerPort() {
		return this.client.getRemotePort();
	}

	public Collection<String> ls() throws IOException {
		Collection<String> l = new LinkedHashSet<String>();
		if (this.client.isConnected()) {
			FTPFile[] ls = this.client.listFiles();
			this.getLogger().debug(this.client.getReplyString().trim());
			for (FTPFile f : ls) {
				l.add(f.getName());
			}
		}
		return l;
	}

	public boolean put(final String name) throws IOException {
		if (this.client.isConnected()) {
			boolean put = this.client.storeFile(name, new FileInputStream(name));
			this.getLogger().debug(this.client.getReplyString().trim());
			return put;
		}
		return false;
	}

	public boolean put(final File f) throws IOException {
		if (this.client.isConnected()) {
			boolean put = this.client.storeFile(f.getName(), new FileInputStream(f));
			this.getLogger().debug(this.client.getReplyString().trim());
			return put;
		}
		return false;
	}

	public boolean put(final String name, final InputStream input) throws IOException {
		if (this.client.isConnected()) {
			boolean put = this.client.storeFile(name, input);
			this.getLogger().debug(this.client.getReplyString().trim());
			return put;
		}
		return false;
	}

	public String pwd() throws IOException {
		if (this.client.isConnected()) {
			String pwd = this.client.printWorkingDirectory();
			this.getLogger().debug(this.client.getReplyString().trim());
			return pwd;
		}
		return null;
	}

	public boolean rm(final String name) throws IOException {
		if (this.client.isConnected()) {
			boolean rm = this.client.deleteFile(name);
			this.getLogger().debug(this.client.getReplyString().trim());
			return rm;
		}
		return false;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
