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

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.query.impl.JPAQueryBuilder;
import net.woodstock.rockframework.utils.ConditionUtils;

class CommonJPAGenericRepository implements GenericRepository {

	private EntityManager	entityManager;

	public CommonJPAGenericRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public void delete(final Entity<?> e) {
		this.entityManager.remove(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> E get(final E entity) {
		E e = (E) this.entityManager.find(entity.getClass(), entity.getId());
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listAll(final E e, final Map<String, Object> options) {
		String sql = RepositoryHelper.getListAllSql(e.getClass(), options, false);
		Query q = this.entityManager.createQuery(sql);
		Collection<E> list = q.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		JPAQueryBuilder builder = new JPAQueryBuilder(this.entityManager);
		builder.setEntity(e);
		if (ConditionUtils.isNotEmpty(options)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		Query q = builder.getQuery();
		Collection<E> list = q.getResultList();
		return list;
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
