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
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

public abstract class AbstractHibernateRepository extends AbstractGenericRepository {

	public static final String	MSG_ERROR_TWO_SESSION	= "Illegal attempt to associate a collection with two open sessions";

	public AbstractHibernateRepository() {
		super();
	}

	public void delete(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		try {
			s.delete(e);
		} catch (PropertyValueException pve) {
			s.refresh(e);
			s.delete(e);
		} catch (NonUniqueObjectException nuoe) {
			e = (Entity<?>) s.get(e.getClass(), e.getId());
			s.delete(e);
		}
		s.flush();
	}

	@SuppressWarnings("unchecked")
	public <ID extends Serializable, E extends Entity<ID>> E get(Class<E> clazz, ID id) throws PersistenceException {
		Session s = this.getSession();
		E entity = (E) s.get(clazz, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(Class<E> clazz, String order) throws PersistenceException {
		Session s = this.getSession();
		String sql = AbstractRepository.getListAllSql(clazz, order);
		Query q = s.createQuery(sql);
		Collection<E> list = q.list();
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
		Query q = (Query) builder.getQuery(this.getSession());
		Collection<E> list = q.list();
		return list;
	}

	public void save(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		s.save(e);
		s.flush();
	}

	public void update(Entity<?> e) throws PersistenceException {
		Session s = this.getSession();
		try {
			s.update(e);
		} catch (NonUniqueObjectException nuoe) {
			s.merge(e);
		} catch (HibernateException he) {
			if (he.getMessage().startsWith(AbstractHibernateRepository.MSG_ERROR_TWO_SESSION)) {
				s.merge(e);
			}
		}
		s.flush();
	}

	protected abstract Session getSession() throws PersistenceException;

}
