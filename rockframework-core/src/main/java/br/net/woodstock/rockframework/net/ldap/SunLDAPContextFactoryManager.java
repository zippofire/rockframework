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

import br.net.woodstock.rockframework.InitializationException;

@SuppressWarnings({ "unchecked", "rawtypes" })
class SunLDAPContextFactoryManager extends LDAPContextFactoryManager {

	private static final String							SUN_FACTORY_NAME	= "com.sun.jndi.ldap.LdapCtxFactory";

	private static final Class<InitialContextFactory>	SUN_FACTORY_TYPE;

	static {
		try {
			SUN_FACTORY_TYPE = (Class) Class.forName(SunLDAPContextFactoryManager.SUN_FACTORY_NAME);
		} catch (ClassNotFoundException e) {
			throw new InitializationException(e);
		}
	}

	@Override
	public String getName() {
		return SunLDAPContextFactoryManager.SUN_FACTORY_NAME;
	}

	@Override
	public Class<? extends InitialContextFactory> getType() {
		return SunLDAPContextFactoryManager.SUN_FACTORY_TYPE;
	}

}
