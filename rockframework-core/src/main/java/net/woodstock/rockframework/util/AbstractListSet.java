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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

abstract class AbstractListSet<E> implements Set<E> {

	private List<E>	list;

	public AbstractListSet(List<E> list) {
		super();
		this.list = list;
	}

	public boolean add(E e) {
		if (e == null) {
			return false;
		}
		if (this.list.contains(e)) {
			return false;
		}
		this.list.add(e);
		return true;
	}

	public boolean addAll(Collection<? extends E> collection) {
		for (E e : collection) {
			if (e == null) {
				return false;
			}
			if (this.list.contains(e)) {
				return false;
			}
		}
		for (E e : collection) {
			this.list.add(e);
		}
		return true;
	}

	public void clear() {
		this.list.clear();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public boolean containsAll(Collection<?> collection) {
		return this.list.containsAll(collection);
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	public boolean removeAll(Collection<?> collection) {
		return this.list.remove(collection);
	}

	public boolean retainAll(Collection<?> collection) {
		return this.list.retainAll(collection);
	}

	public int size() {
		return this.list.size();
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T> T[] toArray(T[] t) {
		return this.list.toArray(t);
	}

	@Override
	public String toString() {
		return this.list.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return this.list.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

}
