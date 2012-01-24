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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
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

	// Store
	@Override
	public Certificate getCertificate(final String alias) {
		return this.certificateMap.get(alias);
	}

	@Override
	public PrivateKey getPrivateKey(final String alias) {
		return this.privateKeyMap.get(alias);
	}

	@Override
	public PublicKey getPublicKeys(final String alias) {
		return this.publicKeyMap.get(alias);
	}

	@Override
	public Certificate[] getCertificates() {
		return CollectionUtils.toArray(this.certificateMap.values(), Certificate.class);
	}

	@Override
	public PrivateKey[] getPrivateKeys() {
		return CollectionUtils.toArray(this.privateKeyMap.values(), PrivateKey.class);
	}

	@Override
	public PublicKey[] getPublicKeys() {
		return CollectionUtils.toArray(this.publicKeyMap.values(), PublicKey.class);
	}

	@Override
	public boolean addCertificate(final Certificate certificate, final String alias) {
		this.certificateMap.put(alias, certificate);
		return true;
	}

	@Override
	public boolean addPrivateKey(final PrivateKey privateKey, final String alias) {
		this.privateKeyMap.put(alias, privateKey);
		return true;
	}

	@Override
	public boolean addPublicKey(final PublicKey publicKey, final String alias) {
		this.publicKeyMap.put(alias, publicKey);
		return true;
	}

	@Override
	public boolean removeCertificate(final String alias) {
		this.certificateMap.remove(alias);
		return true;
	}

	@Override
	public boolean removePrivateKey(final String alias) {
		this.privateKeyMap.remove(alias);
		return true;
	}

	@Override
	public boolean removePublicKey(final String alias) {
		this.publicKeyMap.remove(alias);
		return true;
	}

	@Override
	public KeyStore toKeyStore(final char[] storePasswd) {
		JCAStore jcaStore = new JCAStore(KeyStoreType.JKS, storePasswd);
		for (Entry<String, Certificate> certificates : this.certificateMap.entrySet()) {
			jcaStore.addCertificate(certificates.getValue(), certificates.getKey());
		}
		for (Entry<String, PrivateKey> privateKey : this.privateKeyMap.entrySet()) {
			jcaStore.addPrivateKey(privateKey.getValue(), privateKey.getKey());
		}
		for (Entry<String, PrivateKey> privateKey : this.privateKeyMap.entrySet()) {
			jcaStore.addPrivateKey(privateKey.getValue(), privateKey.getKey());
		}
		return jcaStore.toKeyStore(storePasswd);
	}

}
