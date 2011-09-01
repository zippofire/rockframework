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
import java.util.List;
import java.util.ListIterator;

public class DelegateList<E> extends DelegateCollection<E> implements List<E> {

	private List<E>	list;

	public DelegateList(final List<E> list) {
		super(list);
		this.list = list;
	}

	@Override
	public void add(final int index, final E element) {
		this.list.add(index, element);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public boolean equals(final Object o) {
		return this.list.equals(o);
	}
	
	@Override
	public E get(final int index) {
		return this.list.get(index);
	}
	
	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

	@Override
	public int indexOf(final Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		return this.list.listIterator(index);
	}

	@Override
	public E remove(final int index) {
		return this.list.remove(index);
	}

	@Override
	public E set(final int index, final E element) {
		return this.list.set(index, element);
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

}
