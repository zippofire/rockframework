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

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;

public abstract class SpringJPAGenericRepository extends SpringJPARepository implements GenericRepository {

	public SpringJPAGenericRepository() {
		super();
	}

	@Override
	public void delete(final Entity<?> e) {
		this.getJpaTemplate().remove(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		return (E) this.getJpaTemplate().find(entity.getClass(), entity.getId());
	}

	@Override
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		return new CommonJPAGenericRepository(this.getJpaTemplate().getEntityManager()).listAll(e, options);
	}

	@Override
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		return new CommonJPAGenericRepository(this.getJpaTemplate().getEntityManager()).listByExample(e, options);
	}

	@Override
	public void save(final Entity<?> e) {
		this.getJpaTemplate().persist(e);
	}

	@Override
	public void update(final Entity<?> e) {
		this.getJpaTemplate().merge(e);
	}

}
