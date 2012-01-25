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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.CollectionUtils;

public abstract class MapStore implements Store {

	private Map<String, Certificate>	certificateMap;

	private Map<String, PrivateKey>		privateKeyMap;

	private Map<String, PublicKey>		publicKeyMap;

	public MapStore() {
		super();
		this.certificateMap = new HashMap<String, Certificate>();
		this.privateKeyMap = new HashMap<String, PrivateKey>();
		this.publicKeyMap = new HashMap<String, PublicKey>();
	}

	// Aux
	protected Map<String, Certificate> getCertificateMap() {
		return this.certificateMap;
	}

	protected Map<String, PrivateKey> getPrivateKeyMap() {
		return this.privateKeyMap;
	}

	protected Map<String, PublicKey> getPublicKeyMap() {
		return this.publicKeyMap;
	}

	@Override
	public StoreEntry[] aliases() {
		Collection<StoreEntry> aliases = new ArrayList<StoreEntry>();
		for (Entry<String, Certificate> certificate : this.certificateMap.entrySet()) {
			aliases.add(new StoreEntry(certificate.getKey(), null, certificate.getValue(), StoreEntryType.CERTIFICATE));
		}
		for (Entry<String, PrivateKey> privateKey : this.privateKeyMap.entrySet()) {
			aliases.add(new StoreEntry(privateKey.getKey(), null, privateKey.getValue(), StoreEntryType.PRIVATE_KEY));
		}
		for (Entry<String, PublicKey> publicKey : this.publicKeyMap.entrySet()) {
			aliases.add(new StoreEntry(publicKey.getKey(), null, publicKey.getValue(), StoreEntryType.PUBLIC_KEY));
		}
		return CollectionUtils.toArray(aliases, StoreEntry.class);
	}

	@Override
	public StoreEntry get(final StoreEntry entry) {
		Assert.notNull(entry, "entry");
		Assert.notNull(entry.getAlias(), "entry.alias");
		Assert.notNull(entry.getType(), "entry.type");

		Object value = null;

		switch (entry.getType()) {
			case CERTIFICATE:
				value = this.certificateMap.get(entry.getAlias());
				break;
			case PRIVATE_KEY:
				value = this.privateKeyMap.get(entry.getAlias());
				break;
			case PUBLIC_KEY:
				value = this.publicKeyMap.get(entry.getAlias());
				break;
			default:
				break;
		}

		if (value != null) {
			return new StoreEntry(entry.getAlias(), null, value, entry.getType());
		}

		return null;
	}

	@Override
	public boolean add(final StoreEntry entry) {
		Assert.notNull(entry, "entry");
		Assert.notNull(entry.getAlias(), "entry.alias");
		Assert.notNull(entry.getType(), "entry.type");
		Assert.notNull(entry.getValue(), "entry.value");

		switch (entry.getType()) {
			case CERTIFICATE:
				this.certificateMap.put(entry.getAlias(), (Certificate) entry.getValue());
				break;
			case PRIVATE_KEY:
				this.privateKeyMap.put(entry.getAlias(), (PrivateKey) entry.getValue());
				break;
			case PUBLIC_KEY:
				this.publicKeyMap.put(entry.getAlias(), (PublicKey) entry.getValue());
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public boolean remove(final StoreEntry entry) {
		Assert.notNull(entry, "entry");
		Assert.notNull(entry.getAlias(), "entry.alias");
		Assert.notNull(entry.getType(), "entry.type");

		switch (entry.getType()) {
			case CERTIFICATE:
				this.certificateMap.remove(entry.getAlias());
				break;
			case PRIVATE_KEY:
				this.privateKeyMap.remove(entry.getAlias());
				break;
			case PUBLIC_KEY:
				this.publicKeyMap.remove(entry.getAlias());
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public KeyStore toKeyStore() {
		JCAStore jcaStore = new JCAStore(KeyStoreType.JKS);
		for (Entry<String, Certificate> certificate : this.certificateMap.entrySet()) {
			jcaStore.add(new StoreEntry(certificate.getKey(), null, certificate.getValue(), StoreEntryType.CERTIFICATE));
		}
		for (Entry<String, PrivateKey> privateKey : this.privateKeyMap.entrySet()) {
			jcaStore.add(new StoreEntry(privateKey.getKey(), null, privateKey.getValue(), StoreEntryType.PRIVATE_KEY));
		}
		for (Entry<String, PublicKey> publicKey : this.publicKeyMap.entrySet()) {
			jcaStore.add(new StoreEntry(publicKey.getKey(), null, publicKey.getValue(), StoreEntryType.PUBLIC_KEY));
		}
		return jcaStore.toKeyStore();
	}

}
