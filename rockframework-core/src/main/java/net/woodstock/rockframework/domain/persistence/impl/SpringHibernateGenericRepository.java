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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;

import org.hibernate.Query;

public class SpringHibernateGenericRepository extends SpringHibernateRepository implements GenericRepository {

	public SpringHibernateGenericRepository() {
		super();
	}

	public void delete(Entity<?> e) throws PersistenceException {
		this.getHibernateTemplate().execute(new HibernateDeleteCallback(e));
	}

	@SuppressWarnings("unchecked")
	public <ID extends Serializable, E extends Entity<ID>> E get(Class<E> clazz, ID id) throws PersistenceException {
		return (E) this.getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(Class<E> clazz, String order) throws PersistenceException {
		String sql = RepositoryHelper.getListAllSql(clazz, order);
		return this.getHibernateTemplate().find(sql);
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
		Query q = (Query) builder.getQuery(this.getSession());
		Collection<E> list = q.list();
		return list;
	}

	public void save(Entity<?> e) throws PersistenceException {
		this.getHibernateTemplate().save(e);
	}

	public void update(Entity<?> e) throws PersistenceException {
		this.getHibernateTemplate().execute(new HibernateUpdateCallback(e));
	}

}
