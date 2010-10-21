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

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.query.CacheMode;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;
import net.woodstock.rockframework.domain.persistence.util.Constants;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

class CommonHibernateGenericRepository implements GenericRepository {

	private static final String		PROPERTY_GET_TYPE		= "hibernate.getType";

	private static final GetType	GET_TYPE				= GetType.valueOf(CoreConfig.getInstance().getValue(CommonHibernateGenericRepository.PROPERTY_GET_TYPE));

	private static final String		MSG_ERROR_TWO_SESSION	= "Illegal attempt to associate a collection with two open sessions";

	private static final String		ID_ATTRIBUTE			= "id";

	private Session					session;

	public CommonHibernateGenericRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	public void delete(final Entity<?> e) {
		try {
			this.session.delete(e);
		} catch (PropertyValueException pve) {
			this.session.refresh(e);
			this.session.delete(e);
		} catch (NonUniqueObjectException nuoe) {
			Entity<?> tmp = (Entity<?>) this.session.get(e.getClass(), e.getId());
			this.session.delete(tmp);
		} catch (HibernateException he) {
			if (he.getMessage().startsWith(CommonHibernateGenericRepository.MSG_ERROR_TWO_SESSION)) {
				String sql = RepositoryHelper.getDeleteSql(e.getClass(), true);
				Query query = this.session.createQuery(sql);
				query.setParameter(CommonHibernateGenericRepository.ID_ATTRIBUTE, e.getId());
				query.executeUpdate();
			} else {
				throw he;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		if (CommonHibernateGenericRepository.GET_TYPE == GetType.LOAD) {
			try {
				E e = (E) this.session.load(entity.getClass(), entity.getId());
				return e;
			} catch (ObjectNotFoundException e) {
				return null;
			}
		}
		E e = (E) this.session.get(entity.getClass(), entity.getId());
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		String sql = RepositoryHelper.getListAllSql(e.getClass(), options, true);
		Query q = this.session.createQuery(sql);

		if ((options != null) && (options.containsKey(Constants.OPTION_CACHE_MODE)) && (options.get(Constants.OPTION_CACHE_MODE) instanceof CacheMode)) {
			CacheMode cacheMode = (CacheMode) options.get(Constants.OPTION_CACHE_MODE);
			if (cacheMode == CacheMode.ENABLED) {
				q.setCacheable(true);
				q.setCacheMode(org.hibernate.CacheMode.NORMAL);
			}
		}

		Collection<E> list = q.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		HibernateQueryBuilder builder = new HibernateQueryBuilder(this.session);
		builder.setEntity(e);
		if ((options != null) && (options.size() > 0)) {
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
		this.session.save(e);
	}

	@Override
	public void update(final Entity<?> e) {
		try {
			this.session.update(e);
		} catch (NonUniqueObjectException nuoe) {
			this.session.merge(e);
		} catch (HibernateException he) {
			if (he.getMessage().startsWith(CommonHibernateGenericRepository.MSG_ERROR_TWO_SESSION)) {
				this.session.merge(e);
			} else {
				throw he;
			}
		}
	}

	public static enum GetType {
		GET, LOAD;
	}

}
