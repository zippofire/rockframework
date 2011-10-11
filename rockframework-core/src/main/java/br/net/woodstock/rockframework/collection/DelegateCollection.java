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

import java.util.Collection;
import java.util.Iterator;

import br.net.woodstock.rockframework.util.Assert;

public class DelegateCollection<E> implements Collection<E> {

	private Collection<E>	collection;

	public DelegateCollection(final Collection<E> collection) {
		super();
		Assert.notNull(collection, "collection");
		this.collection = collection;
	}

	@Override
	public boolean add(final E e) {
		return this.collection.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		return this.collection.addAll(c);
	}

	@Override
	public void clear() {
		this.collection.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return this.collection.contains(o);
	}

	@Override
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

	@Override
	public boolean isEmpty() {
		return this.collection.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.collection.iterator();
	}

	@Override
	public boolean remove(final Object o) {
		return this.collection.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.collection.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.collection.retainAll(c);
	}

	@Override
	public int size() {
		return this.collection.size();
	}

	@Override
	public Object[] toArray() {
		return this.collection.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return this.collection.toArray(a);
	}

	@Override
	public String toString() {
		return this.collection.toString();
	}

}
