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

import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.persistence.EJBQLRepository;
import net.woodstock.rockframework.domain.persistence.query.CacheMode;
import net.woodstock.rockframework.domain.persistence.util.Constants;

import org.hibernate.Query;
import org.hibernate.Session;

class CommonHibernateEJBQLRepository extends AbstractHibernateQueryableRepository implements EJBQLRepository {

	private Session	session;

	public CommonHibernateEJBQLRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	protected Query getQuery(final String sql, final Map<String, Object> parameters) {
		Query query = this.session.createQuery(sql);

		if ((parameters != null) && (parameters.size() > 0)) {
			if ((parameters.containsKey(Constants.OPTION_CACHE_MODE)) && (parameters.get(Constants.OPTION_CACHE_MODE) instanceof CacheMode)) {
				CacheMode cacheMode = (CacheMode) parameters.get(Constants.OPTION_CACHE_MODE);
				if (cacheMode == CacheMode.ENABLED) {
					query.setCacheable(true);
					query.setCacheMode(org.hibernate.CacheMode.NORMAL);
				}
			}
			if (parameters.containsKey(Constants.OPTION_FIRST_RESULT)) {
				Integer firstResult = (Integer) parameters.get(Constants.OPTION_FIRST_RESULT);
				query.setFirstResult(firstResult.intValue());
			}
			if (parameters.containsKey(Constants.OPTION_MAX_RESULT)) {
				Integer maxResult = (Integer) parameters.get(Constants.OPTION_MAX_RESULT);
				query.setMaxResults(maxResult.intValue());
			}
			if (parameters.containsKey(Constants.OPTION_READ_ONLY)) {
				Boolean readOnly = (Boolean) parameters.get(Constants.OPTION_READ_ONLY);
				query.setReadOnly(readOnly.booleanValue());
			}

			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (this.isValidParameter(name)) {
					query.setParameter(name, value);
				}
			}

		}

		return query;
	}
}
