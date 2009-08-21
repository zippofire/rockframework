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

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.PersistenceException;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.Transaction;

abstract class AbstractTransactionHibernateRepository extends AbstractHibernateGenericRepository {

	public AbstractTransactionHibernateRepository() {
		super();
	}

	@Override
	public void delete(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		Transaction t = this.getTransaction();
		t.begin();
		try {
			s.delete(e);
		} catch (PropertyValueException pve) {
			s.refresh(e);
			s.delete(e);
		} catch (NonUniqueObjectException nuoe) {
			e = (Entity<?>) s.get(e.getClass(), e.getId());
			s.delete(e);
		}
		t.commit();
		s.flush();
	}

	@Override
	public void save(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		Transaction t = this.getTransaction();
		t.begin();
		s.save(e);
		t.commit();
		s.flush();
	}

	@Override
	public void update(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		Transaction t = this.getTransaction();
		t.begin();
		try {
			s.update(e);
		} catch (NonUniqueObjectException nuoe) {
			s.merge(e);
		}
		t.commit();
		s.flush();
	}

	protected abstract Transaction getTransaction() throws PersistenceException;

}
