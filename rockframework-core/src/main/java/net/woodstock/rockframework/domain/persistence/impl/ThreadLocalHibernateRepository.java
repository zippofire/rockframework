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
package net.woodstock.rockframework.domain.persistence.impl;

import net.woodstock.rockframework.domain.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ThreadLocalHibernateRepository extends AbstractTransactionHibernateRepository {

	private static final ThreadLocal<Session>		session		= new ThreadLocal<Session>();

	private static final ThreadLocal<Transaction>	transaction	= new ThreadLocal<Transaction>();

	private SessionFactory							sessionFactory;

	public ThreadLocalHibernateRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected Session getSession() throws PersistenceException {
		try {
			if (ThreadLocalHibernateRepository.session.get() == null) {
				ThreadLocalHibernateRepository.session.set(this.sessionFactory.openSession());
			}
			if (!ThreadLocalHibernateRepository.session.get().isOpen()) {
				ThreadLocalHibernateRepository.session.set(this.sessionFactory.openSession());
			}
			return ThreadLocalHibernateRepository.session.get();
		} catch (Exception e) {
			this.getLogger().warn(e.getMessage(), e);
			throw new PersistenceException(e);
		}
	}

	@Override
	protected Transaction getTransaction() throws PersistenceException {
		try {
			if (ThreadLocalHibernateRepository.transaction.get() == null) {
				Session session = this.getSession();
				ThreadLocalHibernateRepository.transaction.set(session.getTransaction());
			}
			if (!ThreadLocalHibernateRepository.transaction.get().isActive()) {
				Session session = this.getSession();
				ThreadLocalHibernateRepository.transaction.set(session.getTransaction());
			}
			return ThreadLocalHibernateRepository.transaction.get();
		} catch (Exception e) {
			this.getLogger().warn(e.getMessage(), e);
			throw new PersistenceException(e);
		}
	}

}
