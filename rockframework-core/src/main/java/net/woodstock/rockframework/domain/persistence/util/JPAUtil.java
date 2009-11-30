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
package net.woodstock.rockframework.domain.persistence.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.sys.SysLogger;

public abstract class JPAUtil {

	private static final String					JPA_PERSISTENCE_UNIT_PROPERTY	= "jpa.persistenceUnit";

	private static ThreadLocal<EntityManager>	manager							= new ThreadLocal<EntityManager>();

	private static EntityManagerFactory			factory;

	private JPAUtil() {
		//
	}

	public static EntityManager getManager() {
		if (JPAUtil.factory == null) {
			String s = CoreConfig.getInstance().getValue(JPAUtil.JPA_PERSISTENCE_UNIT_PROPERTY);
			JPAUtil.factory = Persistence.createEntityManagerFactory(s);
		}
		EntityManager m = manager.get();
		if (m == null) {
			m = JPAUtil.factory.createEntityManager();
			manager.set(m);
		}
		return m;
	}

	public static void closeManager() {
		EntityManager m = manager.get();
		if (m != null) {
			EntityTransaction t = m.getTransaction();
			if (t.isActive()) {
				SysLogger.getLogger().warn("EntityManager contains an active transaction, commiting transaction");
				t.commit();
			}
			m.flush();
			m.close();
			manager.set(null);
		}
	}

}
