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

import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.config.DomainConfig;
import br.net.woodstock.rockframework.domain.persistence.orm.GenericRepository;
import br.net.woodstock.rockframework.domain.persistence.orm.util.PersistenceUtil;

public class HibernateGenericRepository implements GenericRepository {

	private static final long		serialVersionUID	= -6733344942134234646L;

	private static final String		PROPERTY_GET_TYPE	= "hibernate.getType";

	private static final GetType	GET_TYPE			= GetType.valueOf(DomainConfig.getInstance().getValue(HibernateGenericRepository.PROPERTY_GET_TYPE));

	private Session					session;

	public HibernateGenericRepository(final Session session) {
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
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		Class<E> clazz = PersistenceUtil.getRealClass(entity);

		if (HibernateGenericRepository.GET_TYPE == GetType.LOAD) {
			try {
				E e = (E) this.session.load(clazz, entity.getId());
				// if (e != null) {
				// this.session.refresh(e);
				// }
				return e;
			} catch (ObjectNotFoundException e) {
				return null;
			}
		}
		E e = (E) this.session.get(clazz, entity.getId());
		// if (e != null) {
		// this.session.refresh(e);
		// }
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
		}
	}

	public static enum GetType {
		GET, LOAD;
	}

}
