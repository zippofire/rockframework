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

import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.QueryStrategy;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

public class HibernateQueryStrategy implements QueryStrategy {

	public Object getQuery(String sql, Object manager) {
		Session session = (Session) manager;
		Query query = session.createQuery(sql);
		query.setResultTransformer(new DistinctRootEntityResultTransformer());
		return query;
	}

	public void setParameter(Object query, String name, Object value) {
		Query q = (Query) query;
		q.setParameter(name, value);
	}

	public void setOption(Object query, String name, Object value) throws BuilderException {
		if ((name != null) && (value != null)) {
			Query q = (Query) query;
			if ((name.equals(QueryBuilder.OPTION_FIRST_RESULT)) && (value instanceof Integer)) {
				Integer firstResult = (Integer) value;
				q.setFirstResult(firstResult.intValue());
			}
			if ((name.equals(QueryBuilder.OPTION_MAX_RESULT)) && (value instanceof Integer)) {
				Integer maxResult = (Integer) value;
				q.setMaxResults(maxResult.intValue());
			}
		}
	}

}
