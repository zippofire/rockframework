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
package net.woodstock.rockframework.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.woodstock.rockframework.util.Assert;

public class DelegateMap<K, V> implements Map<K, V> {

	private Map<K, V>	map;

	public DelegateMap(final Map<K, V> map) {
		super();
		Assert.notNull(map, "map");
		this.map = map;
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(final Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(final Object value) {
		return this.map.containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public boolean equals(final Object o) {
		return this.map.equals(o);
	}

	public V get(final Object key) {
		return this.map.get(key);
	}

	@Override
	public int hashCode() {
		return this.map.hashCode();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set<K> keySet() {
		return this.map.keySet();
	}

	public V put(final K key, final V value) {
		return this.map.put(key, value);
	}

	public void putAll(final Map<? extends K, ? extends V> m) {
		this.map.putAll(m);
	}

	public V remove(final Object key) {
		return this.map.remove(key);
	}

	public int size() {
		return this.map.size();
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	public Collection<V> values() {
		return this.map.values();
	}

}
