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

import java.security.KeyStore;

import br.net.woodstock.rockframework.security.store.KeyStoreBuilder;
import br.net.woodstock.rockframework.security.store.KeyStoreException;
import br.net.woodstock.rockframework.security.store.KeyStoreType;

public class KeyStoreBuilderImpl implements KeyStoreBuilder {

	private KeyStoreType	keyStoreType;

	public KeyStoreBuilderImpl(final KeyStoreType keyStoreType) {
		super();
		this.keyStoreType = keyStoreType;
	}

	@Override
	public KeyStore build() {
		try {
			KeyStore keyStore = KeyStore.getInstance(this.keyStoreType.getType());
			keyStore.load(null, null);
			return keyStore;
		} catch (Exception e) {
			throw new KeyStoreException(e);
		}
	}

}
