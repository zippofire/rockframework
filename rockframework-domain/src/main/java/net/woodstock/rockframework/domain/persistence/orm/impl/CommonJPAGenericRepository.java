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

import javax.persistence.EntityManager;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.orm.GenericRepository;

class CommonJPAGenericRepository implements GenericRepository {

	private EntityManager	entityManager;

	public CommonJPAGenericRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public void delete(final Entity<?> e) {
		if (!this.entityManager.contains(e)) {
			this.entityManager.merge(e);
		}
		this.entityManager.remove(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		E e = (E) this.entityManager.find(entity.getClass(), entity.getId());
		if (e != null) {
			this.entityManager.refresh(e);
		}
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
