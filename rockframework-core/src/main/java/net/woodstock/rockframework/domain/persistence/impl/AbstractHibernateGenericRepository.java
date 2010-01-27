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

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.query.CacheMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

abstract class AbstractHibernateGenericRepository extends AbstractHibernateRepository implements GenericRepository {

	public static final String	MSG_ERROR_TWO_SESSION	= "Illegal attempt to associate a collection with two open sessions";

	public AbstractHibernateGenericRepository() {
		super();
	}

	public void delete(final Entity<?> e) {
		Session s = this.getSession();
		try {
			s.delete(e);
		} catch (PropertyValueException pve) {
			s.refresh(e);
			s.delete(e);
		} catch (NonUniqueObjectException nuoe) {
			Entity<?> tmp = (Entity<?>) s.get(e.getClass(), e.getId());
			s.delete(tmp);
		}
		s.flush();
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> E get(final E entity) {
		Session s = this.getSession();
		E e = (E) s.get(entity.getClass(), entity.getId());
		return e;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		Session s = this.getSession();
		String sql = RepositoryHelper.getListAllSql(e.getClass(), options);
		Query q = s.createQuery(sql);

		if ((options.containsKey(QueryBuilder.OPTION_CACHE_MODE)) && (options.get(QueryBuilder.OPTION_CACHE_MODE) instanceof CacheMode)) {
			CacheMode cacheMode = (CacheMode) options.get(QueryBuilder.OPTION_CACHE_MODE);
			if (cacheMode == CacheMode.ENABLED) {
				q.setCacheable(true);
				q.setCacheMode(org.hibernate.CacheMode.NORMAL);
			}
		}

		Collection<E> list = q.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		HibernateQueryBuilder builder = new HibernateQueryBuilder(this.getSession());
		builder.setEntity(e);
		if ((options != null) && (options.size() > 0)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		builder.build();
		Query q = builder.getQuery();
		Collection<E> list = q.list();
		return list;
	}

	public void save(final Entity<?> e) {
		Session s = this.getSession();
		s.save(e);
		s.flush();
	}

	public void update(final Entity<?> e) {
		Session s = this.getSession();
		try {
			s.update(e);
		} catch (NonUniqueObjectException nuoe) {
			s.merge(e);
		} catch (HibernateException he) {
			if (he.getMessage().startsWith(AbstractHibernateGenericRepository.MSG_ERROR_TWO_SESSION)) {
				s.merge(e);
			}
		}
		s.flush();
	}

}
