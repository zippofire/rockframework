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
package br.net.woodstock.rockframework.net.ldap;

import javax.naming.spi.InitialContextFactory;

import br.net.woodstock.rockframework.config.CoreLog;

public abstract class LDAPContextFactoryManager {

	private static final String					SUN_FACTORY	= "com.sun.jndi.ldap.LdapCtxFactory";

	private static LDAPContextFactoryManager	instance	= LDAPContextFactoryManager.getAvailableManager();

	public abstract String getName();

	public abstract Class<? extends InitialContextFactory> getType();

	public static LDAPContextFactoryManager getInstance() {
		return LDAPContextFactoryManager.instance;
	}

	private static LDAPContextFactoryManager getAvailableManager() {
		try {
			Class.forName(LDAPContextFactoryManager.SUN_FACTORY);
			LDAPContextFactoryManager factory = new SunLDAPContextFactoryManager();
			CoreLog.getInstance().getLogger().info("Using Sun LDAPContextFactory");
			return factory;
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException("No LDAPContextFactory found");
		}
	}

}
