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

import java.util.Iterator;

import br.net.woodstock.rockframework.util.Assert;


class ImmutableIterator<E> extends DelegateIterator<E> {

	public ImmutableIterator(final Iterator<E> iterator) {
		super(iterator);
		Assert.notNull(iterator, "iterator");
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
