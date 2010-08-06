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
package net.woodstock.rockframework.util;

import java.io.Serializable;
import java.util.Map;

public class Entry<K, V> implements Map.Entry<K, V>, Serializable {

	private static final long	serialVersionUID	= -3491714904009797034L;

	private static final String	VALUE_SEPARATOR		= "=";

	private static final String	BEGIN_ENTRY			= "[";

	private static final String	END_ENTRY			= "]";

	private K					key;

	private V					value;

	public Entry() {
		super();
	}

	public Entry(final K key, final V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return this.key;
	}

	public K setKey(final K key) {
		this.key = key;
		return this.key;
	}

	@Override
	public V getValue() {
		return this.value;
	}

	@Override
	public V setValue(final V value) {
		this.value = value;
		return this.value;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Entry)) {
			return false;
		}
		Entry<?, ?> e = (Entry<?, ?>) o;
		if (((this.key == null) && (e.getKey() != null)) || ((this.key != null) && (e.getKey() == null))) {
			return false;
		}
		if (((this.value == null) && (e.getValue() != null)) || ((this.value != null) && (e.getValue() == null))) {
			return false;
		}
		if (!this.key.equals(e.getKey())) {
			return false;
		}
		if (!this.value.equals(e.getValue())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		if ((this.key == null) && (this.value == null)) {
			return super.hashCode();
		}
		return this.key.hashCode() ^ this.value.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append(Entry.BEGIN_ENTRY);
		builder.append(this.key);
		builder.append(Entry.VALUE_SEPARATOR);
		builder.append(this.value);
		builder.append(Entry.END_ENTRY);
		return builder.toString();
	}

}
