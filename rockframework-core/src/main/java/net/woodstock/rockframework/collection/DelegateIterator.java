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

import java.util.Iterator;

import net.woodstock.rockframework.util.Assert;

class DelegateIterator<E> implements Iterator<E> {

	private Iterator<E>	iterator;

	public DelegateIterator(final Iterator<E> iterator) {
		super();
		Assert.notNull(iterator, "iterator");
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public E next() {
		return this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return this.iterator.toString();
	}

}
