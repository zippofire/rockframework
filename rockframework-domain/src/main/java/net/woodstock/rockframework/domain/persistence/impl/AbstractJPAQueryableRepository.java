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
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

abstract class AbstractJPAQueryableRepository extends AbstractQueryableRepository {

	public AbstractJPAQueryableRepository() {
		super();
	}

	@Override
	public void executeUpdate(final String sql, final Map<String, Object> parameters) {
		Query query = this.getQuery(sql, parameters);
		query.executeUpdate();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection getCollection(final String sql, final Map<String, Object> parameters) {
		Query query = this.getQuery(sql, parameters);
		List list = query.getResultList();
		return list;
	}

	@Override
	public Object getSingle(final String sql, final Map<String, Object> parameters) {
		Query query = this.getQuery(sql, parameters);
		Object obj = query.getSingleResult();
		return obj;
	}

	protected abstract Query getQuery(final String sql, final Map<String, Object> parameters);
}
