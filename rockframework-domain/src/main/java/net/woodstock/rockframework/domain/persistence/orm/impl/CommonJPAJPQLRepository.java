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
package net.woodstock.rockframework.domain.persistence.orm.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.woodstock.rockframework.domain.persistence.orm.JPQLRepository;
import net.woodstock.rockframework.domain.query.Constants;
import net.woodstock.rockframework.utils.ConditionUtils;

class CommonJPAJPQLRepository extends AbstractJPAQueryableRepository implements JPQLRepository {

	private EntityManager	entityManager;

	public CommonJPAJPQLRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	protected Query getQuery(final String sql, final Map<String, Object> parameters) {
		EntityManager entityManager = this.entityManager;
		Query query = entityManager.createQuery(sql);

		if (ConditionUtils.isNotEmpty(parameters)) {
			if (parameters.containsKey(Constants.OPTION_FIRST_RESULT)) {
				Integer firstResult = (Integer) parameters.get(Constants.OPTION_FIRST_RESULT);
				query.setFirstResult(firstResult.intValue());
			}
			if (parameters.containsKey(Constants.OPTION_MAX_RESULT)) {
				Integer maxResult = (Integer) parameters.get(Constants.OPTION_MAX_RESULT);
				query.setMaxResults(maxResult.intValue());
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
