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
package br.net.woodstock.rockframework.web.ws.wss4j;

import java.io.Serializable;
import java.util.Properties;

public class WSS4JConfig implements Serializable {

	private static final long	serialVersionUID	= -1426531325574513971L;

	private WSS4JCredential		client;

	private WSS4JCredential		server;

	private Properties			properties;

	public WSS4JConfig(final WSS4JCredential client, final WSS4JCredential server) {
		this(client, server, null);
	}

	public WSS4JConfig(final WSS4JCredential client, final WSS4JCredential server, final Properties properties) {
		super();
		this.client = client;
		this.server = server;
		this.properties = properties;
	}

	public WSS4JCredential getClient() {
		return this.client;
	}

	public WSS4JCredential getServer() {
		return this.server;
	}

	public Properties getProperties() {
		return this.properties;
	}

}
