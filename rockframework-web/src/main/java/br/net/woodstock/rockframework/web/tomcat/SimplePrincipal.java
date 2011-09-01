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
package br.net.woodstock.rockframework.web.tomcat;

import java.util.List;


import org.apache.catalina.realm.GenericPrincipal;

import br.net.woodstock.rockframework.utils.ArrayUtils;
import br.net.woodstock.rockframework.web.security.WebPrincipal;

public class SimplePrincipal extends GenericPrincipal implements WebPrincipal {

	public SimplePrincipal(final String name, final String password, final String role) {
		super(null, name, password, ArrayUtils.toList(new String[] { role }));
	}

	public SimplePrincipal(final String name, final String password, final String[] roles) {
		super(null, name, password, ArrayUtils.toList(roles));
	}

	public SimplePrincipal(final String name, final String password, final List<String> roles) {
		super(null, name, password, roles);
	}

}
