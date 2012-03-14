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
package br.net.woodstock.rockframework.persistence.orm.util;

import org.apache.openjpa.enhance.PersistenceCapable;

import br.net.woodstock.rockframework.ImprobableException;

abstract class JPAUtil {

	private JPAUtil() {
		//
	}

	public static boolean isProxy(final Object e) {
		boolean b = (e instanceof PersistenceCapable);
		return b;
	}

	@SuppressWarnings("unchecked")
	public static <E> Class<E> getRealClass(final E e) {
		try {
			return (Class<E>) Class.forName(e.getClass().getCanonicalName());
		} catch (ClassNotFoundException ex) {
			throw new ImprobableException(ex);
		}
	}

}
