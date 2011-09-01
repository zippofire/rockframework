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
package br.net.woodstock.rockframework.security.digest.impl;

import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.util.Assert;

public class AsStringDigester extends DelegateDigester {

	public AsStringDigester(final Digester digester) {
		super(digester);
	}

	public String digestAsString(final byte[] data) {
		byte[] digest = super.digest(data);
		return new String(digest);
	}

	public String digestAsString(final String str) {
		Assert.notEmpty(str, "str");
		
		byte[] data = str.getBytes();
		byte[] digest = super.digest(data);
		return new String(digest);
	}
}
