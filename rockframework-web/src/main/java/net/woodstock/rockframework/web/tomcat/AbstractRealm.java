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
package net.woodstock.rockframework.web.tomcat;

import java.security.Principal;

import org.apache.catalina.realm.RealmBase;

public abstract class AbstractRealm extends RealmBase {

	private static final String	NAME	= "CustomRealm";

	@Override
	public Principal authenticate(final String username, final String password) {
		return super.authenticate(username, password);
	}

	@Override
	protected String getName() {
		return AbstractRealm.NAME;
	}

	@Override
	protected String getPassword(final String username) {
		return null;
	}

	@Override
	protected Principal getPrincipal(final String username) {
		return null;
	}

}
