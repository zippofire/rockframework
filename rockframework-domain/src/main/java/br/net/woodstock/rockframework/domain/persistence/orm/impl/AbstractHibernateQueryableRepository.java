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

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

abstract class AbstractHibernateQueryableRepository extends AbstractQueryableRepository {

	public AbstractHibernateQueryableRepository() {
		super();
	}

	@Override
	public void executeUpdate(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query) {
		Query q = this.getQuery(query);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> Collection<E> getCollection(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query) {
		Query q = this.getQuery(query);
		List<E> list = q.list();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E getSingle(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query) {
		Query q = this.getQuery(query);
		Object obj = q.uniqueResult();
		return (E) obj;
	}

	protected abstract Query getQuery(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query);

}
