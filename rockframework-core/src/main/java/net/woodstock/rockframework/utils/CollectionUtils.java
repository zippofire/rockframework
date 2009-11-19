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
package net.woodstock.rockframework.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class CollectionUtils {

	private CollectionUtils() {
		//
	}

	public static <E> Collection<E> toCollection(Enumeration<E> enumeration) {
		return CollectionUtils.toList(enumeration);
	}

	public static <E> Iterator<E> toIterator(Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<E> list = new LinkedList<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, list);
		Iterator<E> iterator = list.iterator();
		return iterator;
	}

	public static <E> List<E> toList(Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<E> list = new LinkedList<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, list);
		return list;
	}

	public static <E> Set<E> toSet(Enumeration<E> enumeration) {
		if (enumeration == null) {
			return null;
		}
		Set<E> set = new LinkedHashSet<E>();
		CollectionUtils.copyEnumerationToCollection(enumeration, set);
		return set;
	}

	private static <E> void copyEnumerationToCollection(Enumeration<E> enumeration, Collection<E> collection) {
		while (enumeration.hasMoreElements()) {
			E e = enumeration.nextElement();
			collection.add(e);
		}
	}

}
