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
import java.util.List;
import java.util.ListIterator;

public final class ImmutableList<E> implements List<E> {

	private static final long	serialVersionUID	= 623412197140388020L;

	private List<E>				list;

	private ImmutableList(final List<E> list) {
		super();
		this.list = list;
	}

	public boolean add(final E e) {
		return this.list.add(e);
	}

	public void add(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(final int index, final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(final Object o) {
		return this.list.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return this.list.equals(o);
	}

	public E get(final int index) {
		return this.list.get(index);
	}

	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

	public int indexOf(final Object o) {
		return this.list.indexOf(o);
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public Iterator<E> iterator() {
		return new ImmutableIterator<E>(this.list.iterator());
	}

	public int lastIndexOf(final Object o) {
		return this.list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return new ImmutableListIterator<E>(this.list.listIterator());
	}

	public ListIterator<E> listIterator(final int index) {
		return new ImmutableListIterator<E>(this.list.listIterator(index));
	}

	public E remove(final int index) {
		throw new UnsupportedOperationException();
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

	public E set(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return this.list.size();
	}

	public List<E> subList(final int fromIndex, final int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T> T[] toArray(final T[] a) {
		return this.list.toArray(a);
	}

	// Static
	public static <T> List<T> toImmutable(final List<T> list) {
		return new ImmutableList<T>(list);
	}

}
