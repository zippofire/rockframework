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
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.net.woodstock.rockframework.security.store.KeyStoreType;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreException;
import br.net.woodstock.rockframework.utils.CollectionUtils;

public class JCAStore implements Store {

	private KeyStore	keyStore;

	private char[]		storePasswd;

	public JCAStore(final KeyStore keyStore, final char[] storePasswd) {
		super();
		this.keyStore = keyStore;
		this.storePasswd = storePasswd;
	}

	public JCAStore(final KeyStoreType keyStoreType, final char[] storePasswd) {
		super();
		try {
			this.keyStore = KeyStore.getInstance(keyStoreType.getType());
			this.storePasswd = storePasswd;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public Certificate getCertificate(final String alias) {
		try {
			return this.keyStore.getCertificate(alias);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public PrivateKey getPrivateKey(final String alias) {
		try {
			return (PrivateKey) this.keyStore.getKey(alias, null);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public PublicKey getPublicKeys(final String alias) {
		try {
			return (PublicKey) this.keyStore.getKey(alias, null);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public Certificate[] getCertificates() {
		try {
			Enumeration<String> aliases = this.keyStore.aliases();
			List<Certificate> list = new ArrayList<Certificate>();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				if (this.keyStore.isCertificateEntry(alias)) {
					Certificate c = this.keyStore.getCertificate(alias);
					list.add(c);
				}
			}
			return CollectionUtils.toArray(list, Certificate.class);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public PrivateKey[] getPrivateKeys() {
		try {
			Enumeration<String> aliases = this.keyStore.aliases();
			List<PrivateKey> list = new ArrayList<PrivateKey>();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				if (this.keyStore.isKeyEntry(alias)) {
					Key k = this.keyStore.getKey(alias, null);
					if (k instanceof PrivateKey) {
						list.add((PrivateKey) k);
					}
				}
			}
			return CollectionUtils.toArray(list, PrivateKey.class);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public PublicKey[] getPublicKeys() {
		try {
			Enumeration<String> aliases = this.keyStore.aliases();
			List<PublicKey> list = new ArrayList<PublicKey>();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				if (this.keyStore.isKeyEntry(alias)) {
					Key k = this.keyStore.getKey(alias, null);
					if (k instanceof PublicKey) {
						list.add((PublicKey) k);
					}
				}
			}
			return CollectionUtils.toArray(list, PublicKey.class);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean addCertificate(final Certificate certificate, final String alias) {
		try {
			this.keyStore.setCertificateEntry(alias, certificate);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean addPrivateKey(final PrivateKey privateKey, final String alias) {
		try {
			this.keyStore.setKeyEntry(alias, privateKey, null, null);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean addPublicKey(final PublicKey publicKey, final String alias) {
		try {
			this.keyStore.setKeyEntry(alias, publicKey, null, null);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean removeCertificate(final String alias) {
		try {
			this.keyStore.deleteEntry(alias);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean removePrivateKey(final String alias) {
		try {
			this.keyStore.deleteEntry(alias);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean removePublicKey(final String alias) {
		try {
			this.keyStore.deleteEntry(alias);
			return true;
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public void read(final InputStream inputStream) throws IOException {
		try {
			this.keyStore.load(inputStream, this.storePasswd);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public void write(final OutputStream outputStream) throws IOException {
		try {
			this.keyStore.store(outputStream, this.storePasswd);
		} catch (GeneralSecurityException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public KeyStore toKeyStore(final char[] storePasswd) {
		return this.keyStore;
	}

}
