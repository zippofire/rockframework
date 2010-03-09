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

import net.woodstock.rockframework.domain.persistence.Repository;

abstract class AbstractJPARepository implements Repository {

	private EntityManagerFactory	entityManagerFactory;

	private boolean					transationEnabled;

	private boolean					flushEnabled;

	public AbstractJPARepository() {
		super();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}

	public void setEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public boolean isTransationEnabled() {
		return this.transationEnabled;
	}

	public void setTransationEnabled(final boolean transationEnabled) {
		this.transationEnabled = transationEnabled;
	}

	public boolean isFlushEnabled() {
		return this.flushEnabled;
	}

	public void setFlushEnabled(final boolean flushEnabled) {
		this.flushEnabled = flushEnabled;
	}

	protected EntityManager getEntityManager() {
		if (this.entityManagerFactory == null) {
			throw new IllegalStateException("Entity Manager Factory is null");
		}
		return this.entityManagerFactory.createEntityManager();
	}

}
