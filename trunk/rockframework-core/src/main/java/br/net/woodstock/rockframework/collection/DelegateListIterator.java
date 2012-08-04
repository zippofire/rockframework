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

import java.util.ListIterator;

import br.net.woodstock.rockframework.util.Assert;

public class DelegateListIterator<E> implements ListIterator<E> {

	private ListIterator<E>	listIterator;

	public DelegateListIterator(final ListIterator<E> listIterator) {
		super();
		Assert.notNull(listIterator, "listIterator");
		this.listIterator = listIterator;
	}

	@Override
	public void add(final E e) {
		this.listIterator.add(e);
	}

	@Override
	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	@Override
	public E next() {
		return this.listIterator.next();
	}

	@Override
	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	@Override
	public E previous() {
		return this.listIterator.previous();
	}

	@Override
	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	@Override
	public void remove() {
		this.listIterator.remove();
	}

	@Override
	public void set(final E e) {
		this.listIterator.set(e);
	}

	@Override
	public String toString() {
		return this.listIterator.toString();
	}

}
