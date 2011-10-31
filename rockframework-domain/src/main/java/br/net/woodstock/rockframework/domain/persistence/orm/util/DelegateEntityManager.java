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

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

public class DelegateEntityManager implements EntityManager {

	private EntityManager	entityManager;

	public DelegateEntityManager(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public void clear() {
		this.entityManager.clear();
	}

	@Override
	public void close() {
		this.entityManager.close();
	}

	@Override
	public boolean contains(final Object arg0) {
		return this.entityManager.contains(arg0);
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(final String arg0, final Class<T> arg1) {
		return this.entityManager.createNamedQuery(arg0, arg1);
	}

	@Override
	public Query createNamedQuery(final String arg0) {
		return this.entityManager.createNamedQuery(arg0);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Query createNativeQuery(final String arg0, final Class arg1) {
		return this.entityManager.createNativeQuery(arg0, arg1);
	}

	@Override
	public Query createNativeQuery(final String arg0, final String arg1) {
		return this.entityManager.createNativeQuery(arg0, arg1);
	}

	@Override
	public Query createNativeQuery(final String arg0) {
		return this.entityManager.createNativeQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> arg0) {
		return this.entityManager.createQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(final String arg0, final Class<T> arg1) {
		return this.entityManager.createQuery(arg0, arg1);
	}

	@Override
	public Query createQuery(final String arg0) {
		return this.entityManager.createQuery(arg0);
	}

	@Override
	public void detach(final Object arg0) {
		this.entityManager.detach(arg0);
	}

	@Override
	public <T> T find(final Class<T> arg0, final Object arg1, final LockModeType arg2, final Map<String, Object> arg3) {
		return this.entityManager.find(arg0, arg1, arg2, arg3);
	}

	@Override
	public <T> T find(final Class<T> arg0, final Object arg1, final LockModeType arg2) {
		return this.entityManager.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(final Class<T> arg0, final Object arg1, final Map<String, Object> arg2) {
		return this.entityManager.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(final Class<T> arg0, final Object arg1) {
		return this.entityManager.find(arg0, arg1);
	}

	@Override
	public void flush() {
		this.entityManager.flush();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	@Override
	public Object getDelegate() {
		return this.entityManager.getDelegate();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManager.getEntityManagerFactory();
	}

	@Override
	public FlushModeType getFlushMode() {
		return this.entityManager.getFlushMode();
	}

	@Override
	public LockModeType getLockMode(final Object arg0) {
		return this.entityManager.getLockMode(arg0);
	}

	@Override
	public Metamodel getMetamodel() {
		return this.entityManager.getMetamodel();
	}

	@Override
	public Map<String, Object> getProperties() {
		return this.entityManager.getProperties();
	}

	@Override
	public <T> T getReference(final Class<T> arg0, final Object arg1) {
		return this.entityManager.getReference(arg0, arg1);
	}

	@Override
	public EntityTransaction getTransaction() {
		return this.entityManager.getTransaction();
	}

	@Override
	public boolean isOpen() {
		return this.entityManager.isOpen();
	}

	@Override
	public void joinTransaction() {
		this.entityManager.joinTransaction();
	}

	@Override
	public void lock(final Object arg0, final LockModeType arg1, final Map<String, Object> arg2) {
		this.entityManager.lock(arg0, arg1, arg2);
	}

	@Override
	public void lock(final Object arg0, final LockModeType arg1) {
		this.entityManager.lock(arg0, arg1);
	}

	@Override
	public <T> T merge(final T arg0) {
		return this.entityManager.merge(arg0);
	}

	@Override
	public void persist(final Object arg0) {
		this.entityManager.persist(arg0);
	}

	@Override
	public void refresh(final Object arg0, final LockModeType arg1, final Map<String, Object> arg2) {
		this.entityManager.refresh(arg0, arg1, arg2);
	}

	@Override
	public void refresh(final Object arg0, final LockModeType arg1) {
		this.entityManager.refresh(arg0, arg1);
	}

	@Override
	public void refresh(final Object arg0, final Map<String, Object> arg1) {
		this.entityManager.refresh(arg0, arg1);
	}

	@Override
	public void refresh(final Object arg0) {
		this.entityManager.refresh(arg0);
	}

	@Override
	public void remove(final Object arg0) {
		this.entityManager.remove(arg0);
	}

	@Override
	public void setFlushMode(final FlushModeType arg0) {
		this.entityManager.setFlushMode(arg0);
	}

	@Override
	public void setProperty(final String arg0, final Object arg1) {
		this.entityManager.setProperty(arg0, arg1);
	}

	@Override
	public <T> T unwrap(final Class<T> arg0) {
		return this.entityManager.unwrap(arg0);
	}

}
