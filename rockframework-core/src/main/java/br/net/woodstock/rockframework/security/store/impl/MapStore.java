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

import br.net.woodstock.rockframework.security.Alias;
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
	public Alias[] aliases() {
		Collection<Alias> aliases = new ArrayList<Alias>();
		for (Entry<String, Certificate> entry : this.certificateMap.entrySet()) {
			aliases.add(new Alias(entry.getKey()));
		}
		for (Entry<String, PrivateKey> entry : this.privateKeyMap.entrySet()) {
			aliases.add(new Alias(entry.getKey()));
		}
		for (Entry<String, PublicKey> entry : this.publicKeyMap.entrySet()) {
			aliases.add(new Alias(entry.getKey()));
		}
		return CollectionUtils.toArray(aliases, Alias.class);
	}

	@Override
	public StoreEntry get(final Alias alias, final StoreEntryType type) {
		Assert.notNull(alias, "alias");
		Assert.notNull(type, "type");

		Object value = null;

		switch (type) {
			case CERTIFICATE:
				value = this.certificateMap.get(alias.getName());
				break;
			case PRIVATE_KEY:
				value = this.privateKeyMap.get(alias.getName());
				break;
			case PUBLIC_KEY:
				value = this.publicKeyMap.get(alias.getName());
				break;
			default:
				break;
		}

		if (value != null) {
			return new StoreEntry(alias, value, type);
		}

		return null;
	}

	@Override
	public StoreEntry[] getChain(final Alias alias) {
		StoreEntry entry = this.get(alias, StoreEntryType.CERTIFICATE);
		if (entry != null) {
			return new StoreEntry[] { entry };
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
				this.certificateMap.put(entry.getAlias().getName(), (Certificate) entry.getValue());
				break;
			case PRIVATE_KEY:
				this.privateKeyMap.put(entry.getAlias().getName(), (PrivateKey) entry.getValue());
				break;
			case PUBLIC_KEY:
				this.publicKeyMap.put(entry.getAlias().getName(), (PublicKey) entry.getValue());
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public boolean remove(final Alias alias) {
		Assert.notNull(alias, "alias");
		this.certificateMap.remove(alias.getName());
		this.privateKeyMap.remove(alias.getName());
		this.publicKeyMap.remove(alias.getName());
		return true;
	}

	@Override
	public KeyStore toKeyStore() {
		JCAStore jcaStore = new JCAStore(KeyStoreType.JKS);
		for (Entry<String, Certificate> entry : this.certificateMap.entrySet()) {
			jcaStore.add(new StoreEntry(new Alias(entry.getKey()), entry.getValue(), StoreEntryType.CERTIFICATE));
		}
		for (Entry<String, PrivateKey> entry : this.privateKeyMap.entrySet()) {
			jcaStore.add(new StoreEntry(new Alias(entry.getKey()), entry.getValue(), StoreEntryType.PRIVATE_KEY));
		}
		for (Entry<String, PublicKey> entry : this.publicKeyMap.entrySet()) {
			jcaStore.add(new StoreEntry(new Alias(entry.getKey()), entry.getValue(), StoreEntryType.PUBLIC_KEY));
		}
		return jcaStore.toKeyStore();
	}

}
