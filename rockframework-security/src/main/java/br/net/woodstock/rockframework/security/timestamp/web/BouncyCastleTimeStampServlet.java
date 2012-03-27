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
 * GNU General Public License for more details.es
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.timestamp.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PasswordAlias;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.impl.JCAStore;
import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;
import br.net.woodstock.rockframework.security.timestamp.impl.BouncyCastleTimeStampServer;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public class BouncyCastleTimeStampServlet extends AbstractTimeStampServlet {

	private static final long	serialVersionUID		= -4911494861058607111L;

	public static final String	DEFAULT_PROPERTIES_FILE	= "timestamp-servlet.properties";

	private TimeStampServer		timeStampServer;

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		try {
			BouncyCastleTimeStampProperties properties = this.getProperties();
			InputStream inputStream = ClassLoaderUtils.getResourceAsStream(properties.getStoreResource());

			Store store = new JCAStore(KeyStoreType.valueOf(properties.getStoreType()));
			store.read(inputStream, properties.getStorePassword());

			Alias alias = new PasswordAlias(properties.getKeyAlias(), properties.getKeyPassword());

			this.timeStampServer = new BouncyCastleTimeStampServer(store, alias);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected BouncyCastleTimeStampProperties getProperties() throws IOException {
		return new BouncyCastleTimeStampProperties(BouncyCastleTimeStampServlet.DEFAULT_PROPERTIES_FILE);
	}

	@Override
	protected TimeStampServer getTimeStampServer() {
		return this.timeStampServer;
	}

}
