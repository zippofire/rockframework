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
package net.woodstock.rockframework.domain.persistence.orm.util;

import org.hibernate.Session;

/**
 * @see HibernatePersistenceHelper
 */
@Deprecated
public abstract class HibernateUtil {

	private HibernateUtil() {
		//
	}

	@Deprecated
	public static Session getSession() {
		return HibernatePersistenceHelper.getInstance().get();
	}

	@Deprecated
	public static void closeSession() {
		HibernatePersistenceHelper.getInstance().close();
	}

}