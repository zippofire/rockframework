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
import java.util.Set;

public final class ImmutableSet<E> implements Set<E> {

	private static final long	serialVersionUID	= -5090564103604977206L;

	private Set<E>				set;

	private ImmutableSet(final Set<E> set) {
		super();
		this.set = set;
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
		return this.set.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {
		return this.set.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return this.set.equals(o);
	}

	@Override
	public int hashCode() {
		return this.set.hashCode();
	}

	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	public Iterator<E> iterator() {
		return new ImmutableIterator<E>(this.set.iterator());
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
		return this.set.size();
	}

	public Object[] toArray() {
		return this.set.toArray();
	}

	public <T> T[] toArray(final T[] a) {
		return this.set.toArray(a);
	}

	// Static
	public static <T> Set<T> toImmutable(final Set<T> set) {
		return new ImmutableSet<T>(set);
	}

}
