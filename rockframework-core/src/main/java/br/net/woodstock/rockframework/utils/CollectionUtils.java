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
package br.net.woodstock.rockframework.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.net.woodstock.rockframework.collection.GenericEnumeration;


public abstract class CollectionUtils {

	private CollectionUtils() {
		//
	}

	public static <E> Collection<E> toCollection(final Enumeration<E> enumeration) {
		return CollectionUtils.toList(enumeration);
	}

	public static <E> Iterator<E> toIterator(final Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<E> list = new LinkedList<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, list);
		Iterator<E> iterator = list.iterator();
		return iterator;
	}

	public static <E> List<E> toList(final Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<E> list = new LinkedList<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, list);
		return list;
	}

	public static <E> Set<E> toSet(final Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		Set<E> set = new LinkedHashSet<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, set);
		return set;
	}

	public static <E> Enumeration<E> toEnumeration(final Collection<E> collection) {
		if (collection == null) {
			return null;
		}
		Iterator<E> iterator = collection.iterator();
		Enumeration<E> enumeration = new GenericEnumeration<E>(iterator);
		return enumeration;
	}

	private static <E> void copyEnumerationToCollection(final Enumeration<E> enumeration, final Collection<E> collection) {
		while (enumeration.hasMoreElements()) {
			E e = enumeration.nextElement();
			collection.add(e);
		}
	}

}
