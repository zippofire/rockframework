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
package br.net.woodstock.rockframework.security.store.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.util.Assert;

public class ImmutableStore implements Store {

	private Store	store;

	public ImmutableStore(final Store store) {
		super();
		Assert.notNull(store, "store");
		this.store = store;
	}

	@Override
	public Alias[] aliases() {
		return this.store.aliases();
	}

	@Override
	public StoreEntry[] getChain(final Alias alias) {
		return this.store.getChain(alias);
	}

	@Override
	public StoreEntry get(final Alias alias, final StoreEntryType type) {
		return this.store.get(alias, type);
	}

	@Override
	public boolean add(final StoreEntry entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(final Alias alias) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void read(final InputStream inputStream, final String password) throws IOException {
		this.store.read(inputStream, password);
	}

	@Override
	public void write(final OutputStream outputStream, final String password) throws IOException {
		this.store.write(outputStream, password);
	}

	@Override
	public KeyStore toKeyStore() {
		return this.store.toKeyStore();
	}

}
