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

import org.hibernate.Session;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.GenericRepository;

public class HibernateGenericRepository extends AbstractHibernateRepository implements GenericRepository {

	public HibernateGenericRepository() {
		super();
	}

	@Override
	public void delete(final Entity<?> e) {
		Session s = this.getSession();

		if (this.isTransationEnabled()) {
			s.getTransaction().begin();
		}

		new CommonHibernateGenericRepository(s).delete(e);

		if (this.isTransationEnabled()) {
			s.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			s.flush();
		}
	}

	@Override
	public <E extends Entity<?>> E get(final E entity) {
		return new CommonHibernateGenericRepository(this.getSession()).get(entity);
	}

	@Override
	public void save(final Entity<?> e) {
		Session s = this.getSession();

		if (this.isTransationEnabled()) {
			s.getTransaction().begin();
		}

		new CommonHibernateGenericRepository(this.getSession()).save(e);

		if (this.isTransationEnabled()) {
			s.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			s.flush();
		}
	}

	@Override
	public void update(final Entity<?> e) {
		Session s = this.getSession();

		if (this.isTransationEnabled()) {
			s.getTransaction().begin();
		}

		new CommonHibernateGenericRepository(this.getSession()).update(e);

		if (this.isTransationEnabled()) {
			s.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			s.flush();
		}
	}

}
