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
package br.net.woodstock.rockframework.collection;

import java.util.Map;

public final class ImmutableMap<K, V> extends DelegateMap<K, V> {

	private ImmutableMap(final Map<K, V> map) {
		super(map);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V put(final K key, final V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(final Object key) {
		throw new UnsupportedOperationException();
	}

	// Static
	public static <K, V> Map<K, V> toImmutable(final Map<K, V> map) {
		return new ImmutableMap<K, V>(map);
	}

}
