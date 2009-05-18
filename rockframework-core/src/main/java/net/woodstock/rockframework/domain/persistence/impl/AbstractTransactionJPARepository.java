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

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.PersistenceException;

public abstract class AbstractTransactionJPARepository extends AbstractJPARepository {

	public AbstractTransactionJPARepository() {
		super();
	}

	@Override
	public void delete(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		EntityTransaction t = this.getEntityTransaction();
		t.begin();
		m.remove(e);
		t.commit();
		m.flush();
	}

	@Override
	public void save(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		EntityTransaction t = this.getEntityTransaction();
		t.begin();
		m.persist(e);
		t.commit();
		m.flush();
	}

	@Override
	public void update(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		EntityTransaction t = this.getEntityTransaction();
		t.begin();
		m.merge(e);
		t.commit();
		m.flush();
	}

	protected abstract EntityTransaction getEntityTransaction() throws PersistenceException;

}
