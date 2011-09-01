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
package br.net.woodstock.rockframework.domain.persistence.orm.impl;

import javax.persistence.EntityManager;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.GenericRepository;


public class JPAGenericRepository extends AbstractJPARepository implements GenericRepository {

	public JPAGenericRepository() {
		super();
	}

	@Override
	public void delete(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		new CommonJPAGenericRepository(m).delete(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

	@Override
	public <E extends Entity<?>> E get(final E entity) {
		return new CommonJPAGenericRepository(this.getEntityManager()).get(entity);
	}

	@Override
	public void save(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		new CommonJPAGenericRepository(m).save(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

	@Override
	public void update(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		new CommonJPAGenericRepository(m).update(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

}
