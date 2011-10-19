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
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import br.net.woodstock.rockframework.domain.persistence.orm.CacheMode;
import br.net.woodstock.rockframework.domain.persistence.orm.Constants;
import br.net.woodstock.rockframework.domain.persistence.orm.JPQLRepository;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class HibernateJPQLRepository extends AbstractHibernateQueryableRepository implements JPQLRepository {

	private Session	session;

	public HibernateJPQLRepository(final Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Query getQuery(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query) {
		Query q = this.session.createQuery(query.getQuery());

		Map<String, Object> parameters = query.getParameters();
		Map<String, Object> options = query.getOptions();

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (RepositoryHelper.isValidParameter(name)) {
					if (RepositoryHelper.isCollection(value)) {
						q.setParameterList(name, (Collection) value);
					} else if (RepositoryHelper.isArray(value)) {
						q.setParameterList(name, (Object[]) value);
					} else {
						q.setParameter(name, value);
					}
				}
			}

		}

		if (ConditionUtils.isNotEmpty(options)) {
			if ((options.containsKey(Constants.OPTION_CACHE_MODE)) && (options.get(Constants.OPTION_CACHE_MODE) instanceof CacheMode)) {
				CacheMode cacheMode = (CacheMode) options.get(Constants.OPTION_CACHE_MODE);
				if (cacheMode == CacheMode.ENABLED) {
					q.setCacheable(true);
					q.setCacheMode(org.hibernate.CacheMode.NORMAL);
				}
			}
			if (options.containsKey(Constants.OPTION_FIRST_RESULT)) {
				Integer firstResult = (Integer) options.get(Constants.OPTION_FIRST_RESULT);
				q.setFirstResult(firstResult.intValue());
			}
			if (options.containsKey(Constants.OPTION_MAX_RESULT)) {
				Integer maxResult = (Integer) options.get(Constants.OPTION_MAX_RESULT);
				q.setMaxResults(maxResult.intValue());
			}
			if (options.containsKey(Constants.OPTION_READ_ONLY)) {
				Boolean readOnly = (Boolean) options.get(Constants.OPTION_READ_ONLY);
				q.setReadOnly(readOnly.booleanValue());
			}
		}

		return q;
	}
}
