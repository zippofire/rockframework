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
package net.woodstock.rockframework.security.digest.impl;

import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.utils.Base64Utils;

public class Base64Digester implements Digester {

	private Digester	digester;

	public Base64Digester(final Digester digester) {
		super();
		this.digester = digester;
	}

	@Override
	public String digest(final String data) {
		String digest = this.digester.digest(data);
		digest = Base64Utils.toBase64(digest);
		return digest;
	}

}
