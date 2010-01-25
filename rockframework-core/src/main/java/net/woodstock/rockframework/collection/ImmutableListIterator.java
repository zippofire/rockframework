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

import java.util.ListIterator;

class ImmutableListIterator<E> implements ListIterator<E> {

	private ListIterator<E>	iterator;

	public ImmutableListIterator(final ListIterator<E> iterator) {
		super();
		this.iterator = iterator;
	}

	public void add(final E e) {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public boolean hasPrevious() {
		return this.iterator.hasPrevious();
	}

	public E next() {
		return this.iterator.next();
	}

	public int nextIndex() {
		return this.iterator.nextIndex();
	}

	public E previous() {
		return this.iterator.previous();
	}

	public int previousIndex() {
		return this.iterator.previousIndex();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public void set(final E e) {
		throw new UnsupportedOperationException();
	}

}
