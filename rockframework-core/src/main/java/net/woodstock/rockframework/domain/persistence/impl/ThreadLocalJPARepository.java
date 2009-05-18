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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import net.woodstock.rockframework.domain.persistence.PersistenceException;

public class ThreadLocalJPARepository extends AbstractTransactionJPARepository {

	private static final ThreadLocal<EntityManager>		entityManager	= new ThreadLocal<EntityManager>();

	private static final ThreadLocal<EntityTransaction>	transaction		= new ThreadLocal<EntityTransaction>();

	private EntityManagerFactory						entityManagerFactory;

	public ThreadLocalJPARepository(EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	protected EntityManager getEntityManager() throws PersistenceException {
		try {
			if (ThreadLocalJPARepository.entityManager.get() == null) {
				ThreadLocalJPARepository.entityManager.set(this.entityManagerFactory.createEntityManager());
			}
			if (!ThreadLocalJPARepository.entityManager.get().isOpen()) {
				ThreadLocalJPARepository.entityManager.set(this.entityManagerFactory.createEntityManager());
			}
			return ThreadLocalJPARepository.entityManager.get();
		} catch (Exception e) {
			this.getLogger().warn(e.getMessage(), e);
			throw new PersistenceException(e);
		}
	}

	@Override
	protected EntityTransaction getEntityTransaction() throws PersistenceException {
		try {
			if (ThreadLocalJPARepository.transaction.get() == null) {
				EntityManager entityManager = this.getEntityManager();
				ThreadLocalJPARepository.transaction.set(entityManager.getTransaction());
			}
			if (!ThreadLocalJPARepository.transaction.get().isActive()) {
				EntityManager entityManager = this.getEntityManager();
				ThreadLocalJPARepository.transaction.set(entityManager.getTransaction());
			}
			return ThreadLocalJPARepository.transaction.get();
		} catch (Exception e) {
			this.getLogger().warn(e.getMessage(), e);
			throw new PersistenceException(e);
		}
	}

}
