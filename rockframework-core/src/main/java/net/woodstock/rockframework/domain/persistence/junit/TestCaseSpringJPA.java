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
package net.woodstock.rockframework.domain.persistence.junit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class TestCaseSpringJPA extends TestCase {

	private EntityManagerFactory	factory;

	public abstract EntityManagerFactory getEntityManagerFactory();

	@Override
	protected void setUp() throws Exception {
		this.factory = this.getEntityManagerFactory();
		EntityManager manager = this.factory.createEntityManager();
		TransactionSynchronizationManager.bindResource(manager, new EntityManagerHolder(manager));
	}

	@Override
	protected void tearDown() throws Exception {
		EntityManagerHolder entityManagerHolder = (EntityManagerHolder) TransactionSynchronizationManager
				.unbindResource(this.factory);
		entityManagerHolder.getEntityManager().close();
	}

}
