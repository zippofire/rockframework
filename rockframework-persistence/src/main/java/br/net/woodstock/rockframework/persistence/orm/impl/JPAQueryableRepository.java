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
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.net.woodstock.rockframework.domain.config.DomainLog;
import br.net.woodstock.rockframework.persistence.orm.Constants;
import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.persistence.orm.QueryMetadata;
import br.net.woodstock.rockframework.persistence.orm.QueryResult;
import br.net.woodstock.rockframework.persistence.orm.QueryableRepository;
import br.net.woodstock.rockframework.persistence.orm.query.QueryException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class JPAQueryableRepository implements QueryableRepository {

	private static final long	serialVersionUID	= -4419669801803379112L;

	private EntityManager		entityManager;

	public JPAQueryableRepository() {
		super();
	}

	public JPAQueryableRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
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
		List<Object> list = q.getResultList();

		int total = -1;

		if (query.getPage() != null) {
			Query qCount = this.getCountQuery(query);
			Number number = (Number) qCount.getSingleResult();
			total = number.intValue();
		} else {
			total = list.size();
		}

		return this.buildResult(query, list, total);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E getSingle(final QueryMetadata query) {
		try {
			Query q = this.getQuery(query);
			Object obj = q.getSingleResult();
			return (E) obj;
		} catch (NoResultException e) {
			DomainLog.getInstance().getLog().log(Level.FINE, e.getMessage(), e);
			return null;
		}
	}

	protected QueryResult buildResult(final QueryMetadata query, final Collection<Object> collection, final int total) {
		return new QueryResultImpl(total, collection, query.getPage());
	}

	protected Query getQuery(final QueryMetadata query) {
		return this.getQuery(query, this.entityManager);
	}

	protected Query getCountQuery(final QueryMetadata query) {
		return this.getCountQuery(query, this.entityManager);
	}

	@SuppressWarnings("rawtypes")
	protected Query getQuery(final QueryMetadata query, final EntityManager entityManager) {
		Assert.notNull(query, "query");
		Assert.notNull(entityManager, "entityManager");

		Map<String, Object> parameters = query.getParameters();
		Map<String, Object> options = query.getOptions();

		Query q = null;

		if (query.isNamed()) {
			q = entityManager.createNamedQuery(query.getQuery());
		} else if (query.isNativeSQL()) {
			if ((ConditionUtils.containsKey(options, Constants.OPTION_TARGET_ENTITY))) {
				Object targetEntity = options.get(Constants.OPTION_TARGET_ENTITY);
				if (targetEntity instanceof Class) {
					q = entityManager.createNativeQuery(query.getQuery(), (Class) targetEntity);
				} else if (targetEntity instanceof String) {
					try {
						q = entityManager.createNativeQuery(query.getQuery(), Class.forName((String) targetEntity));
					} catch (ClassNotFoundException e) {
						throw new QueryException(e);
					}
				} else {
					throw new QueryException("Invalid option[" + Constants.OPTION_TARGET_ENTITY + "] " + targetEntity);
				}
			} else {
				q = entityManager.createNativeQuery(query.getQuery());
			}
		} else {
			q = entityManager.createQuery(query.getQuery());
		}

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				q.setParameter(name, value);
			}
		}

		if (query.getPage() != null) {
			Page page = query.getPage();
			int firstResult = ((page.getPageNumber() - 1) * page.getResultsPerPage());
			q.setFirstResult(firstResult);
			q.setMaxResults(page.getResultsPerPage());
		}

		return q;
	}

	protected Query getCountQuery(final QueryMetadata query, final EntityManager entityManager) {
		Assert.notNull(query, "query");
		Assert.notNull(entityManager, "entityManager");

		Map<String, Object> parameters = query.getParameters();

		Query q = null;

		if (query.isNamed()) {
			q = entityManager.createNamedQuery(query.getCountQuery());
		} else if (query.isNativeSQL()) {
			q = entityManager.createNativeQuery(query.getCountQuery());
		} else {
			q = entityManager.createQuery(query.getCountQuery());
		}

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				q.setParameter(name, value);
			}
		}

		return q;
	}
}
