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

public abstract class PersistenceUtil {

	private static final String	HIBERNATE_PROXY_CLASS	= "org.hibernate.proxy.HibernateProxy";

	public static final String	OPENJPA_PROXY_CLASS		= "org.apache.openjpa.enhance.PersistenceCapable";

	private PersistenceUtil() {
		//
	}

	private static boolean isHibernateAvailable() {
		try {
			Class.forName(PersistenceUtil.HIBERNATE_PROXY_CLASS);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isJpaAvailable() {
		try {
			Class.forName(PersistenceUtil.OPENJPA_PROXY_CLASS);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static boolean isProxy(final Object o) {
		boolean b = false;
		if (PersistenceUtil.isJpaAvailable()) {
			b = JPAUtil.isProxy(o);
		}
		if (!b) {
			if (PersistenceUtil.isHibernateAvailable()) {
				b = HibernateUtil.isProxy(o);
			}
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public static <E> Class<E> getRealClass(final E e) {
		if (PersistenceUtil.isProxy(e)) {
			if (PersistenceUtil.isJpaAvailable()) {
				return JPAUtil.getRealClass(e);
			}
			if (PersistenceUtil.isHibernateAvailable()) {
				return HibernateUtil.getRealClass(e);
			}
		}
		return (Class<E>) e.getClass();
	}

}
