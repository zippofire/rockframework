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
package br.net.woodstock.rockframework.domain.persistence.orm.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import br.net.woodstock.rockframework.domain.config.DomainLog;

public final class HibernatePersistenceHelper implements PersistenceHelper<Session> {

	private static HibernatePersistenceHelper	instance	= new HibernatePersistenceHelper();

	private static ThreadLocal<Session>			session		= new ThreadLocal<Session>();

	private SessionFactory						factory;

	private HibernatePersistenceHelper() {
		super();
		this.factory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public void close() {
		Session s = HibernatePersistenceHelper.session.get();
		if (s != null) {
			Transaction t = s.getTransaction();
			if (t.isActive()) {
				DomainLog.getInstance().getLog().warn("Session contains an active transaction, commiting transaction");
				t.commit();
			}
			s.flush();
			s.close();
			HibernatePersistenceHelper.session.set(null);
		}
	}

	@Override
	public Session get() {
		Session s = HibernatePersistenceHelper.session.get();
		if (s == null) {
			s = this.factory.openSession();
			HibernatePersistenceHelper.session.set(s);
		}
		return s;
	}

	public static HibernatePersistenceHelper getInstance() {
		return HibernatePersistenceHelper.instance;
	}
}
