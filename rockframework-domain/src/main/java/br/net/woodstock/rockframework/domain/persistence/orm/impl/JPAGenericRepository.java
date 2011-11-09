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

import javax.persistence.EntityManager;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.GenericRepository;
import br.net.woodstock.rockframework.domain.persistence.orm.util.PersistenceUtil;

public class JPAGenericRepository implements GenericRepository {

	private static final long	serialVersionUID	= 7145981385019824074L;

	private EntityManager		entityManager;

	public JPAGenericRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void delete(final Entity<?> e) {
		try {
			this.entityManager.remove(e);
		} catch (IllegalArgumentException ex) {
			Entity tmp = this.entityManager.find(e.getClass(), e.getId());
			this.entityManager.remove(tmp);
		}
	}

	@Override
	public <E extends Entity<?>> E get(final E entity) {
		Class<E> clazz = PersistenceUtil.getRealClass(entity);

		E e = this.entityManager.find(clazz, entity.getId());
		// if (e != null) {
		// this.entityManager.refresh(e);
		// }
		return e;
	}

	@Override
	public void save(final Entity<?> e) {
		this.entityManager.persist(e);
	}

	@Override
	public void update(final Entity<?> e) {
		this.entityManager.merge(e);
	}

}
