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
import java.util.Iterator;

public final class ImmutableCollection<E> implements Collection<E> {

	private Collection<E>	collection;

	private ImmutableCollection(final Collection<E> set) {
		super();
		this.collection = set;
	}

	public boolean add(final E e) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(final Object o) {
		return this.collection.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {
		return this.collection.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return this.collection.equals(o);
	}

	@Override
	public int hashCode() {
		return this.collection.hashCode();
	}

	public boolean isEmpty() {
		return this.collection.isEmpty();
	}

	public Iterator<E> iterator() {
		return new ImmutableIterator<E>(this.collection.iterator());
	}

	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return this.collection.size();
	}

	public Object[] toArray() {
		return this.collection.toArray();
	}

	public <T> T[] toArray(final T[] a) {
		return this.collection.toArray(a);
	}

	// Static
	public static <T> Collection<T> toImmutable(final Collection<T> collection) {
		return new ImmutableCollection<T>(collection);
	}

}
