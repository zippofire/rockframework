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
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SpringHibernateRepository extends HibernateDaoSupport implements GenericRepository {

	public SpringHibernateRepository() {
		super();
	}

	public void delete(Entity<?> e) throws PersistenceException {
		this.getHibernateTemplate().execute(new DeleteCallback(e));
	}

	@SuppressWarnings("unchecked")
	public <ID extends Serializable, E extends Entity<ID>> E get(Class<E> clazz, ID id) throws PersistenceException {
		return (E) this.getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(Class<E> clazz, String order) throws PersistenceException {
		String sql = AbstractRepository.getListAllSql(clazz, order);
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
		// this.getHibernateTemplate().update(e);
		this.getHibernateTemplate().execute(new UpdateCallback(e));
	}

	// Callback
	class DeleteCallback implements HibernateCallback {

		private Entity<?>	entity;

		public DeleteCallback(Entity<?> entity) {
			super();
			this.entity = entity;
		}

		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			try {
				session.delete(this.entity);
			} catch (PropertyValueException pve) {
				session.refresh(this.entity);
				session.delete(this.entity);
			} catch (NonUniqueObjectException nuoe) {
				Entity<?> e = (Entity<?>) session.get(this.entity.getClass(), this.entity.getId());
				session.delete(e);
			}
			return null;
		}
	}

	class UpdateCallback implements HibernateCallback {

		private Entity<?>	entity;

		public UpdateCallback(Entity<?> entity) {
			super();
			this.entity = entity;
		}

		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			try {
				session.update(this.entity);
			} catch (NonUniqueObjectException nuoe) {
				session.merge(this.entity);
			} catch (HibernateException he) {
				if (he.getMessage().startsWith(AbstractHibernateRepository.MSG_ERROR_TWO_SESSION)) {
					session.merge(this.entity);
				}
			}
			return null;
		}
	}

}
