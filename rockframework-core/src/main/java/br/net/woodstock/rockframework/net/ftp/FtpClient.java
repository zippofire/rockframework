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
package br.net.woodstock.rockframework.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.util.Assert;

public class FtpClient implements Serializable {

	private static final long	serialVersionUID	= -4041833956040868201L;

	private static final int	ERROR_CODE			= 421;

	private String				host;

	private String				username;

	private String				password;

	private transient FTPClient	client;

	public FtpClient(final String host, final String username, final String password) {
		super();
		Assert.notEmpty(host, "host");
		Assert.notEmpty(username, "username");
		Assert.notEmpty(password, "password");

		this.host = host;
		this.username = username;
		this.password = password;
		this.client = new FTPClient();
	}

	// Aux
	private void checkConnected() {
		if (this.client.isConnected()) {
			throw new IllegalStateException("Client is not connected");
		}
	}

	//
	public void active() {
		this.client.enterLocalActiveMode();
	}

	public void passive() {
		this.client.enterLocalPassiveMode();
	}

	public void ascii() {
		try {
			this.client.setFileType(FTP.ASCII_FILE_TYPE);
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public void binary() {
		try {
			this.client.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean cdup() {
		try {
			if (this.client.isConnected()) {
				return this.client.cdup() != FtpClient.ERROR_CODE;
			}
			return false;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean cd(final String path) {
		try {
			this.checkConnected();
			return this.client.cwd(path) != FtpClient.ERROR_CODE;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public void connect() {
		try {
			this.disconnect();
			this.client.connect(this.host);
			if (!this.client.login(this.username, this.password)) {
				throw new FtpException(this.client.getReplyString());
			}
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public void disconnect() {
		try {
			if (this.client.isConnected()) {
				this.client.disconnect();
			}
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public File get(final String file) {
		return this.get(file, file);
	}

	public File get(final String src, final String dst) {
		try {
			this.checkConnected();

			File f = new File(dst);
			FileOutputStream output = new FileOutputStream(f);
			this.client.retrieveFile(src, output);
			output.close();
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
			return f;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public String getMessage() {
		this.checkConnected();

		return this.client.getReplyString();
	}

	public String getServerAddress() {
		this.checkConnected();

		return this.client.getRemoteAddress().getHostAddress();
	}

	public String getServerName() {
		this.checkConnected();

		return this.client.getRemoteAddress().getHostName();
	}

	public int getServerPort() {
		this.checkConnected();

		return this.client.getRemotePort();
	}

	public Collection<String> ls() {
		try {
			this.checkConnected();

			Collection<String> l = new LinkedHashSet<String>();
			FTPFile[] ls = this.client.listFiles();
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
			for (FTPFile f : ls) {
				l.add(f.getName());
			}
			return l;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean put(final String name) {
		try {
			return this.put(name, new FileInputStream(name));
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean put(final File f) {
		try {
			return this.put(f.getName(), new FileInputStream(f));
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean put(final String name, final InputStream input) {
		try {
			this.checkConnected();

			boolean put = this.client.storeFile(name, input);
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
			return put;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public String pwd() {
		try {
			this.checkConnected();

			String pwd = this.client.printWorkingDirectory();
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
			return pwd;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

	public boolean rm(final String name) {
		try {
			this.checkConnected();

			boolean rm = this.client.deleteFile(name);
			CoreLog.getInstance().getLog().debug(this.client.getReplyString().trim());
			return rm;
		} catch (IOException e) {
			throw new FtpException(e);
		}
	}

}
