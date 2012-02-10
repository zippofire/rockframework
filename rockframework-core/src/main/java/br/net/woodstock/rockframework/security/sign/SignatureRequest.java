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
package br.net.woodstock.rockframework.security.sign;

import java.io.Serializable;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.impl.MemoryStore;

public class SignatureRequest implements Serializable {

	private static final long	serialVersionUID	= -4388076526792546789L;

	private static final String	DEFAULT_ALIAS_NAME	= "unnammed";

	private Alias[]				aliases;

	private Store				store;

	public SignatureRequest(final Alias[] aliases, final Store store) {
		super();
		this.aliases = aliases;
		this.store = store;
	}

	public SignatureRequest(final PrivateKeyHolder privateKeyHolder) {
		super();
		Alias alias = new Alias(SignatureRequest.DEFAULT_ALIAS_NAME);
		this.store = new MemoryStore();
		this.aliases = new Alias[] { alias };
		this.store.add(new PrivateKeyEntry(alias, privateKeyHolder.getPrivateKey(), privateKeyHolder.getChain()));
	}

	public Alias[] getAliases() {
		return this.aliases;
	}

	public Store getStore() {
		return this.store;
	}

}
