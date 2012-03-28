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
import java.util.Properties;

public class DefaultBouncyCastleTimeStampConfig implements BouncyCastleTimeStampConfig {

	public static final String	STORE_TYPE_PROPERTY		= "store.type";

	public static final String	STORE_PASSWORD_PROPERTY	= "store.password";

	public static final String	STORE_RESOURCE_PROPERTY	= "store.resource";

	public static final String	KEY_ALIAS_PROPERTY		= "key.alias";

	public static final String	KEY_PASSWORD_PROPERTY	= "key.password";

	private String				storeType;

	private String				storePassword;

	private String				storeResource;

	private String				keyAlias;

	private String				keyPassword;

	public DefaultBouncyCastleTimeStampConfig(final String propertiesFile) throws IOException {
		super();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
		Properties properties = new Properties();
		properties.load(inputStream);

		this.storeType = properties.getProperty(DefaultBouncyCastleTimeStampConfig.STORE_TYPE_PROPERTY);
		this.storePassword = properties.getProperty(DefaultBouncyCastleTimeStampConfig.STORE_PASSWORD_PROPERTY);
		this.storeResource = properties.getProperty(DefaultBouncyCastleTimeStampConfig.STORE_RESOURCE_PROPERTY);
		this.keyAlias = properties.getProperty(DefaultBouncyCastleTimeStampConfig.KEY_ALIAS_PROPERTY);
		this.keyPassword = properties.getProperty(DefaultBouncyCastleTimeStampConfig.KEY_PASSWORD_PROPERTY);
	}

	@Override
	public String getStoreType() {
		return this.storeType;
	}

	@Override
	public String getStorePassword() {
		return this.storePassword;
	}

	@Override
	public String getStoreResource() {
		return this.storeResource;
	}

	@Override
	public String getKeyAlias() {
		return this.keyAlias;
	}

	@Override
	public String getKeyPassword() {
		return this.keyPassword;
	}

}
