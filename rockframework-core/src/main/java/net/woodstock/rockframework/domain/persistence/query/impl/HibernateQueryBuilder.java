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
package net.woodstock.rockframework.domain.persistence.query.impl;

import java.util.Map;

import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

public class HibernateQueryBuilder extends EJBQLQueryBuilder<Query> {

	private Session	session;

	public HibernateQueryBuilder(final Session session) {
		super();
		this.session = session;
	}

	@Override
	protected Query getQuery(final String sql) {
		Query query = this.session.createQuery(sql);
		query.setResultTransformer(new DistinctRootEntityResultTransformer());
		return query;
	}

	@Override
	protected void setQueryParameter(final Object query, final String name, final Object value) {
		this.getLog().debug("Setting parameter[" + name + "] => " + value);
		Query q = (Query) query;
		q.setParameter(name, value);
	}

	@Override
	protected void setQueryOptions(final Object query, final Map<String, Object> options) {
		Query q = (Query) query;
		if ((options.containsKey(QueryBuilder.OPTION_FIRST_RESULT)) && (options.get(QueryBuilder.OPTION_FIRST_RESULT) instanceof Integer)) {
			Integer firstResult = (Integer) options.get(QueryBuilder.OPTION_FIRST_RESULT);
			q.setFirstResult(firstResult.intValue());
		}
		if ((options.containsKey(QueryBuilder.OPTION_MAX_RESULT)) && (options.get(QueryBuilder.OPTION_MAX_RESULT) instanceof Integer)) {
			Integer maxResult = (Integer) options.get(QueryBuilder.OPTION_MAX_RESULT);
			q.setMaxResults(maxResult.intValue());
		}
	}

}
