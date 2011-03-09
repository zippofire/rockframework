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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.woodstock.rockframework.domain.config.DomainConfig;
import net.woodstock.rockframework.domain.config.DomainLog;

public final class JPAPersistenceHelper implements PersistenceHelper<EntityManager> {

	private static JPAPersistenceHelper			instance						= new JPAPersistenceHelper();

	private static final String					JPA_PERSISTENCE_UNIT_PROPERTY	= "jpa.persistenceUnit";

	private static ThreadLocal<EntityManager>	manager							= new ThreadLocal<EntityManager>();

	private EntityManagerFactory				factory;

	private JPAPersistenceHelper() {
		super();
		String s = DomainConfig.getInstance().getValue(JPAPersistenceHelper.JPA_PERSISTENCE_UNIT_PROPERTY);
		this.factory = Persistence.createEntityManagerFactory(s);
	}

	@Override
	public void close() {
		EntityManager m = JPAPersistenceHelper.manager.get();
		if (m != null) {
			EntityTransaction t = m.getTransaction();
			if (t.isActive()) {
				DomainLog.getInstance().getLog().warn("EntityManager contains an active transaction, commiting transaction");
				t.commit();
			}
			m.flush();
			m.close();
			JPAPersistenceHelper.manager.set(null);
		}
	}

	@Override
	public EntityManager get() {
		EntityManager m = JPAPersistenceHelper.manager.get();
		if (m == null) {
			m = this.factory.createEntityManager();
			JPAPersistenceHelper.manager.set(m);
		}
		return m;
	}

	public static JPAPersistenceHelper getInstance() {
		return instance;
	}
}
