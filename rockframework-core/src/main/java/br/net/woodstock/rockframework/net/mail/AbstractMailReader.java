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
package br.net.woodstock.rockframework.net.mail;

public abstract class AbstractMailReader implements MailReader, MailReaderConfig {

	private String		server;

	private int			port;

	private String		user;

	private String		password;

	private StoreType	type;

	private boolean		debug;

	public AbstractMailReader(final String server, final int port, final String user, final String password, final StoreType type) {
		super();
		this.server = server;
		this.port = port;
		this.user = user;
		this.password = password;
		this.type = type;
	}

	@Override
	public Mail[] read(final String folder) {
		return MailHelper.read(folder, this);
	}

	// Get/Set
	@Override
	public String getServer() {
		return this.server;
	}

	public void setServer(final String server) {
		this.server = server;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	@Override
	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public StoreType getType() {
		return this.type;
	}

	public void setType(final StoreType type) {
		this.type = type;
	}

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

}
