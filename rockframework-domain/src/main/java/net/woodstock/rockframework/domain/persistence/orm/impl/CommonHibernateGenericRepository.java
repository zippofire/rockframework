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
package net.woodstock.rockframework.domain.persistence.orm.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.config.DomainConfig;
import net.woodstock.rockframework.domain.persistence.orm.GenericRepository;
import net.woodstock.rockframework.domain.persistence.orm.util.PersistenceUtil;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

class CommonHibernateGenericRepository implements GenericRepository {

	private static final String		PROPERTY_GET_TYPE		= "hibernate.getType";

	private static final GetType	GET_TYPE				= GetType.valueOf(DomainConfig.getInstance().getValue(CommonHibernateGenericRepository.PROPERTY_GET_TYPE));

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
		Class<E> clazz = PersistenceUtil.getRealClass(entity);

		if (CommonHibernateGenericRepository.GET_TYPE == GetType.LOAD) {
			try {
				E e = (E) this.session.load(clazz, entity.getId());
				if (e != null) {
					this.session.refresh(e);
				}
				return e;
			} catch (ObjectNotFoundException e) {
				return null;
			}
		}
		E e = (E) this.session.get(clazz, entity.getId());
		if (e != null) {
			this.session.refresh(e);
		}
		return e;
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
