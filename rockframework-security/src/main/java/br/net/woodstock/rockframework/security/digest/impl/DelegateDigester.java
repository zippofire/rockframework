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

public abstract class DelegateDigester implements Digester {

	private Digester	digester;

	public DelegateDigester(final Digester digester) {
		super();
		Assert.notNull(digester, "digester");
		this.digester = digester;
	}

	@Override
	public byte[] digest(final byte[] data) {
		return this.digester.digest(data);
	}
}
