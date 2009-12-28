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

import javax.persistence.Query;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.JPAQueryBuilder;

public abstract class SpringJPAGenericRepository extends SpringJPARepository implements GenericRepository {

	public SpringJPAGenericRepository() {
		super();
	}

	public void delete(final Entity<?> e) {
		this.getJpaTemplate().remove(e);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> E get(final E entity) {
		return (E) this.getJpaTemplate().find(entity.getClass(), entity.getId());
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(final Class<E> clazz) {
		return this.getJpaTemplate().find("SELECT o FROM " + clazz.getCanonicalName() + " AS o");
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listAll(final E e, final String order) {
		String sql = RepositoryHelper.getListAllSql(e.getClass(), order);
		return this.getJpaTemplate().find(sql);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity<?>> Collection<E> listByExample(final E e, final Map<String, Object> options) {
		QueryBuilder builder = new JPAQueryBuilder(this.getJpaTemplate().getEntityManager());
		builder.setEntity(e);
		if ((options != null) && (options.size() > 0)) {
			for (Entry<String, Object> option : options.entrySet()) {
				builder.setOption(option.getKey(), option.getValue());
			}
		}
		builder.build();
		Query q = (Query) builder.getQuery();
		Collection<E> list = q.getResultList();
		return list;
	}

	public void save(final Entity<?> e) {
		this.getJpaTemplate().persist(e);
	}

	public void update(final Entity<?> e) {
		this.getJpaTemplate().merge(e);
	}

}
