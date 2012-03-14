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
package br.net.woodstock.rockframework.persistence.orm.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.net.woodstock.rockframework.persistence.orm.Constants;
import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.persistence.orm.QueryMetadata;
import br.net.woodstock.rockframework.persistence.orm.QueryResult;
import br.net.woodstock.rockframework.persistence.orm.QueryableRepository;
import br.net.woodstock.rockframework.persistence.orm.query.QueryException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class HibernateQueryableRepository implements QueryableRepository {

	private static final long	serialVersionUID	= 459878908923082704L;

	private Session				session;

	public HibernateQueryableRepository() {
		super();
	}

	public HibernateQueryableRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	public void executeUpdate(final QueryMetadata query) {
		Query q = this.getQuery(query);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryResult getCollection(final QueryMetadata query) {
		Query q = this.getQuery(query);
		List<Object> list = q.list();
		return this.buildResult(query, list, 0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E getSingle(final QueryMetadata query) {
		Query q = this.getQuery(query);
		Object obj = q.uniqueResult();
		return (E) obj;
	}

	protected QueryResult buildResult(final QueryMetadata query, final Collection<Object> collection, final int total) {
		return new QueryResultImpl(total, collection, query.getPage());
	}

	protected Query getQuery(final QueryMetadata query) {
		return this.getQuery(query, this.session);
	}

	@SuppressWarnings("rawtypes")
	protected Query getQuery(final QueryMetadata query, final Session session) {
		Assert.notNull(query, "query");
		Assert.notNull(session, "session");

		Map<String, Object> parameters = query.getParameters();
		Map<String, Object> options = query.getOptions();

		Query q = null;

		if (query.isNamed()) {
			throw new QueryException("Hibernate dont support named queries");
		} else if (query.isNativeSQL()) {
			if ((ConditionUtils.containsKey(options, Constants.OPTION_TARGET_ENTITY))) {
				Object targetEntity = options.get(Constants.OPTION_TARGET_ENTITY);
				if (targetEntity instanceof Class) {
					SQLQuery sq = session.createSQLQuery(query.getQuery());
					sq.addEntity((Class) targetEntity);
					q = sq;
				} else if (targetEntity instanceof String) {
					try {
						SQLQuery sq = session.createSQLQuery(query.getQuery());
						sq.addEntity(Class.forName((String) targetEntity));
						q = sq;
					} catch (ClassNotFoundException e) {
						throw new QueryException(e);
					}
				} else {
					throw new QueryException("Invalid option[" + Constants.OPTION_TARGET_ENTITY + "] " + targetEntity);
				}
			} else {
				q = session.createSQLQuery(query.getQuery());
			}
		} else {
			q = session.createQuery(query.getQuery());
		}

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (RepositoryHelper.isCollection(value)) {
					q.setParameterList(name, (Collection) value);
				} else if (RepositoryHelper.isArray(value)) {
					q.setParameterList(name, (Object[]) value);
				} else {
					q.setParameter(name, value);
				}
			}
		}

		if (query.isCacheEnabled()) {
			q.setCacheable(true);
			q.setCacheMode(CacheMode.NORMAL);
		}

		if (query.getPage() != null) {
			Page page = query.getPage();
			int firstResult = ((page.getPageNumber() - 1) * page.getResultsPerPage()) + 1;
			q.setFirstResult(firstResult);
			q.setMaxResults(page.getResultsPerPage());
		}

		if (ConditionUtils.isNotEmpty(options)) {
			if (options.containsKey(Constants.OPTION_READ_ONLY)) {
				Boolean readOnly = (Boolean) options.get(Constants.OPTION_READ_ONLY);
				q.setReadOnly(readOnly.booleanValue());
			}
		}

		return q;
	}
}
