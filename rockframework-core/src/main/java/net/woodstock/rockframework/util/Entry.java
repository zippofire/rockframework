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

	private K					key;

	private V					value;

	public Entry() {
		super();
	}

	public Entry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public K setKey(K key) {
		this.key = key;
		return this.key;
	}

	public V getValue() {
		return this.value;
	}

	public V setValue(V value) {
		this.value = value;
		return this.value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Entry)) {
			return false;
		}
		Entry<?, ?> e = (Entry<?, ?>) o;
		return (this.key == null ? e.getKey() == null : this.key.equals(e.getKey())) && (this.value == null ? e.getValue() == null : this.value.equals(e.getValue()));
	}

	@Override
	public int hashCode() {
		return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
	}

}
