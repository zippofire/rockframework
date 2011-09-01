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

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.GenericRepository;
import br.net.woodstock.rockframework.domain.persistence.orm.util.PersistenceUtil;

public abstract class SpringHibernateGenericRepository extends SpringHibernateRepository implements GenericRepository {

	public SpringHibernateGenericRepository() {
		super();
	}

	@Override
	public void delete(final Entity<?> e) {
		this.getHibernateTemplate().execute(new HibernateDeleteCallback(e));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		Class<E> clazz = PersistenceUtil.getRealClass(entity);

		E e = (E) this.getHibernateTemplate().get(clazz, entity.getId());
		if (e != null) {
			this.getHibernateTemplate().refresh(e);
		}
		return e;
	}

	@Override
	public void save(final Entity<?> e) {
		this.getHibernateTemplate().save(e);
	}

	@Override
	public void update(final Entity<?> e) {
		this.getHibernateTemplate().execute(new HibernateUpdateCallback(e));
	}

}
