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

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.query.impl.JPAQueryBuilder;

abstract class AbstractJPAGenericRepository extends AbstractJPARepository implements GenericRepository {

	public AbstractJPAGenericRepository() {
		super();
	}

	public void delete(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		m.remove(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> E get(final E entity) {
		EntityManager m = this.getEntityManager();
		E e = (E) m.find(entity.getClass(), entity.getId());
		return e;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		EntityManager m = this.getEntityManager();
		String sql = RepositoryHelper.getListAllSql(e.getClass(), options, false);
		Query q = m.createQuery(sql);
		Collection<E> list = q.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		JPAQueryBuilder builder = new JPAQueryBuilder(this.getEntityManager());
		builder.setEntity(e);
		if ((options != null) && (options.size() > 0)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		builder.build();
		Query q = builder.getQuery();
		Collection<E> list = q.getResultList();
		return list;
	}

	public void save(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		m.persist(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

	public void update(final Entity<?> e) {
		EntityManager m = this.getEntityManager();

		if (this.isTransationEnabled()) {
			m.getTransaction().begin();
		}

		m.merge(e);

		if (this.isTransationEnabled()) {
			m.getTransaction().commit();
		}

		if (this.isFlushEnabled()) {
			m.flush();
		}
	}

}
