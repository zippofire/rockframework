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
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;
import net.woodstock.rockframework.domain.persistence.util.Constants;
import net.woodstock.rockframework.utils.ConditionUtils;

import org.hibernate.Query;
import org.hibernate.Session;

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
		return (E) this.getHibernateTemplate().get(entity.getClass(), entity.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		Session s = this.getSession();
		String sql = RepositoryHelper.getListAllSql(e.getClass(), options, true);
		Query q = s.createQuery(sql);

		if (ConditionUtils.isNotEmpty(options)) {
			if (options.containsKey(Constants.OPTION_CACHE_MODE)) {
				Object o = options.get(Constants.OPTION_CACHE_MODE);
				if (o instanceof CacheMode) {
					CacheMode cacheMode = (CacheMode) o;
					if (cacheMode == CacheMode.ENABLED) {
						q.setCacheable(true);
						q.setCacheMode(org.hibernate.CacheMode.NORMAL);
					}
				}
			}
		}

		Collection<E> list = q.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		HibernateQueryBuilder builder = new HibernateQueryBuilder(this.getSession());
		builder.setEntity(e);
		if (ConditionUtils.isNotEmpty(options)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		Query q = builder.getQuery();
		Collection<E> list = q.list();
		return list;
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
