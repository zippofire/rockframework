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
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;

abstract class AbstractJPAGenericRepository extends AbstractJPARepository implements GenericRepository {

	public AbstractJPAGenericRepository() {
		super();
	}

	public void delete(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		m.remove(e);
		m.flush();
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> E get(E entity) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		E e = (E) m.find(entity.getClass(), entity.getId());
		return e;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(E e, String order) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		String sql = RepositoryHelper.getListAllSql(e.getClass(), order);
		Query q = m.createQuery(sql);
		Collection<E> list = q.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listByExample(E e, Map<String, Object> options) throws PersistenceException {
		QueryBuilder builder = new HibernateQueryBuilder();
		if ((options != null) && (options.size() > 0)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		builder.parse(e);
		Query q = (Query) builder.getQuery(this.getEntityManager());
		Collection<E> list = q.getResultList();
		return list;
	}

	public void save(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		m.persist(e);
	}

	public void update(Entity<?> e) throws PersistenceException {
		EntityManager m = this.getEntityManager();
		m.merge(e);
		m.flush();
	}

}
