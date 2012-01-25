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
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.store.StoreException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.CollectionUtils;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class JCAStore implements Store {

	private KeyStore	keyStore;

	public JCAStore(final KeyStore keyStore) {
		super();
		this.keyStore = keyStore;
	}

	public JCAStore(final KeyStoreType keyStoreType) {
		super();
		try {
			this.keyStore = KeyStore.getInstance(keyStoreType.getType());
			this.keyStore.load(null, null);
		} catch (Exception e) {
			throw new StoreException(e);
		}
	}

	@Override
	public StoreEntry[] aliases() {
		try {
			Enumeration<String> aliases = this.keyStore.aliases();
			List<StoreEntry> list = new ArrayList<StoreEntry>();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				StoreEntryType type = null;
				if (this.keyStore.isCertificateEntry(alias)) {
					type = StoreEntryType.CERTIFICATE;
				} else if (this.keyStore.isKeyEntry(alias)) {
					type = StoreEntryType.PRIVATE_KEY;
				}
				list.add(new StoreEntry(alias, null, null, type));
			}
			return CollectionUtils.toArray(list, StoreEntry.class);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public StoreEntry get(final StoreEntry entry) {
		try {
			Assert.notNull(entry, "entry");
			Assert.notNull(entry.getAlias(), "entry.alias");
			Assert.notNull(entry.getType(), "entry.type");

			Object value = null;

			switch (entry.getType()) {
				case CERTIFICATE:
					value = this.keyStore.getCertificate(entry.getAlias());
					break;
				case PRIVATE_KEY:
					PrivateKey privateKey = (PrivateKey) this.keyStore.getKey(entry.getAlias(), this.toCharArray(entry.getPassword()));
					value = privateKey;
					break;
				case PUBLIC_KEY:
					PublicKey publicKey = (PublicKey) this.keyStore.getKey(entry.getAlias(), this.toCharArray(entry.getPassword()));
					value = publicKey;
					break;
				default:
					break;
			}

			if (value != null) {
				return new StoreEntry(entry.getAlias(), null, value, entry.getType());
			}

			return null;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean add(final StoreEntry entry) {
		try {
			Assert.notNull(entry, "entry");
			Assert.notNull(entry.getAlias(), "entry.alias");
			Assert.notNull(entry.getType(), "entry.type");
			Assert.notNull(entry.getValue(), "entry.value");

			switch (entry.getType()) {
				case CERTIFICATE:
					this.keyStore.setCertificateEntry(entry.getAlias(), (Certificate) entry.getValue());
					break;
				case PRIVATE_KEY:
					this.keyStore.setKeyEntry(entry.getAlias(), (PrivateKey) entry.getValue(), this.toCharArray(entry.getPassword()), null);
					break;
				case PUBLIC_KEY:
					this.keyStore.setKeyEntry(entry.getAlias(), (PublicKey) entry.getValue(), this.toCharArray(entry.getPassword()), null);
					break;
				default:
					break;
			}

			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean remove(final StoreEntry entry) {
		try {
			Assert.notNull(entry, "entry");
			Assert.notNull(entry.getAlias(), "entry.alias");

			this.keyStore.deleteEntry(entry.getAlias());

			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public void read(final InputStream inputStream, final String password) throws IOException {
		try {
			this.keyStore.load(inputStream, this.toCharArray(password));
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public void write(final OutputStream outputStream, final String password) throws IOException {
		try {
			this.keyStore.store(outputStream, this.toCharArray(password));
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	private char[] toCharArray(final String s) {
		if (ConditionUtils.isNotEmpty(s)) {
			return s.toCharArray();
		}
		return null;
	}

	@Override
	public KeyStore toKeyStore() {
		return this.keyStore;
	}

}
