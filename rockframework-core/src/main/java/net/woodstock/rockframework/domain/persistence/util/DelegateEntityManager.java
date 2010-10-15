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
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class DelegateEntityManager implements EntityManager {

	private EntityManager	entityManager;

	public DelegateEntityManager(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public void persist(final Object entity) {
		this.entityManager.persist(entity);
	}

	@Override
	public <T> T merge(final T entity) {
		return this.entityManager.merge(entity);
	}

	@Override
	public void remove(final Object entity) {
		this.entityManager.remove(entity);
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey) {
		return this.entityManager.find(entityClass, primaryKey);
	}

	@Override
	public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
		return this.entityManager.getReference(entityClass, primaryKey);
	}

	@Override
	public void flush() {
		this.entityManager.flush();
	}

	@Override
	public void setFlushMode(final FlushModeType flushMode) {
		this.entityManager.setFlushMode(flushMode);
	}

	@Override
	public FlushModeType getFlushMode() {
		return this.entityManager.getFlushMode();
	}

	@Override
	public void lock(final Object entity, final LockModeType lockMode) {
		this.entityManager.lock(entity, lockMode);
	}

	@Override
	public void refresh(final Object entity) {
		this.entityManager.refresh(entity);
	}

	@Override
	public void clear() {
		this.entityManager.clear();
	}

	@Override
	public boolean contains(final Object entity) {
		return this.entityManager.contains(entity);
	}

	@Override
	public Query createQuery(final String ejbqlString) {
		return this.entityManager.createQuery(ejbqlString);
	}

	@Override
	public Query createNamedQuery(final String name) {
		return this.entityManager.createNamedQuery(name);
	}

	@Override
	public Query createNativeQuery(final String sqlString) {
		return this.entityManager.createNativeQuery(sqlString);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Query createNativeQuery(final String sqlString, final Class resultClass) {
		return this.entityManager.createNativeQuery(sqlString, resultClass);
	}

	@Override
	public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
		return this.entityManager.createNativeQuery(sqlString, resultSetMapping);
	}

	@Override
	public void close() {
		this.entityManager.close();
	}

	@Override
	public boolean isOpen() {
		return this.entityManager.isOpen();
	}

	@Override
	public EntityTransaction getTransaction() {
		return this.entityManager.getTransaction();
	}

	@Override
	public void joinTransaction() {
		this.entityManager.joinTransaction();
	}

	@Override
	public Object getDelegate() {
		return this.entityManager.getDelegate();
	}

}
