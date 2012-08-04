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
import java.util.Enumeration;
import java.util.Iterator;

public class GenericEnumeration<E> implements Enumeration<E> {

	private Iterator<E>	iterator;

	public GenericEnumeration(final Iterator<E> iterator) {
		super();
		this.iterator = iterator;
	}

	public GenericEnumeration(final Collection<E> collection) {
		super();
		Iterator<E> iterator = collection.iterator();
		this.iterator = iterator;
	}

	@Override
	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return this.iterator.next();
	}

}
