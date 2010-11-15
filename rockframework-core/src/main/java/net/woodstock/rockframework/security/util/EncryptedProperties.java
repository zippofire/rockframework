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
package net.woodstock.rockframework.security.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.impl.AsStringCrypter;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.ConditionUtils;

public class EncryptedProperties extends Properties {

	private static final long	serialVersionUID	= 982155416609776739L;

	private AsStringCrypter		crypter;

	private boolean				isLoading;

	public EncryptedProperties(final Crypter crypter) {
		super();
		Assert.notNull(crypter, "crypter");
		this.crypter = new AsStringCrypter(crypter);
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		Set<Entry<Object, Object>> set = new LinkedHashSet<Entry<Object, Object>>();
		for (Entry<Object, Object> entry : super.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			String str = null;
			if (value != null) {
				str = this.crypter.encryptAsString(value.toString());
			}
			net.woodstock.rockframework.util.Entry<Object, Object> e = new net.woodstock.rockframework.util.Entry<Object, Object>(key, str);
			set.add(e);
		}
		return set;
	}

	@Override
	public String getProperty(final String key) {
		String property = super.getProperty(key);
		if (ConditionUtils.isNotEmpty(property)) {
			return this.crypter.decryptAsString(property);
		}
		return null;
	}

	@Override
	public String getProperty(final String key, final String defaultValue) {
		String property = super.getProperty(key);
		if (ConditionUtils.isNotEmpty(property)) {
			return this.crypter.decryptAsString(property);
		}
		return defaultValue;
	}

	@Override
	public synchronized void load(final InputStream inStream) throws IOException {
		this.isLoading = true;
		super.load(inStream);
		this.isLoading = false;
	}

	@Override
	public synchronized void load(final Reader reader) throws IOException {
		this.isLoading = true;
		super.load(reader);
		this.isLoading = false;
	}

	@Override
	public synchronized void loadFromXML(final InputStream in) throws IOException {
		this.isLoading = true;
		super.loadFromXML(in);
		this.isLoading = false;
	}

	@Override
	public synchronized Object put(final Object key, final Object value) {
		Object obj = null;
		if (!this.isLoading) {
			if (value != null) {
				obj = this.crypter.encryptAsString(value.toString());
			}
		} else {
			obj = value;
		}
		return super.put(key, obj);

	}

	@Override
	public synchronized void putAll(final Map<? extends Object, ? extends Object> t) {
		if (t != null) {
			for (Entry<? extends Object, ? extends Object> entry : t.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();
				String str = null;
				if (value != null) {
					str = this.crypter.encryptAsString(value.toString());
				}
				super.put(key, str);
			}
		}
	}

	@Override
	public Collection<Object> values() {
		List<Object> values = new LinkedList<Object>();
		for (Object o : super.values()) {
			if (o != null) {
				String str = o.toString();
				if (ConditionUtils.isNotEmpty(str)) {
					str = this.crypter.decryptAsString(str);
				}
				values.add(str);
			} else {
				values.add(null);
			}
		}
		return values;
	}

}
