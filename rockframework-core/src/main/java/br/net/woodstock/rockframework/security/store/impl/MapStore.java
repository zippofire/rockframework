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

import javax.crypto.SecretKey;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.store.CertificateEntry;
import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.PublicKeyEntry;
import br.net.woodstock.rockframework.security.store.SecretKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.CollectionUtils;

public abstract class MapStore implements Store {

	private Map<String, Certificate>		certificateMap;

	private Map<String, PrivateKeyHolder>	privateKeyMap;

	private Map<String, PublicKey>			publicKeyMap;

	private Map<String, SecretKey>			secretKeyMap;

	public MapStore() {
		super();
		this.certificateMap = new HashMap<String, Certificate>();
		this.privateKeyMap = new HashMap<String, PrivateKeyHolder>();
		this.publicKeyMap = new HashMap<String, PublicKey>();
		this.secretKeyMap = new HashMap<String, SecretKey>();
	}

	// Aux
	protected Map<String, Certificate> getCertificateMap() {
		return this.certificateMap;
	}

	protected Map<String, PrivateKeyHolder> getPrivateKeyMap() {
		return this.privateKeyMap;
	}

	protected Map<String, PublicKey> getPublicKeyMap() {
		return this.publicKeyMap;
	}

	protected Map<String, SecretKey> getSecretKeyMap() {
		return this.secretKeyMap;
	}

	@Override
	public Alias[] aliases() {
		Collection<Alias> aliases = new ArrayList<Alias>();
		for (Entry<String, Certificate> entry : this.certificateMap.entrySet()) {
			aliases.add(new Alias(entry.getKey()));
		}
		for (Entry<String, PrivateKeyHolder> entry : this.privateKeyMap.entrySet()) {
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

		StoreEntry entry = null;

		switch (type) {
			case CERTIFICATE:
				Certificate certificate = this.certificateMap.get(alias.getName());
				if (certificate != null) {
					entry = new CertificateEntry(alias, certificate);
				}
				break;
			case PRIVATE_KEY:
				PrivateKeyHolder holder = this.privateKeyMap.get(alias.getName());
				if (holder != null) {
					entry = new PrivateKeyEntry(alias, holder.getPrivateKey(), holder.getChain());
				}
				break;
			case PUBLIC_KEY:
				PublicKey publicKey = this.publicKeyMap.get(alias.getName());
				if (publicKey != null) {
					entry = new PublicKeyEntry(alias, publicKey);
				}
				break;
			case SECRET_KEY:
				SecretKey secretKey = this.secretKeyMap.get(alias.getName());
				if (secretKey != null) {
					entry = new SecretKeyEntry(alias, secretKey);
				}
				break;
			default:
				break;
		}
		return entry;
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

		Alias alias = entry.getAlias();
		String name = alias.getName();
		Object value = entry.getValue();

		switch (entry.getType()) {
			case CERTIFICATE:
				this.certificateMap.put(name, (Certificate) value);
				break;
			case PRIVATE_KEY:
				this.privateKeyMap.put(name, new PrivateKeyHolder((PrivateKey) value, ((PrivateKeyEntry) entry).getChain()));
				break;
			case PUBLIC_KEY:
				this.publicKeyMap.put(name, (PublicKey) value);
				break;
			case SECRET_KEY:
				this.secretKeyMap.put(name, (SecretKey) value);
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
			jcaStore.add(new CertificateEntry(new Alias(entry.getKey()), entry.getValue()));
		}
		for (Entry<String, PrivateKeyHolder> entry : this.privateKeyMap.entrySet()) {
			jcaStore.add(new PrivateKeyEntry(new Alias(entry.getKey()), entry.getValue().getPrivateKey(), entry.getValue().getChain()));
		}
		for (Entry<String, PublicKey> entry : this.publicKeyMap.entrySet()) {
			jcaStore.add(new PublicKeyEntry(new Alias(entry.getKey()), entry.getValue()));
		}
		for (Entry<String, SecretKey> entry : this.secretKeyMap.entrySet()) {
			jcaStore.add(new SecretKeyEntry(new Alias(entry.getKey()), entry.getValue()));
		}
		return jcaStore.toKeyStore();
	}

}
