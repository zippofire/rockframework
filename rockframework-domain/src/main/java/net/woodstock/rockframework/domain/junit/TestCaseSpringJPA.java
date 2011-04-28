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
package net.woodstock.rockframework.domain.junit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.EntityManagerHolder;

public abstract class TestCaseSpringJPA extends TestCaseSpring {

	protected abstract EntityManagerFactory getEntityManagerFactory();

	private EntityManager	entityManager;

	@Override
	protected void setUp() throws Exception {
		EntityManagerFactory factory = this.getEntityManagerFactory();
		this.entityManager = factory.createEntityManager();
		this.bind(factory, new EntityManagerHolder(this.entityManager));
	}

	@Override
	protected void tearDown() throws Exception {
		EntityManagerFactory factory = this.getEntityManagerFactory();
		EntityManagerHolder holder = (EntityManagerHolder) this.unbind(factory);
		EntityManager manager = holder.getEntityManager();
		manager.close();
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

}
