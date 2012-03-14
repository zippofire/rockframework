/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Private License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Private License for more details.
 * 
 * You should have received a copy of the GNU General Private License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.store;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.security.Alias;

public class PrivateKeyEntry extends StoreEntry {

	private static final long	serialVersionUID	= 7861612701609530731L;

	private Certificate[]		chain;

	public PrivateKeyEntry(final Alias alias, final PrivateKey value) {
		super(alias, value);
	}

	public PrivateKeyEntry(final Alias alias, final PrivateKey value, final Certificate[] chain) {
		super(alias, value);
		this.chain = chain;
	}

	@Override
	public PrivateKey getValue() {
		return (PrivateKey) super.getValue();
	}

	public Certificate[] getChain() {
		return this.chain;
	}

	@Override
	public StoreEntryType getType() {
		return StoreEntryType.PRIVATE_KEY;
	}

}
